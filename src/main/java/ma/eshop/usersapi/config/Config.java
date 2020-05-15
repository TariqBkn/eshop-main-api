package ma.eshop.usersapi.config;


import ma.eshop.usersapi.filters.CorsFilter;
import ma.eshop.usersapi.filters.EntryPointFilter;
import ma.eshop.usersapi.filters.JwtRequestFilter;
 import ma.eshop.usersapi.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "ma.eshop.usersapi.repositories")
public class Config {

//    @Value("${elasticsearch.cluster.name:elasticsearch}")
//    private String clusterName;
//    @Bean
//    public Client client() throws UnknownHostException {
//        Settings elasticsearchSettings = Settings.builder()
//                .put("client.transport.sniff", true)
//                .put("path.home", elasticsearchHome)
//                .put("cluster.name", clusterName).build();
//        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
//        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//        return client;
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
//        return new ElasticsearchTemplate(client());
//    }

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
}
