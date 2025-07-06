package online.shop.java.service;

import com.retail.supershop.app.entity.Product;
import com.retail.supershop.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Add a new product
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    // Update an existing product
    public boolean updateProduct(String id, Product updatedProduct) {
        return productRepository.findById(Long.valueOf(id)).map(existingProduct -> {
            if (updatedProduct.getName() != null) existingProduct.setName(updatedProduct.getName());
            if (updatedProduct.getCategory() != null) existingProduct.setCategory(updatedProduct.getCategory());
            if (updatedProduct.getPrice().compareTo(BigDecimal.ZERO) != 0) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }

            if (updatedProduct.getQuantity() != 0) existingProduct.setQuantity(updatedProduct.getQuantity());
            if (updatedProduct.getExpiryDate() != null) existingProduct.setExpiryDate(updatedProduct.getExpiryDate());
            if (updatedProduct.getDiscount().compareTo(BigDecimal.ZERO) != 0) {
                existingProduct.setDiscount(updatedProduct.getDiscount());
            }
            productRepository.save(existingProduct);
            return true;
        }).orElse(false);
    }

    // Get products expiring within X days and apply a discount
    public List<Product> getExpiringProductsWithDiscount(int days, double discountPercent) {
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(days);
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> product.getExpiryDate() != null &&
                        !product.getExpiryDate().isBefore(today) &&
                        !product.getExpiryDate().isAfter(maxDate) &&
                        product.getQuantity() > 0)
                .peek(product -> product.setDiscount(BigDecimal.valueOf(discountPercent)))
                .toList();
    }
}
