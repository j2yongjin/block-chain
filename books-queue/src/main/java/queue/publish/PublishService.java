package queue.publish;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public interface PublishService {

    <T >void basicPublish(T t) throws IOException, TimeoutException;
}
