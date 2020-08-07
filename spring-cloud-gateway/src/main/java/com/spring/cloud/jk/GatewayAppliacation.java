package com.spring.cloud.jk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.spring.cloud")
public class GatewayAppliacation {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAppliacation.class,args);
    }

    @Bean
    public KeyResolver hostAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
