package com.daou.books.order.service;

import com.daou.books.order.domain.Order;
import com.daou.books.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders(Long userId) {

        List<Order> order = orderRepository.findByUserId(userId);
        return order;
    }

    @Override
    public Order addOrder(Order order) {
        return null;
    }
}
