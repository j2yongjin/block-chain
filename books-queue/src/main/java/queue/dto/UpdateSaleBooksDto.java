package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateSaleBooksDto extends QueueDto {

    @JsonProperty
    String isbn;
    @JsonProperty
    Long orderId;
    @JsonProperty
    Integer salesCount;

    public UpdateSaleBooksDto(){
        super(ChainFunction.UPDATE_BOOK);
    };

    public UpdateSaleBooksDto(String isbn, Long orderId, Integer salesCount) {
        super(ChainFunction.UPDATE_BOOK);
        this.isbn = isbn;
        this.orderId = orderId;
        this.salesCount = salesCount;
    }

    public ChainFunction getChainFunction() {
        return chainFunction;
    }
}
