package kuzmindenis.test.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {

    @JsonProperty("programId")
    public final Long programId;

    @JsonProperty("productId")
    public final  Long productId;

    @JsonProperty("name")
    public final  String name;

    @JsonProperty("model")
    public final  String model;

    @JsonProperty("price")
    public final  Double price;

    @JsonProperty("buy_url")
    public final  String buyUrl;

    public ProductResponse(Long programId, Long productId, String name, String model, Double price, String buyUrl) {
        this.programId = programId;
        this.productId = productId;
        this.name = name;
        this.model = model;
        this.price = price;
        this.buyUrl = buyUrl;
    }
}
