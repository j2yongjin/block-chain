package com.daou.books.book.service;

import com.daou.books.book.domain.Book;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void addBook() throws Exception {

        Book book = new Book();
        bookService.addBook(book);


    }

    @Test
    public void connectionMQ() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.close();
        conn.close();

    }

}