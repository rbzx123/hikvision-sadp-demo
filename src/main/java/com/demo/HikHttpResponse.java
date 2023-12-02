package com.demo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * xmlns : http://www.hikvision.com/ver20/XMLSchema
 * version : 2
 * requestURL : /ISAPI/System/activate
 * statusCode : 1
 * statusString : OK
 * subStatusCode : ok
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ResponseStatus")
public class HikHttpResponse {
    @JacksonXmlProperty(localName = "requestURL")
    private String requestURL;
    @JacksonXmlProperty(localName = "statusCode")
    private int statusCode;
    @JacksonXmlProperty(localName = "statusString")
    private String statusString;
    @JacksonXmlProperty(localName = "subStatusCode")
    private String subStatusCode;
}

