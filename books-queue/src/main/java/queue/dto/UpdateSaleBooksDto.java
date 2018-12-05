package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
@Data
public class UpdateSaleBooksDto extends QueueDto {


    @JsonProperty
    String isbn;
    @JsonProperty
    Integer salesCount;

    public UpdateSaleBooksDto(String isbn, Integer salesCount) {
        super(ChainFunction.UPDATE_BOOK);
        this.isbn = isbn;
        this.salesCount = salesCount;
    }

    public ChainFunction getChainFunction() {
        return chainFunction;
    }
}
