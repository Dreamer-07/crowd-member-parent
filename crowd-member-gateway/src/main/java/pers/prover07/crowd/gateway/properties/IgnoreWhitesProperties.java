package pers.prover07.crowd.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author by Prover07
 * @classname IgnoreWhitesProperties
 * @description TODO
 * @date 2022/2/28 17:39
 */
@Component
@ConfigurationProperties(prefix = "ignore")
@Data
public class IgnoreWhitesProperties {

    private HashSet<String> whites;

}
