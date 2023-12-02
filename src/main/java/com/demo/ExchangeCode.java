package com.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ProbeMatch")
public class ExchangeCode {

    @JacksonXmlProperty(localName = "Uuid")
    private String Uuid;
    @JacksonXmlProperty(localName = "Types")
    private String Types;
    @JacksonXmlProperty(localName = "Result")
    private String Result;
    @JacksonXmlProperty(localName = "Code")
    private String Code;
}

