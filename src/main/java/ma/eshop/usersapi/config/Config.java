package ma.eshop.usersapi.config;


import ma.eshop.usersapi.filters.CorsFilter;
import ma.eshop.usersapi.filters.EntryPointFilter;
import ma.eshop.usersapi.filters.JwtRequestFilter;
 import ma.eshop.usersapi.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Config {

    @Bean
    JwtUtilService jwtUtilService(){
        return new JwtUtilService();
    }

    @Bean
    CorsFilter corsFilter(){
        return new CorsFilter();
    }

    @Bean
    JwtRequestFilter jwtRequestFilter(){
        return new JwtRequestFilter();
    }

    @Bean
    EntryPointFilter entryEntryPoint(){
        return new EntryPointFilter();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/products/images/**")
                        .addResourceLocations("classpath:/static/images/products/");
            }
        };
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
