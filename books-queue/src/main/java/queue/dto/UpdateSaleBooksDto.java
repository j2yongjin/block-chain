package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class UpdateSaleBooksDto {

    @JsonProperty
    static final ChainFunction chainFunction = ChainFunction.UPDATE_BOOK;

    @JsonProperty
    String isbn;
    @JsonProperty
    Integer salesCount;

    public UpdateSaleBooksDto(String isbn, Integer salesCount) {
        this.isbn = isbn;
        this.salesCount = salesCount;
    }

    public static ChainFunction getChainFunction() {
        return chainFunction;
    }
}
