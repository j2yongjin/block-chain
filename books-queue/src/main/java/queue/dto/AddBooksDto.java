package queue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class AddBooksDto {

    @JsonProperty
    String isbn;
    @JsonProperty
    String name;
    @JsonProperty
    String writer;
    @JsonProperty
    Integer amount;
    @JsonProperty
    String issueDate;
    @JsonProperty
    Integer salesCount;

    public AddBooksDto(String isbn, String name, String writer, Integer amount, String issueDate, Integer salesCount) {
        this.isbn = isbn;
        this.name = name;
        this.writer = writer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.salesCount = salesCount;
    }

}
