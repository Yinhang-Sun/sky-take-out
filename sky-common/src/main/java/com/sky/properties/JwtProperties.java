package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * Configuration related to jwt token generation by management-side employees
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * Configuration related to jwt token generation by WeChat users on the client side
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
