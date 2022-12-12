package kuzmindenis.test.project.controller;

import kuzmindenis.test.project.dto.ProductResponse;
import kuzmindenis.test.project.model.Product;
import kuzmindenis.test.project.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductResponse> getProducts(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Product> products = productService.getProducts(query, PageRequest.of(page, limit));
        return products.stream().map(ProductController::toProductResponse).collect(Collectors.toList());
    }

    private static ProductResponse toProductResponse(Product product){
        return new ProductResponse(
                product.getId().programId,
                product.getId().productId,
                product.getName(),
                product.getModel(),
                product.getPrice(),
                product.getBuyUrl()
        );
    }
}
