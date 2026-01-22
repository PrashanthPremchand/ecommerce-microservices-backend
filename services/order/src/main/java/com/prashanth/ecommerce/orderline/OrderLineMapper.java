package com.prashanth.ecommerce.orderline;

import com.prashanth.ecommerce.order.Order;
import com.prashanth.ecommerce.order.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {

        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order.builder()
                                .id(orderLineRequest.id())
                                .build()
                )
                .build();

    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {

        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );

    }

}
