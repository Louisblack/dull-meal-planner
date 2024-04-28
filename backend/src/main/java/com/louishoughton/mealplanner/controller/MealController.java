package com.louishoughton.mealplanner.controller;

import com.louishoughton.mealplanner.model.Meal;
import com.louishoughton.mealplanner.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals")
    public ResponseEntity<List<Meal>> getMeals(@RequestHeader String userGuid) {
        return ResponseEntity.ok(mealService.getAllMeals(userGuid));
    }

    @PostMapping("/meals")
    public ResponseEntity<Meal> addMeal(@RequestHeader String userGuid, @RequestBody Meal meal) {
        mealService.addMeal(userGuid, meal);
        return ResponseEntity.ok(meal);
    }

    @DeleteMapping("/meals")
    public ResponseEntity<Meal> removeMeal(@RequestHeader String userGuid, @RequestBody Meal meal) {
        mealService.removeMeal(userGuid, meal);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @GetMapping("/meals-for-week")
    public ResponseEntity<Map<DayOfWeek, Meal>> getMealsForTheWeek(@RequestHeader String userGuid) {
        return ResponseEntity.ok(mealService.getTheWeeksMeals(userGuid));
    }
}
