package org.example.restaurantmanagement25.endpoint.rest;

import org.example.restaurantmanagement25.model.BestSellingDish;
import org.example.restaurantmanagement25.model.ProcessingTime;
import org.example.restaurantmanagement25.model.StatisticType;
import org.example.restaurantmanagement25.model.TimeUnit;
import org.example.restaurantmanagement25.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardRestController {
    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/bestSales")
    public List<BestSellingDish> getBestSellingDishes(
            @RequestParam int limit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return dashboardService.getBestSellingDishes(
                limit,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
    }

    @GetMapping("/dishes/{id}/processingTime")
    public ProcessingTime getProcessingTime(
            @PathVariable int id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "SECONDS") TimeUnit timeUnit,
            @RequestParam(required = false, defaultValue = "AVERAGE") StatisticType statisticType) {

        return dashboardService.getProcessingTime(
                id,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59),
                timeUnit,
                statisticType
        );
    }
}
