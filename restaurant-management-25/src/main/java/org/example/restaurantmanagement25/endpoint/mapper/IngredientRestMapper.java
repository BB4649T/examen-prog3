package org.example.restaurantmanagement25.endpoint.mapper;

import org.example.restaurantmanagement25.dao.operations.IngredientCrudOperations;
import org.example.restaurantmanagement25.endpoint.rest.CreateOrUpdateIngredient;
import org.example.restaurantmanagement25.endpoint.rest.IngredientRest;
import org.example.restaurantmanagement25.endpoint.rest.PriceRest;
import org.example.restaurantmanagement25.endpoint.rest.StockMovementRest;
import org.example.restaurantmanagement25.model.Ingredient;
import org.example.restaurantmanagement25.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IngredientRestMapper {
    @Autowired private PriceRestMapper priceRestMapper;
    @Autowired private StockMovementRestMapper stockMovementRestMapper;
    @Autowired private IngredientCrudOperations ingredientCrudOperations;

    public IngredientRest toRest(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();
        return new IngredientRest(ingredient.getId(), ingredient.getName(), prices, stockMovementRests);
    }

    public Ingredient toModel(CreateOrUpdateIngredient newIngredient) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(newIngredient.getId());
        ingredient.setName(newIngredient.getName());
        try {
            Ingredient existingIngredient = ingredientCrudOperations.findById(newIngredient.getId());
            ingredient.addPrices(existingIngredient.getPrices());
            ingredient.addStockMovements(existingIngredient.getStockMovements());
        } catch (NotFoundException e) {
            ingredient.addPrices(new ArrayList<>());
            ingredient.addStockMovements(new ArrayList<>());
        }
        return ingredient;
    }
}
