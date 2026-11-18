package com.prashanth.ecommerce.payment;

import com.prashanth.feignconfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}",
        configuration = FeignClientConfig.class
)
public interface PaymentClient {

    @PostMapping
    Integer requestOrderPayment(
            @RequestBody PaymentRequest paymentRequest
    );

}
