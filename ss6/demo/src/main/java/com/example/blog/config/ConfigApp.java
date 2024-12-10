package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;


@Configuration
public class ConfigApp {
    /*
    SpringSecurityDialect là một thành phần tích hợp giữa Spring Security và Thymeleaf,
    giúp bạn dễ dàng kiểm soát và hiển thị nội dung trong các template Thymeleaf dựa trên quyền hạn và trạng thái xác thực của người dùng.
    Dưới đây là giải thích chi tiết về mục đích và cách sử dụng SpringSecurityDialect trong ứng dụng Spring Boot của bạn.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
