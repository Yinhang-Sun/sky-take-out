package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid; //Appid of the mini program
    private String secret; //Secret key of the applet
    private String mchid; //Merchant number
    private String mchSerialNo; //Certificate serial number of merchant API certificate
    private String privateKeyFilePath; //Merchant private key file
    private String apiV3Key; //Key for certificate decryption
    private String weChatPayCertFilePath; //Platform certificate
    private String notifyUrl; //Callback address for successful payment
    private String refundNotifyUrl; //Callback address for successful refund

}
