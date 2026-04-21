package com.mikael.eCommerce.carts;

import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_entity", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"user_id", "product_id"} // user id and product id come from foreign key
        )
})
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key user_id points to id column in UserEntity
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public CartEntity(){

    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
