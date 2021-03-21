package de.jkueck.fire;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AlertMessage {

    public static final String PLACEHOLDER_CITY = "city";
    public static final String PLACEHOLDER_STREET = "street";
    public static final String PLACEHOLDER_OBJECT = "object";
    public static final String PLACEHOLDER_CATEGORY = "category";
    public static final String PLACEHOLDER_KEYWORD = "keyword";
    public static final String PLACEHOLDER_KEYWORD_TEXT = "keywordText";
    public static final String PLACEHOLDER_REMARK_1 = "remark1";
    public static final String PLACEHOLDER_REMARK_2 = "remark2";
    public static final String PLACEHOLDER_TIMESTAMP = "timestamp";

    private String city;

    private String street;

    private String object;

    private String category;

    private String keyword;

    private String keywordText;

    private String remark1;

    private String remark2;

    private String ric;

    private String completeMessage;

    private Date timestamp;

}
