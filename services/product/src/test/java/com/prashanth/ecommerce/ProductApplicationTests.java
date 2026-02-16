package com.prashanth.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.autoconfigure.exclude=org.springframework.cloud.config.client.ConfigClientAutoConfiguration"
})
class ProductApplicationTests {

    @Test
    void contextLoads() {
    }

}
