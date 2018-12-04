package queue.exception;

/**
 * Created by yjlee on 2018-12-03.
 */
public class ConsumerException extends RuntimeException {

    public ConsumerException() {
    }

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
