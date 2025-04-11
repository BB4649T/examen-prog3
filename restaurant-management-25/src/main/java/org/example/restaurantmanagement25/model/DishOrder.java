package org.example.restaurantmanagement25.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DishOrder {
    private String id;
    private String orderId;
    private String dishId;
    private int quantity;
    private List<DishOrderStatus> statusHistory;
    private BigDecimal currentPrice;
}

