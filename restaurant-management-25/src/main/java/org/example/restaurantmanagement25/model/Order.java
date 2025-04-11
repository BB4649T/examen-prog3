package org.example.restaurantmanagement25.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String customerName;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private List<DishOrder> dishOrders;
    private BigDecimal totalAmount;

}