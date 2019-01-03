package com.daou.books.book.service;

import com.daou.books.book.domain.Book;
import com.daou.books.book.domain.model.BookModel;
import com.daou.books.book.repository.BookRepository;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.service.MqPublish;
import com.daou.books.order.domain.Order;
import com.daou.books.order.service.OrderService;
import com.daou.books.queue.RabbitChannelFactory;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public PageModel<BookModel> getBookList(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookModel> models = Lists.newArrayList();
        for (Book book : books) {
            models.add(new BookModel(book));
        }
        return new PageModel(models, books);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<BookModel> addBooks(List<Book> books) {
        List<BookModel> models = Lists.newArrayList();
        for(Book book : books) {
            models.add(addBook(book));
        }
        return models;
    }

    @Override
    @Transactional
    public BookModel addBook(Book book) {
        String isbn = generateIsbn();
        book.setIsbn(isbn);
        book.setSalesCount(0L);

        // 1. DB insert
        Book newBook = bookRepository.save(book);

        // 2. chaincode에 등록
        pushQueueForBook(newBook);

        return new BookModel(newBook);
    }

    @Override
    @Transactional
    public BookModel updateBook(Book book) {
        return new BookModel(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookModel updateSalesCount(Long orderId, String isbn) {
        Order order = orderService.getOrder(orderId);
        if(null == order) {
            return null;
        }

        Book book = order.getBook();
        if(Strings.isNullOrEmpty(isbn) || isbn.equals(book.getIsbn())) {
            return null;
        }

        Long salesCount = book.getSalesCount();
        book.setSalesCount(salesCount + 1);
        return updateBook(book);
    }

    @Override
    @Transactional
    public BookModel updateBookStatus(String isbn, ProcessStatus status) {
        Book book = getBook(isbn);

        if(null == book) {
            return null;
        }

        if(null == status) {
            book.setStatus(ProcessStatus.COMPLETED);
        } else {
            book.setStatus(status);
        }

        return updateBook(book);
    }

    @Async
    @Override
    public void pushQueueForBook(Book book) {
        try {
            RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
            MqPublish mqPublish = new MqPublish(rabbitChannelFactory,"books-queue","book");
            mqPublish.basicPublish(book);
        } catch (Exception e) {
            log.error("book push queue error: {}, {}", book.getIsbn(), e);
        }

    }

    private String generateIsbn() {
        long timeSeed = System.nanoTime(); // to get the current date time value
        double randSeed = Math.random() * 1000; // random number generation
        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and

        String s = midSeed + "";
        String subStr = s.substring(0, 8);
        int finalSeed = Integer.parseInt(subStr); // integer value

        String num = String.format("%08d", finalSeed);
        String newIbsn = String.format("1000%s", num);
        log.info("new ibsn: {}", newIbsn);

        return newIbsn;
    }
}
