package pers.prover07.crowd.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author by Prover07
 * @classname CrowdGatewayApplication
 * @description TODO
 * @date 2022/2/25 21:47
 */
@EnableEurekaClient
@SpringBootApplication
public class CrowdGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdGatewayApplication.class, args);
    }

}
