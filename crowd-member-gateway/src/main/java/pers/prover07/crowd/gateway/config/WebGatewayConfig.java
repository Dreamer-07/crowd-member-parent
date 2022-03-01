package pers.prover07.crowd.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author by Prover07
 * @classname WebGatewayConfig
 * @description TODO
 * @date 2022/3/1 11:01
 */
@Configuration
public class WebGatewayConfig {

    @Bean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

}
