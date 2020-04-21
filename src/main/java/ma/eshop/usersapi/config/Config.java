package ma.eshop.usersapi.config;


import ma.eshop.usersapi.filters.CorsFilter;
import ma.eshop.usersapi.services.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    JwtService jwtService(){
        return new JwtService();
    }

    @Bean
    MyUserDetailsService myUserDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    OrdersService ordersService(){
        return new OrdersService();
    }

    @Bean
    WishListsService wishListsService(){
        return new WishListsService();
    }

    @Bean
    UsersService usersService(){
        return new UsersService();
    }

    @Bean
    ProductsService productsService(){
        return new ProductsService();
    }

    @Bean
    CartsService cartsService(){
        return new CartsService();
    }

    @Bean
    CorsFilter corsFilter(){
        return new CorsFilter();
    }

}
