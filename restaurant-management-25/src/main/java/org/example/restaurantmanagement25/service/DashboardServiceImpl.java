package org.example.restaurantmanagement25.service;

import org.example.restaurantmanagement25.model.*;
import org.example.restaurantmanagement25.dao.DashboardRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public List<BestSellingDish> getBestSellingDishes(int limit, LocalDateTime startDate, LocalDateTime endDate) {
        return dashboardRepository.findBestSellers(startDate, endDate).stream()
                .sorted(Comparator.comparingInt(BestSellingDish::getTotalQuantitySold).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public ProcessingTime getProcessingTime(String dishId, LocalDateTime startDate, LocalDateTime endDate,
                                            TimeUnit timeUnit, StatisticType statisticType) {
        List<DashboardRepository.ProcessingStat> stats =
                dashboardRepository.findProcessingStats(dishId, startDate, endDate);

        if (stats.isEmpty()) {
            return new ProcessingTime(dishId, "Unknown Dish", 0, timeUnit, statisticType);
        }

        double result = switch (statisticType) {
            case MINIMUM -> stats.stream()
                    .mapToLong(DashboardRepository.ProcessingStat::getProcessingSeconds)
                    .min().orElse(0L);
            case MAXIMUM -> stats.stream()
                    .mapToLong(DashboardRepository.ProcessingStat::getProcessingSeconds)
                    .max().orElse(0L);
            default -> stats.stream()
                    .mapToLong(DashboardRepository.ProcessingStat::getProcessingSeconds)
                    .average().orElse(0);
        };

        double convertedResult = convertTimeUnit(result, timeUnit);

        String dishName = dashboardRepository.findDishNameById(dishId)
                .orElse("Unknown Dish");

        return new ProcessingTime(dishId, dishName, convertedResult, timeUnit, statisticType);
    }

    @Override
    public ProcessingTime getProcessingTime(int dishId, LocalDateTime startDate, LocalDateTime endDate, TimeUnit timeUnit, StatisticType statisticType) {
        return null;
    }

    private double convertTimeUnit(double seconds, TimeUnit timeUnit) {
        return switch (timeUnit) {
            case MINUTES -> seconds / 60;
            case HOURS -> seconds / 3600;
            default -> seconds;
        };
    }
}
