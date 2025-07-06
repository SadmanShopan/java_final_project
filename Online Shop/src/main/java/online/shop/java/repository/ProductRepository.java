package online.shop.java.repository;

import com.retail.supershop.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get products expiring within 'days' and still available
    @Query("SELECT p FROM Product p WHERE p.expiryDate IS NOT NULL AND p.expiryDate BETWEEN :start AND :end AND p.available = 'Yes'")
    List<Product> findExpiringProducts(@Param("start") LocalDate start, @Param("end") LocalDate end);

    // Get total value by category (price * quantity)
    @Query("SELECT p.category, SUM(p.price * p.quantity) FROM Product p WHERE p.available = 'Yes' GROUP BY p.category")
    List<Object[]> getTotalValueByCategory();

    // Get products grouped by category (only available ones)
    @Query("SELECT p FROM Product p WHERE p.available = 'Yes'")
    List<Product> findAvailableProducts();
}
