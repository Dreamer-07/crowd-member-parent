package pers.prover07.crowd.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author by Prover07
 * @classname CrowdCoreServiceApplication
 * @description TODO
 * @date 2022/3/1 14:32
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"pers.prover07.crowd"})
public class CrowdCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdCoreServiceApplication.class, args);
    }

}
