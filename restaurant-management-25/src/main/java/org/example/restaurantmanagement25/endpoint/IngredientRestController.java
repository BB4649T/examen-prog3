package org.example.restaurantmanagement25.endpoint;

import org.example.restaurantmanagement25.endpoint.mapper.IngredientRestMapper;
import org.example.restaurantmanagement25.endpoint.rest.CreateIngredientPrice;
import org.example.restaurantmanagement25.endpoint.rest.CreateOrUpdateIngredient;
import org.example.restaurantmanagement25.endpoint.rest.IngredientRest;
import org.example.restaurantmanagement25.model.Ingredient;
import org.example.restaurantmanagement25.model.Price;
import org.example.restaurantmanagement25.service.IngredientService;
import org.example.restaurantmanagement25.service.exception.ClientException;
import org.example.restaurantmanagement25.service.exception.NotFoundException;
import org.example.restaurantmanagement25.service.exception.ServerException;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class IngredientRestController {
    private final IngredientService ingredientService;
    private final IngredientRestMapper ingredientRestMapper;

    @GetMapping("/ingredients")
    public ResponseEntity<Object> getIngredients(@RequestParam(name = "priceMinFilter", required = false) Double priceMinFilter,
                                                 @RequestParam(name = "priceMaxFilter", required = false) Double priceMaxFilter) {
        try {
            List<Ingredient> ingredientsByPrices = ingredientService.getIngredientsByPrices(priceMinFilter, priceMaxFilter);
            List<IngredientRest> ingredientRests = ingredientsByPrices.stream()
                    .map(ingredient -> ingredientRestMapper.toRest(ingredient))
                    .toList();
            return ResponseEntity.ok().body(ingredientRests);
        } catch (ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Object> addIngredients() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/ingredients")
    public ResponseEntity<Object> updateIngredients(@RequestBody List<CreateOrUpdateIngredient> ingredientsToCreateOrUpdate) {
        try {
            List<Ingredient> ingredients = ingredientsToCreateOrUpdate.stream()
                    .map(ingredient -> ingredientRestMapper.toModel(ingredient))
                    .toList();
            List<IngredientRest> ingredientsRest = ingredientService.saveAll(ingredients).stream()
                    .map(ingredient -> ingredientRestMapper.toRest(ingredient))
                    .toList();
            return ResponseEntity.ok().body(ingredientsRest);
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/ingredients/{ingredientId}/prices")
    public ResponseEntity<Object> updateIngredientPrices(@PathVariable Long ingredientId, @RequestBody List<CreateIngredientPrice> ingredientPrices) {
        List<Price> prices = ingredientPrices.stream()
                .map(ingredientPrice ->
                        new Price(ingredientPrice.getAmount(), ingredientPrice.getDateValue()))
                .toList();
        Ingredient ingredient = ingredientService.addPrices(ingredientId, prices);
        IngredientRest ingredientRest = ingredientRestMapper.toRest(ingredient);
        return ResponseEntity.ok().body(ingredientRest);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Object> getIngredient(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(ingredientRestMapper.toRest(ingredientService.getById(id)));
        } catch (ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
