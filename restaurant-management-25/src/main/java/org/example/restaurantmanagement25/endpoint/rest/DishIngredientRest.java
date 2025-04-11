package org.example.restaurantmanagement25.endpoint.rest;

import org.example.restaurantmanagement25.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishIngredientRest {
    private IngredientBasicProperty ingredient;
    private Double requiredQuantity;
    private Unit unit;
}
