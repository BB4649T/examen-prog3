package org.example.restaurantmanagement25.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class BestSellingDish {
    private String dishId;
    private String name;
    private int totalQuantitySold;
    private BigDecimal totalRevenue;
}
