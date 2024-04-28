package com.louishoughton.mealplanner.controller;

import com.louishoughton.mealplanner.model.Meal;
import com.louishoughton.mealplanner.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public ResponseEntity<List<Meal>> getMeals(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(mealService.getAllMeals(principal.getName()));
    }

    @PostMapping("/meals")
    public ResponseEntity<Meal> addMeal(JwtAuthenticationToken principal, @RequestBody Meal meal) {
        mealService.addMeal(principal.getName(), meal);
        return ResponseEntity.ok(meal);
    }

    @DeleteMapping("/meals")
    public ResponseEntity<Meal> removeMeal(JwtAuthenticationToken principal, @RequestBody Meal meal) {
        mealService.removeMeal(principal.getName(), meal);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @GetMapping("/meals-for-week")
    public ResponseEntity<Map<DayOfWeek, Meal>> getMealsForTheWeek(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(mealService.getTheWeeksMeals(principal.getName()));
    }
}
