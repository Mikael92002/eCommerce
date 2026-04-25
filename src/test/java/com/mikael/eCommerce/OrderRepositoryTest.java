package com.mikael.eCommerce;

import com.mikael.eCommerce.orders.OrderRepository;
import com.mikael.eCommerce.users.UserEntity;
import com.mikael.eCommerce.users.UserRepository;
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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(OrderRepositoryTest.H2ConsoleConfig.class)
public class OrderRepositoryTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

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
    void seedDatabase(){
        UserEntity user = new UserEntity();
        user.setEmail("mikael@gmail.com");
        user.setPassword("123");
        user.setRole("admin");
        user.setUsername("mikael92002");
        this.userRepository.save(user);
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