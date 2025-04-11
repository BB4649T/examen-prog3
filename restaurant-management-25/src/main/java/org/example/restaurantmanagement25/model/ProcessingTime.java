package org.example.restaurantmanagement25.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingTime {
    private String dishId;
    private String dishName;
    private double processingTime;
    private TimeUnit timeUnit;
    private StatisticType statisticType;
}
