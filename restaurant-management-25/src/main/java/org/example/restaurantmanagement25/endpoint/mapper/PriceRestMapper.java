package org.example.restaurantmanagement25.endpoint.mapper;

import org.example.restaurantmanagement25.endpoint.rest.PriceRest;
import org.example.restaurantmanagement25.model.Price;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PriceRestMapper implements Function<Price, PriceRest> {

    @Override
    public PriceRest apply(Price price) {
        return new PriceRest(price.getId(), price.getAmount(), price.getDateValue());
    }
}
