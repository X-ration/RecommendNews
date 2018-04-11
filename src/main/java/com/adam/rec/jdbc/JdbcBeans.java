package com.adam.rec.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author adam
 * 创建于 2018-04-11 22:05.
 */
@Configuration
public class JdbcBeans {

    @Bean
    public JdbcUtil jdbcUtil() {
        JdbcUtil jdbcUtil = new JdbcUtil();
        jdbcUtil.initAll();
        return jdbcUtil;
    }

}
