package org.example.restaurantmanagement25.service;

import org.example.restaurantmanagement25.model.BestSellingDish;
import org.example.restaurantmanagement25.model.ProcessingTime;
import org.example.restaurantmanagement25.model.StatisticType;
import org.example.restaurantmanagement25.model.TimeUnit;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardService {
    List<BestSellingDish> getBestSellingDishes(int limit, LocalDateTime startDate, LocalDateTime endDate);
    ProcessingTime getProcessingTime(String dishId, LocalDateTime startDate, LocalDateTime endDate,
                                     TimeUnit timeUnit, StatisticType statisticType);

    ProcessingTime getProcessingTime(int dishId, LocalDateTime startDate, LocalDateTime endDate,
                                     TimeUnit timeUnit, StatisticType statisticType);
}