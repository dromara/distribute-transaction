package io.transaction.msg.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@EnableTransactionManagement
@Configuration
@MapperScan(value = {"io.transaction.msg.order.mapper"})
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
