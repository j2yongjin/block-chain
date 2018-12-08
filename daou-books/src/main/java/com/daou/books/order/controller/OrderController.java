package com.daou.books.order.controller;

import com.daou.books.order.domain.Order;
import com.daou.books.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/orders")
    @ResponseBody
    public List<Order> getOrders(@RequestParam(required = false) Long id) {
        return orderService.getOrders(id);
    }

    @PostMapping("/api/order")
    @ResponseBody
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

}
