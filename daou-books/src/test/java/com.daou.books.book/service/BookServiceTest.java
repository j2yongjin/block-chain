package com.daou.books.book.service;

import com.daou.books.book.domain.Book;
import com.daou.books.core.ProcessStatus;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import queue.network.RabbitChannelFactory;
import queue.publish.DefaultPublishService;

import java.util.Date;

@MockBean
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void addBook() throws Exception {

        // 1. db insert
        Book book = new Book();
        book.setIsbn("100011112222");
        book.setTitle("TestTitle");
        book.setWriter("김작가");
        book.setStatus(ProcessStatus.SAVED_DB);
        book.setIssueDate(new Date());
        bookService.addBook(book);

        // 2. queues insert
        RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
        DefaultPublishService defaultPublishService = new DefaultPublishService(rabbitChannelFactory,"books-queue","book");
//        AddBooksDto addBooksDto = new AddBooksDto("1234","hello-world" + System.currentTimeMillis(),"yjlee",1000,"20181114",1);
        defaultPublishService.basicPublish(book);

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