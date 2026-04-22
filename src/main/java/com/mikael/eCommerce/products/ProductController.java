package com.mikael.eCommerce.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<ProductDTO> getProductsFromName(@RequestParam String name) {
        return this.productService.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/between")
    public Page<ProductDTO> getProductsBetweenPrice(@RequestParam(defaultValue = "0") BigDecimal low, @RequestParam(defaultValue = "100000") BigDecimal high, @PageableDefault(size = 20, sort = "price") Pageable pageable) {
        if (low.compareTo(high) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Low price cannot be higher than high price");
        }
        return this.productService.findByPriceBetween(low, high, pageable);
    }

    @GetMapping("/greater")
    public List<ProductDTO> getProductsWithStockGreaterThan(@RequestParam(defaultValue = "0") Integer amount) {
        return this.productService.findByStockQuantityGreaterThan(amount);
    }

    @GetMapping("/lesser")
    public List<ProductDTO> getProductsWithStockLessThan(@RequestParam(defaultValue = "100000") Integer amount) {
        return this.productService.findByStockQuantityLessThan(amount);
    }

    @PostMapping("/")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return this.productService.createProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        if(productDTO.id() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id not specified");
        }
        return this.productService.updateProduct(productDTO);
    }
}
