package queue.consume;

import java.io.IOException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public interface ConsumerService {

    void consume() throws IOException;
}
