package kuzmindenis.test.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramResponse {
    
    @JsonProperty("id")
    public final Long id;

    @JsonProperty("name")
    public final String name;

    @JsonProperty("actionDetails")
    public final String actionDetails;

    @JsonProperty("image")
    public final String image;
    
    @JsonProperty("categoriesIds")
    public final String categoriesIds;

    @JsonProperty("goToLink")
    public final String goToLink;

    @JsonProperty("productXmlLink")
    public final String productXmlLink;

    public ProgramResponse(Long id, String name, String actionDetails, String image, String categoriesIds, String goToLink, String productXmlLink) {
        this.id = id;
        this.name = name;
        this.actionDetails = actionDetails;
        this.image = image;
        this.categoriesIds = categoriesIds;
        this.goToLink = goToLink;
        this.productXmlLink = productXmlLink;
    }
}
