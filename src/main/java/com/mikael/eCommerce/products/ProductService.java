package com.mikael.eCommerce.products;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> findByStockQuantityGreaterThan(Integer amount) {
        List<ProductEntity> productEntityList = this.productRepository.findByStockQuantityGreaterThan(amount);
        return productEntityList.stream().map(product -> productMapper.toDTO(product)).toList();
    }

    public List<ProductDTO> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable) {
        List<ProductEntity> productEntityList = this.productRepository.findByPriceBetween(min, max, pageable);
        return productEntityList.stream().map(product -> productMapper.toDTO(product)).toList();
    }

    public List<ProductDTO> findByNameContainingIgnoreCase(String keyword) {
        List<ProductEntity> productEntityList = this.productRepository.findByNameContainingIgnoreCase(keyword);
        return productEntityList.stream().map(product -> productMapper.toDTO(product)).toList();
    }

    public List<ProductDTO> findByStockQuantityLessThan(Integer threshold) {
        List<ProductEntity> productEntityList = this.productRepository.findByStockQuantityLessThan(threshold);
        return productEntityList.stream().map(product -> productMapper.toDTO(product)).toList();
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        boolean exists = this.productRepository.existsByName(productDTO.name());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CREATE FAILED: Product already exists");
        }
        ProductEntity product = productMapper.toEntity(productDTO);
        product.setId(null); // if front end forgets to set id to null in POST request
        this.productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, String name, BigDecimal price, Integer stockQuantity) {
        ProductEntity productEntity = this.productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UPDATE FAILED: Product not found"));
        productEntity.setName(name);
        productEntity.setPrice(price);
        productEntity.setStockQuantity(stockQuantity);

        // MAP TO DTO:
        this.productRepository.save(productEntity);
        return productMapper.toDTO(productEntity);
    }

    @Transactional
    public void deleteProduct(Long id) {
        boolean exists = this.productRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DELETE FAILED: Product not found");
        }
        this.productRepository.deleteById(id);
    }
}
