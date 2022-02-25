package pers.prover07.crowd.mysql.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Prover07
 * @classname MybatisPlusConfig
 * @description TODO
 * @date 2022/2/25 19:31
 */
@Configuration
@MapperScan("pers.prover07.crowd.**.mapper")
public class MybatisPlusConfig {

}
