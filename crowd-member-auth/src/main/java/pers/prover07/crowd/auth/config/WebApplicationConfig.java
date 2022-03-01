package pers.prover07.crowd.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by Prover07
 * @classname WebApplicationConfig
 * @description TODO
 * @date 2022/2/27 13:20
 */
@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/register").setViewName("register");
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/page/member_center").setViewName("member-center");
    }
}
