package com.daou.books.order.controller;

import com.daou.books.core.domain.model.PageModel;
import com.daou.books.order.domain.Order;
import com.daou.books.order.domain.OrderModel;
import com.daou.books.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/orders/{userId}")
    @ResponseBody
    public PageModel<OrderModel> getOrders(@PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property) {

        Pageable pageable = new PageRequest(page, offset, new Sort(Sort.Direction.fromString(direction), property));
        return orderService.getOrders(pageable, userId);
    }

    @PostMapping("/api/order")
    @ResponseBody
    public OrderModel addOrder(@RequestBody OrderModel model) {
        return orderService.addOrder(model);
    }

}
