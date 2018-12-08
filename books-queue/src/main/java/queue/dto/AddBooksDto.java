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
public class AddBooksDto extends QueueDto {

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

    public AddBooksDto(){
        super(ChainFunction.ADD_BOOK);
    }

    public AddBooksDto(String isbn, String name, String writer, Integer amount, String issueDate, Integer salesCount) {
        super(ChainFunction.ADD_BOOK);
        this.isbn = isbn;
        this.name = name;
        this.writer = writer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.salesCount = salesCount;
    }

    public AddBooksDto(ChainFunction chainFunction,String isbn, String name, String writer, Integer amount, String issueDate, Integer salesCount) {
        super(chainFunction);
        this.isbn = isbn;
        this.name = name;
        this.writer = writer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.salesCount = salesCount;
    }

    public ChainFunction getChainFunction() {
        return chainFunction;
    }
}
