package com.louishoughton.mealplanner.service;

import com.louishoughton.mealplanner.model.Meal;

import java.util.List;

public record RandomMealPickerRequest(List<Meal> meals, int numberToPick) {

}
