package com.demo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ScanResultList")
@AllArgsConstructor
@NoArgsConstructor
public class ScanResultList implements Serializable {

    @JacksonXmlProperty(localName = "ProbeMatch")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ProbeMatch> ProbeMatch;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProbeMatch implements Serializable {

        
        @JacksonXmlProperty(localName = "Uuid")
        private String Uuid;

        
        @JacksonXmlProperty(localName = "Types")
        private String Types;

        
        @JacksonXmlProperty(localName = "DeviceType")
        private Integer DeviceType;

        
        @JacksonXmlProperty(localName = "DeviceDescription")
        private String DeviceDescription;

        
        @JacksonXmlProperty(localName = "DeviceSN")
        private String DeviceSN;

        
        @JacksonXmlProperty(localName = "CommandPort")
        private Integer CommandPort;

        
        @JacksonXmlProperty(localName = "HttpPort")
        private Integer HttpPort;

        
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
        private Integer IPv6MaskLen;

        
        @JacksonXmlProperty(localName = "DHCP")
        private Boolean DHCP;

        
        @JacksonXmlProperty(localName = "AnalogChannelNum")
        private Integer AnalogChannelNum;

        
        @JacksonXmlProperty(localName = "DigitalChannelNum")
        private Integer DigitalChannelNum;

        
        @JacksonXmlProperty(localName = "SoftwareVersion")
        private String SoftwareVersion;

        
        @JacksonXmlProperty(localName = "DSPVersion")
        private String DSPVersion;

        
        @JacksonXmlProperty(localName = "BootTime")
        private String BootTime;

        
        @JacksonXmlProperty(localName = "Encrypt")
        private Boolean Encrypt;

        
        @JacksonXmlProperty(localName = "ResetAbility")
        private Boolean ResetAbility;

        
        @JacksonXmlProperty(localName = "DiskNumber")
        private Integer DiskNumber;

        
        @JacksonXmlProperty(localName = "Activated")
        private Boolean Activated;

        
        @JacksonXmlProperty(localName = "PasswordResetAbility")
        private Boolean PasswordResetAbility;

        
        @JacksonXmlProperty(localName = "PasswordResetModeSecond")
        private Boolean PasswordResetModeSecond;

        
        @JacksonXmlProperty(localName = "DetailOEMCode")
        private Integer DetailOEMCode;

        
        @JacksonXmlProperty(localName = "SupportSecurityQuestion")
        private Boolean SupportSecurityQuestion;

        
        @JacksonXmlProperty(localName = "SupportHCPlatform")
        private Boolean SupportHCPlatform;

        
        @JacksonXmlProperty(localName = "HCPlatformEnable")
        private String HCPlatformEnable;

        
        @JacksonXmlProperty(localName = "IsModifyVerificationCode")
        private Boolean IsModifyVerificationCode;

        
        @JacksonXmlProperty(localName = "Salt")
        private String Salt;

        
        @JacksonXmlProperty(localName = "DeviceLock")
        private Boolean DeviceLock;

        
        @JacksonXmlProperty(localName = "SDKServerStatus")
        private Boolean SDKServerStatus;

        
        @JacksonXmlProperty(localName = "SDKOverTLSServerStatus")
        private Boolean SDKOverTLSServerStatus;

        
        @JacksonXmlProperty(localName = "SDKOverTLSPort")
        private Integer SDKOverTLSPort;

        
        @JacksonXmlProperty(localName = "SupportMailBox")
        private Boolean SupportMailBox;

        
        @JacksonXmlProperty(localName = "supportEzvizUnbind")
        private Boolean supportEzvizUnbind;

    }
}