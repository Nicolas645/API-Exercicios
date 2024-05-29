package org.serratec.ecommerce.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumverifyConfig {
    @Value("${numverify.access_key}")
    private String accessKey;

    @Value("${numverify.url}")
    private String url;

    public String getAccessKey() {
        return accessKey;
    }

    public String getUrl() {
        return url;
    }
}
