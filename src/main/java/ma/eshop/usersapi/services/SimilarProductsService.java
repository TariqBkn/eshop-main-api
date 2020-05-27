package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.SimilarProduct;
import ma.eshop.usersapi.models.SimilarProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SimilarProductsService {
    @Inject
    private ProductsService productsService;

    @Value("${productSimilarityCalculatorApi}")
    private String similarityCalculatorAPIUrl;

    public List<SimilarProduct> getSimilarProductsOf(int productId) throws UnsupportedEncodingException {

        WebClient webClient = buildWebClientOfSimilarityCalculatorApiForSimilarProductsOf(productId);

        List<SimilarProduct> similarProducts = getListOfSimilarProducts(webClient);

        return similarProducts;
    }

    private List<SimilarProduct> getListOfSimilarProducts(WebClient webClient) {
        return webClient.get()
           .retrieve()
           .toEntityList(SimilarProductDTO.class)
           .block()
           .getBody()
           .stream()
           .filter(similarProductDTO -> productsService.existsById(similarProductDTO.getProductId()))
           .map(similarProductDTO -> new SimilarProduct(productsService.findById(similarProductDTO.getProductId()).get(), similarProductDTO.getSimilarityPercentage()))
           .collect(Collectors.toList());
    }

    private WebClient buildWebClientOfSimilarityCalculatorApiForSimilarProductsOf(int productId) {
        return WebClient
             .builder()
             .baseUrl(similarityCalculatorAPIUrl.strip()+"/products/"+productId+"/similar") //getResourceLocationOfProduct(productId)
             .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
             .build();
    }

}
