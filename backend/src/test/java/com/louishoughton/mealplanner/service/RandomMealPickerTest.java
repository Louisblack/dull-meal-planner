package com.louishoughton.mealplanner.service;

import com.louishoughton.mealplanner.model.Meal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RandomMealPickerTest {

    private RandomMealPicker underTest = new RandomMealPicker();

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    void should_just_return_the_list_if_not_enough_meals(int numberOfMeals) {
        List<Meal> meals = mealListGenerator(numberOfMeals);
        List<Meal> randomMeals = underTest.pickRandomMeals(new RandomMealPickerRequest(meals, 7));

        assertThat(randomMeals).isEqualTo(meals);
    }

    @Test
    void should_return_the_requested_number() {
        List<Meal> meals = mealListGenerator(10);
        List<Meal> randomMeals = underTest.pickRandomMeals(new RandomMealPickerRequest(meals, 7));

        assertThat(randomMeals).hasSize(7);
    }

    private List<Meal> mealListGenerator(int numberToCreate) {
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < numberToCreate; i++) {
            meals.add(new Meal(String.format("Meal %d", i + 1)));
        }
        return meals;
    }
}
