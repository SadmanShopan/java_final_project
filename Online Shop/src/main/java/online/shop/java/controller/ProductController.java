package online.shop.java.controller;

import com.retail.supershop.app.entity.Product;
import com.retail.supershop.app.service.ProductService;
import com.retail.supershop.app.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    /*@PostMapping("/products/bulk")
    public void addProductsBulk(@RequestBody List<Product> products) {
        productService.addProductsBulk(products);
    }*/

    @PutMapping("/products/{id}")
    public String updateProduct(@PathVariable String id, @RequestBody Product product) {
        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            return "Product updated successfully.";
        } else {
            return "Product with id " + id + " not found.";
        }
    }

    @GetMapping("/products/expiring")
    public List<Product> getExpiringProducts() {
        int nextDays = 7;
        double discountPercent = 20.0;
        return productService.getExpiringProductsWithDiscount(nextDays, discountPercent);
    }

    @GetMapping("/reports/total-value-by-category")
    public Map<String, Double> getTotalValueByCategory() {
        return reportService.getTotalValueByCategory();
    }

    @GetMapping("/reports/products-by-category")
    public Map<String, List<Product>> getProductsByCategoryWithDiscount() {
        return reportService.getProductsByCategoryWithDiscount();
    }
}
