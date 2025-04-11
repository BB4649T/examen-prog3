package org.example.restaurantmanagement25.endpoint.rest;

import org.example.restaurantmanagement25.model.StockMovementType;
import org.example.restaurantmanagement25.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class StockMovementRest {
    private Long id;
    private Double quantity;
    private Unit unit;
    private StockMovementType type;
    private Instant creationDatetime;
}
