package pers.prover07.crowd.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author by Prover07
 * @classname CrowdEurekaApplication
 * @description TODO
 * @date 2022/2/25 15:04
 */
@SpringBootApplication
@EnableEurekaServer
public class CrowdEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdEurekaApplication.class, args);
    }

}
