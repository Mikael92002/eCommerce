package com.mikael.eCommerce.carts;

import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.products.ProductRepository;
import com.mikael.eCommerce.users.UserEntity;
import com.mikael.eCommerce.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional
    public CartDTO createCart(CartDTO cart, Long userId, Long productId) {
        if (cartRepository.findByUser_IdAndProduct_Id(userId, productId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart already exists");
        }
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        CartEntity createdCart = this.cartRepository.save(this.cartMapper.toEntity(cart, user, product));
        return this.cartMapper.toDTO(createdCart);
    }

    public List<CartDTO> findByUser_Id(Long userId) {
        return cartRepository.findByUser_Id(userId).stream().map(cart -> cartMapper.toDTO(cart)).toList();
    }

    @Transactional
    public CartDTO updateCartQuantity(int newQuantity, Long userId, Long productId) {
        CartEntity cart = cartRepository.findByUser_IdAndProduct_Id(userId, productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart does not exist"));
        cart.setQuantity(newQuantity);
        return cartMapper.toDTO(cart);
    }

    @Transactional
    public void deleteByUser_Id(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (exists) {
            cartRepository.deleteByUser_Id(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
