package pers.prover07.crowd.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by Prover07
 * @classname WebApplicationConfig
 * @description TODO
 * @date 2022/3/1 17:55
 */
@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    /**
     * 添加视图控制器配置
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/project/page/start").setViewName("project-start");
        registry.addViewController("/project/page/start_step1").setViewName("start-step1");
        registry.addViewController("/project/page/start_step3").setViewName("start-step3");
    }
}
