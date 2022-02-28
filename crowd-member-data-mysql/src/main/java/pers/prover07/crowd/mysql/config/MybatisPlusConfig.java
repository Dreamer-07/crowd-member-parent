package pers.prover07.crowd.mysql.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author by Prover07
 * @classname MybatisPlusConfig
 * @description TODO
 * @date 2022/2/25 19:31
 */
@Configuration
@MapperScan("pers.prover07.crowd.**.mapper")
public class MybatisPlusConfig {

    @PostConstruct
    public void updateDruidConfig() {
        // 修改 druid 配置
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
