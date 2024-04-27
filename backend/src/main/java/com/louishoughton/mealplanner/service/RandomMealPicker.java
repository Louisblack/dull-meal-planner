package com.louishoughton.mealplanner.service;

import com.louishoughton.mealplanner.model.Meal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomMealPicker {

    public List<Meal> pickRandomMeals(RandomMealPickerRequest request) {
        if (request.meals().size() <= request.numberToPick()) {
            return request.meals();
        }

        return new Random().ints(0, request.meals().size() - 1)
                .distinct()
                .limit(request.numberToPick())
                .mapToObj(Integer::valueOf)
                .map(i -> request.meals().get(i))
                .collect(Collectors.toList());
    }

}
