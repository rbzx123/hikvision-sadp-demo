package com.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ProbeMatch")
public class UpdateResult {
    @JacksonXmlProperty(localName = "Uuid")
    private String Uuid;
    @JacksonXmlProperty(localName = "Types")
    private String Types;
    @JacksonXmlProperty(localName = "Result")
    private String Result;
    @JacksonXmlProperty(localName = "DeviceType")
    private int DeviceType;
    @JacksonXmlProperty(localName = "DeviceDescription")
    private String DeviceDescription;
    @JacksonXmlProperty(localName = "DeviceSN")
    private String DeviceSN;
    @JacksonXmlProperty(localName = "CommandPort")
    private int CommandPort;
    @JacksonXmlProperty(localName = "HttpPort")
    private int HttpPort;
    @JacksonXmlProperty(localName = "MAC")
    private String MAC;
    @JacksonXmlProperty(localName = "IPv4Address")
    private String IPv4Address;
    @JacksonXmlProperty(localName = "IPv4SubnetMask")
    private String IPv4SubnetMask;
    @JacksonXmlProperty(localName = "IPv4Gateway")
    private String IPv4Gateway;
    @JacksonXmlProperty(localName = "IPv6Address")
    private String IPv6Address;
    @JacksonXmlProperty(localName = "IPv6Gateway")
    private String IPv6Gateway;
    @JacksonXmlProperty(localName = "IPv6MaskLen")
    private int IPv6MaskLen;
    @JacksonXmlProperty(localName = "DHCP")
    private boolean DHCP;
    @JacksonXmlProperty(localName = "AnalogChannelNum")
    private int AnalogChannelNum;
    @JacksonXmlProperty(localName = "DigitalChannelNum")
    private int DigitalChannelNum;
    @JacksonXmlProperty(localName = "SoftwareVersion")
    private String SoftwareVersion;
    @JacksonXmlProperty(localName = "DSPVersion")
    private String DSPVersion;
    @JacksonXmlProperty(localName = "BootTime")
    private String BootTime;
    @JacksonXmlProperty(localName = "Encrypt")
    private boolean Encrypt;
    @JacksonXmlProperty(localName = "ResetAbility")
    private boolean ResetAbility;
    @JacksonXmlProperty(localName = "DiskNumber")
    private int DiskNumber;
    @JacksonXmlProperty(localName = "Activated")
    private boolean Activated;
    @JacksonXmlProperty(localName = "PasswordResetAbility")
    private boolean PasswordResetAbility;
    @JacksonXmlProperty(localName = "PasswordResetModeSecond")
    private boolean PasswordResetModeSecond;
    @JacksonXmlProperty(localName = "DetailOEMCode")
    private int DetailOEMCode;
    @JacksonXmlProperty(localName = "SupportSecurityQuestion")
    private boolean SupportSecurityQuestion;
    @JacksonXmlProperty(localName = "SupportHCPlatform")
    private boolean SupportHCPlatform;
    @JacksonXmlProperty(localName = "HCPlatformEnable")
    private String HCPlatformEnable;
    @JacksonXmlProperty(localName = "IsModifyVerificationCode")
    private boolean IsModifyVerificationCode;
    @JacksonXmlProperty(localName = "Salt")
    private String Salt;
    @JacksonXmlProperty(localName = "DeviceLock")
    private boolean DeviceLock;
    @JacksonXmlProperty(localName = "SDKServerStatus")
    private boolean SDKServerStatus;
    @JacksonXmlProperty(localName = "SDKOverTLSServerStatus")
    private boolean SDKOverTLSServerStatus;
    @JacksonXmlProperty(localName = "SDKOverTLSPort")
    private int SDKOverTLSPort;
    @JacksonXmlProperty(localName = "SupportMailBox")
    private boolean SupportMailBox;
    @JacksonXmlProperty(localName = "supportEzvizUnbind")
    private boolean supportEzvizUnbind;
}
