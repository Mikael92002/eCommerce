package com.mikael.eCommerce.products;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByStockQuantityGreaterThan(Integer amount);

    List<ProductEntity> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

    List<ProductEntity> findByStockQuantityLessThan(Integer threshold);

    boolean existsByName(String name);
}
