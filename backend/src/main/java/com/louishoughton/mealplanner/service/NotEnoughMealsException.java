package com.louishoughton.mealplanner.service;

public class NotEnoughMealsException extends RuntimeException {

    private final int numberOfMeals;

    public NotEnoughMealsException(int numberOfMeals) {
        this.numberOfMeals = numberOfMeals;
    }

    @Override
    public String getMessage() {
        return String.format("User only have %d meals, need at least 7 meals to generate a week's worth of meals", numberOfMeals);
    }
}
