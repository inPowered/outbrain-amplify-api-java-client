package com.xy1m.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "operationStatus",
        "url",
        "promotedLink"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotedLinkResponse {
    private OperationStatus operationStatus;
    private String url;
    private PromotedLink promotedLink;

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PromotedLink getPromotedLink() {
        return promotedLink;
    }

    public void setPromotedLink(PromotedLink promotedLink) {
        this.promotedLink = promotedLink;
    }
}
