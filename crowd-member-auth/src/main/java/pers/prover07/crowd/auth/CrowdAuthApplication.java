package pers.prover07.crowd.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author by Prover07
 * @classname CrowdAuthApplication
 * @description TODO
 * @date 2022/2/25 20:53
 */
@SpringBootApplication(scanBasePackages = "pers.prover07.crowd")
@EnableEurekaClient
@EnableRedisHttpSession
public class CrowdAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdAuthApplication.class, args);
    }

}
