package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by yjlee on 2018-12-03.
 */
public abstract class QueueDto {

    @JsonProperty
    ChainFunction chainFunction;

    public QueueDto(ChainFunction chainFunction) {
        this.chainFunction = chainFunction;
    }

    public ChainFunction getChainFunction(){
        return chainFunction;
    }
}
