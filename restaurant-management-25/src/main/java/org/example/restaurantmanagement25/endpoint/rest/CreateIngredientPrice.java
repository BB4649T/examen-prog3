package org.example.restaurantmanagement25.endpoint.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Data
public class CreateIngredientPrice {
    private Double amount;
    private LocalDate dateValue;
}
