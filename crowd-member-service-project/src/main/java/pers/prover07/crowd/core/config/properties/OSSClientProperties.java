package pers.prover07.crowd.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by Prover07
 * @classname OSSClientProperties
 * @description TODO
 * @date 2022/3/1 14:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss.client")
public class OSSClientProperties {

    private String endpoint;

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

}
