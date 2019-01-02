package cosumer.service;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(COMMIT_COMPLETED_URL,String.class);
    }
}
