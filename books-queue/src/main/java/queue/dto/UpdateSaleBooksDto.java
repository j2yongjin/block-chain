package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class UpdateSaleBooksDto extends QueueDto {

//    @JsonProperty
//    ChainFunction chainFunction = ChainFunction.UPDATE_BOOK;

    @JsonProperty
    String isbn;
    @JsonProperty
    Integer salesCount;

    public UpdateSaleBooksDto(String isbn, Integer salesCount) {
        super(ChainFunction.ADD_BOOK);
        this.isbn = isbn;
        this.salesCount = salesCount;
    }

    public ChainFunction getChainFunction() {
        return chainFunction;
    }
}
