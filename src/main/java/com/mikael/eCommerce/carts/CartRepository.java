package com.mikael.eCommerce.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByUser_Id(Long userId);

    void deleteByUser_Id(Long userId);

    Optional<CartEntity> findByUser_IdAndProduct_Id(Long userId, Long productId);
}
