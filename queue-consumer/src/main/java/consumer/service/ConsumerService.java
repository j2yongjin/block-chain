package consumer.service;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public interface ConsumerService {

    void consume(Consumer consumer) throws IOException;
}
