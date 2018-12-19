package com.daou.books.order.service;

import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.domain.model.UserModel;
import com.daou.books.order.domain.Order;
import com.daou.books.order.domain.OrderModel;
import com.daou.books.order.repository.OrderRepository;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override public PageModel<OrderModel> getOrders(Pageable pageable, Long userId) {

        Page<Order> orders = orderRepository.findByUserId(userId, pageable);

        List<OrderModel> models = Lists.newArrayList();
        for(Order order: orders) {
            models.add(new OrderModel(order));
        }

        return new PageModel(models, orders);
    }

    @Override public OrderModel addOrder(OrderModel model) {
        Order order = new Order(model);
        // 1. db insert

        // 2. 큐 insert
        return new OrderModel(orderRepository.save(order));
    }

}
