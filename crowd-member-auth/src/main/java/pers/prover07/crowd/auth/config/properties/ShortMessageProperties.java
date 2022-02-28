package pers.prover07.crowd.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by Prover07
 * @classname ShortMessageProperties
 * @description TODO
 * @date 2022/2/27 14:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {

    private String host;
    private String path;
    private String method;
    private String appcode;
    private String templateId;
    private Long expiredMinute;

    public Long getExpiredMinute() {
        return expiredMinute * 60;
    }
}
