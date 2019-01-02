package com.daou.books.order.service;

import com.daou.books.book.service.BookService;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.service.MqPublish;
import com.daou.books.core.service.UserSerivce;
import com.daou.books.order.domain.Order;
import com.daou.books.order.domain.OrderModel;
import com.daou.books.order.repository.OrderRepository;
import com.daou.books.queue.RabbitChannelFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private BookService bookService;

    @Override
    public PageModel<OrderModel> getOrders(Pageable pageable, Long userId) {

        Page<Order> orders = orderRepository.findByUserId(userId, pageable);

        List<OrderModel> models = Lists.newArrayList();
        for(Order order: orders) {
            models.add(new OrderModel(order));
        }

        return new PageModel(models, orders);
    }

    @Override
    public OrderModel addOrder(OrderModel model) {

        Order order = new Order();

        // 1. db insert
        order.setBook(bookService.getBook(model.getBookId()));
        order.setUser(userSerivce.getUser(model.getUserId()));
        order.setStatus(ProcessStatus.SAVED_DB);
        orderRepository.save(order);

        // 2. 큐 insert
        pushQueueForOrder(order);
        order.setStatus(ProcessStatus.WAITING_BLOCKCHAIN);

        return new OrderModel(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, ProcessStatus status) {
        Order order = orderRepository.findOne(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Async
    @Override
    public void pushQueueForOrder(Order order) {
        try {
            RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
            MqPublish mqPublish = new MqPublish(rabbitChannelFactory,"books-queue","order");
            mqPublish.basicPublish(order);
        } catch (Exception e) {
            log.error("order push queue error: {}, {}", order.getId(), e);
        }

    }

}
