package com.daou.books.order.service;

import com.daou.books.core.domain.model.PageModel;
import com.daou.books.order.domain.Order;
import com.daou.books.order.domain.OrderModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    PageModel<OrderModel> getOrders(Pageable pageable, Long userId);

    OrderModel addOrder(OrderModel model);

}
