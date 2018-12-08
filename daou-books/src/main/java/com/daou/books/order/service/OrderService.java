package com.daou.books.order.service;

import com.daou.books.order.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(Long userId);

    Order addOrder(Order order);

}
