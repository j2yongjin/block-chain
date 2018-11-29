package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class UpdateSaleBooks {
    @JsonProperty
    String isbn;
    @JsonProperty
    Integer salesCount;

    public UpdateSaleBooks(String isbn, Integer salesCount) {
        this.isbn = isbn;
        this.salesCount = salesCount;
    }
}
