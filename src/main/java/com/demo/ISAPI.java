package com.demo;

public enum ISAPI {
    DEVICE_INFO("/ISAPI/system/deviceInfo", "设备信息"),
    ACTIVATE_DEVICE("/ISAPI/System/activate", "激活设备"),
    ACTIVATE_CHALLENGE("/ISAPI/Security/challenge", "RSA密文"),
    REBOOT("/ISAPI/System/reboot", "重启"),
    FACTORY_RESET_BASIC("/ISAPI/System/factoryReset?mode=basic", "基础用户配置恢复值"),
    FACTORY_RESET_FULL("/ISAPI/System/factoryReset?mode=full", "全部配置恢复出厂设置"),
    INTERFACES("/ISAPI/System/Network/interfaces/1", "网卡信息"),
    ;
    private final String URL;
    private final String desc;

    ISAPI(String URL, String desc) {
        this.URL = URL;
        this.desc = desc;
    }

    public String getURL() {
        return URL;
    }

    public String getDesc() {
        return desc;
    }
}
