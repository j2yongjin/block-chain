package cosumer.service;

import com.daou.supplier.config.SupplierConfig;
import com.daou.supplier.model.BlockchainUser;
import com.daou.supplier.model.Books;
import com.daou.supplier.service.DefaultBookService;
import com.daou.supplier.service.DefaultUserService;
import com.daou.supplier.service.UserService;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import queue.dto.AddBooksDto;

import java.util.Collections;

/**
 * Created by yjlee on 2019-01-02.
 */
public class ChainCodeConsumerTest {

    static final String COMMIT_COMPLETED_URL = "http://localhost/api/event/book/add";

    @Test
    public void given_when_thenSuccss(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity =
//                restTemplate.getForEntity(COMMIT_COMPLETED_URL,String.class);

        restTemplate.put(COMMIT_COMPLETED_URL+"/11112222",null);

//        ResponseEntity<String> responseEntity =
//                restTemplate.exchange(COMMIT_COMPLETED_URL,HttpMethod.PUT,new HttpEntity<>("sdf"),
    }


    @Test
    public void given_whenAddBooks_thenSucess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);
        AddBooksDto addBooksDto = new AddBooksDto();
        addBooksDto.setIsbn("1111");
        addBooksDto.setAmount(1000);
        addBooksDto.setIssueDate("20181122162314");
        addBooksDto.setName("goods books ");
        addBooksDto.setSalesCount(0);
        addBooksDto.setWriter("kingyjlee");

        DefaultBookService defaultBookService = new DefaultBookService();
        Books books = new Books(addBooksDto.getIsbn(),addBooksDto.getName(),addBooksDto.getWriter()
                ,addBooksDto.getAmount(),addBooksDto.getIssueDate(),addBooksDto.getSalesCount());
        defaultBookService.addBooks(books);

    }
}
