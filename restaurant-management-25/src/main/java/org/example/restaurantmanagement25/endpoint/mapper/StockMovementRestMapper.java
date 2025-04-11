package org.example.restaurantmanagement25.endpoint.mapper;

import org.example.restaurantmanagement25.endpoint.rest.StockMovementRest;
import org.example.restaurantmanagement25.model.StockMovement;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StockMovementRestMapper implements Function<StockMovement, StockMovementRest> {

    @Override
    public StockMovementRest apply(StockMovement stockMovement) {
        return new StockMovementRest(stockMovement.getId(),
                stockMovement.getQuantity(),
                stockMovement.getUnit(),
                stockMovement.getMovementType(),
                stockMovement.getCreationDatetime());
    }
}
