package com.daou.books.order.domain;

import com.daou.books.book.domain.model.BookModel;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.model.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderModel {

        private Long id;

        private UserModel user;

        private BookModel book;

        private ProcessStatus status;

        public OrderModel(Order order) {
                this.id = order.getId();
                this.user = new UserModel(order.getUser());
                this.book = new BookModel(order.getBook());
                this.status = order.getStatus();
        }
}
