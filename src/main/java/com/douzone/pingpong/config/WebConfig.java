package com.douzone.pingpong.config;


import com.douzone.pingpong.security.argumentresolver.LoginMemberArgumentResolver;
import com.douzone.pingpong.security.interceptor.LogInterceptor;
import com.douzone.pingpong.security.interceptor.LoginCheckInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:com/douzone/pingpong/config/WebConfig.properties")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/css/jumbotron-narrow.css");
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/members/new","/members/login", "/members/logout", "/css/**", "/*.ico", "/error");
    }

    // Resource Mapping(URL Magic Mapping)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(env.getProperty("fileupload.resourceMapping"))
                .addResourceLocations("file:"+env.getProperty("fileupload.uploadLocation"));
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내서버가 응답 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
        config.addAllowedOriginPattern("*"); // 모든 ip에 응답을 허용하겠다.
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 하겠다.

        List<String> exposeHeaders = new ArrayList<>();
        exposeHeaders.add("Authorization");
        config.setExposedHeaders(exposeHeaders);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
