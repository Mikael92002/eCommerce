package com.mikael.eCommerce;

import com.mikael.eCommerce.orderItems.OrderItemEntity;
import com.mikael.eCommerce.orderItems.OrderItemRepository;
import com.mikael.eCommerce.orders.OrderEntity;
import com.mikael.eCommerce.orders.OrderRepository;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.payments.PaymentRepository;
import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.products.ProductRepository;
import com.mikael.eCommerce.users.UserEntity;
import com.mikael.eCommerce.users.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.h2.server.web.JakartaWebServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(OrderRepositoryTest.H2ConsoleConfig.class)
public class OrderRepositoryTest {

    @LocalServerPort
    private int port = 3000;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${test.working}")
    private String testWorking;

    @TestConfiguration
    static class H2ConsoleConfig {
        @Bean
        public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
            ServletRegistrationBean<JakartaWebServlet> registration =
                    new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
            registration.addInitParameter("webAllowOthers", "true");
            registration.setLoadOnStartup(1);
            return registration;
        }
    }

    @BeforeEach
    @Transactional
    void seedDatabase() {
        UserEntity user = new UserEntity();
        user.setEmail("mikael@gmail.com");
        user.setPassword("123");
        user.setRole("admin");
        user.setUsername("mikael92002");
        this.userRepository.save(user);

        ProductEntity product = new ProductEntity();
        product.setName("Bacon cheese");
        product.setStockQuantity(100);
        product.setPrice(new BigDecimal("20.00"));
        this.productRepository.save(product);

        // orderItem must have unique constraint of: product, order:
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setAddress("123 newsbury lane");
        order.setOrderStatus("In transit");

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(2)));
        orderItem.setOrder(order);

        order.setOrderItems(new ArrayList<>(Arrays.asList(orderItem)));
        // in actual insertion, use a helper to iterate over each orderItem
        // and get total price (no tax) then set:
        order.setAmount(orderItem.getPrice());

        PaymentEntity payment = new PaymentEntity();
        // get total price (tax included) from stripe:
        payment.setAmount(BigDecimal.valueOf(45.30));
        payment.setCurrency("$");
        payment.setStatus("Pending");
        payment.setUser(user);
        payment.setOrder(order);
        payment.setExternalTransactionId("sh_something");

        order.setPayment(payment);

        this.orderRepository.save(order);

    }

    @Test
    void testQueryMethodNaming() throws Exception {
        System.out.println("Profile loads: " + testWorking);
        System.out.println("===========================================");
        // NOTE: use a trailing slash!
        System.out.println("H2 Console URL: http://localhost:" + port + "/h2-console/");
        System.out.println("JDBC URL: jdbc:h2:mem:testdb");
        System.out.println("Username: sa");
        System.out.println("Password: (leave empty)");
        System.out.println("===========================================");

        assertDoesNotThrow(() -> orderRepository.findByUser_Id(1L));

        System.out.println("\nPress Enter to stop the test...");
        System.in.read();
    }
}