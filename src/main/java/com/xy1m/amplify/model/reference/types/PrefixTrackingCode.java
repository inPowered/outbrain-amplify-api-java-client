package com.xy1m.amplify.model.reference.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "prefix",
        "encode"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrefixTrackingCode {
    private String prefix;
    private boolean encode;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PrefixTrackingCode{");
        sb.append("prefix='").append(prefix).append('\'');
        sb.append(", encode=").append(encode);
        sb.append('}');
        return sb.toString();
    }
}
