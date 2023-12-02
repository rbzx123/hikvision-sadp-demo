package com.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "DeviceInfo")
public class DeviceInfo implements Serializable {


    @JacksonXmlProperty(localName = "deviceName")
    private String deviceName;


    @JacksonXmlProperty(localName = "deviceID")
    private String deviceID;


    @JacksonXmlProperty(localName = "deviceDescription")
    private String deviceDescription;


    @JacksonXmlProperty(localName = "deviceLocation")
    private String deviceLocation;


    @JacksonXmlProperty(localName = "systemContact")
    private String systemContact;


    @JacksonXmlProperty(localName = "model")
    private String model;


    @JacksonXmlProperty(localName = "serialNumber")
    private String serialNumber;


    @JacksonXmlProperty(localName = "macAddress")
    private String macAddress;


    @JacksonXmlProperty(localName = "firmwareVersion")
    private String firmwareVersion;


    @JacksonXmlProperty(localName = "firmwareReleasedDate")
    private String firmwareReleasedDate;


    @JacksonXmlProperty(localName = "encoderVersion")
    private String encoderVersion;


    @JacksonXmlProperty(localName = "encoderReleasedDate")
    private String encoderReleasedDate;


    @JacksonXmlProperty(localName = "bootVersion")
    private String bootVersion;


    @JacksonXmlProperty(localName = "bootReleasedDate")
    private Integer bootReleasedDate;


    @JacksonXmlProperty(localName = "hardwareVersion")
    private String hardwareVersion;


    @JacksonXmlProperty(localName = "deviceType")
    private String deviceType;


    @JacksonXmlProperty(localName = "telecontrolID")
    private Integer telecontrolID;


    @JacksonXmlProperty(localName = "supportBeep")
    private Boolean supportBeep;


    @JacksonXmlProperty(localName = "supportVideoLoss")
    private Boolean supportVideoLoss;


    @JacksonXmlProperty(localName = "firmwareVersionInfo")
    private String firmwareVersionInfo;

}