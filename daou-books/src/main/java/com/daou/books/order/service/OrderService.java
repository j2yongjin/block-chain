package com.daou.books.order.service;

import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.order.domain.Order;
import com.daou.books.order.domain.OrderModel;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    PageModel<OrderModel> getOrders(Pageable pageable, Long userId);

    OrderModel addOrder(OrderModel model);

    void pushQueueForOrder(Order order);

    void updateOrderStatus(Long orderId, ProcessStatus status);

}
