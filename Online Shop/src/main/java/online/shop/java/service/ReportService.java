package online.shop.java.service;

import com.retail.supershop.app.entity.Product;
import com.retail.supershop.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ProductRepository productRepository;

    public ReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Report 1: Total price (price * quantity) by category
    public Map<String, Double> getTotalValueByCategory() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(product ->
                                product.getPrice()
                                        .multiply(BigDecimal.valueOf(product.getQuantity()))
                                        .doubleValue()
                        )
                ));
    }

    // Report 2: Available products grouped by category
    public Map<String, List<Product>> getProductsByCategoryWithDiscount() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.groupingBy(Product::getCategory));
    }
}
