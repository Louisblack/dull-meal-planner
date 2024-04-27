package com.louishoughton.mealplanner.service;

import com.louishoughton.mealplanner.model.Meal;
import com.louishoughton.mealplanner.model.User;
import com.louishoughton.mealplanner.model.UserRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MealService {

    public static final int DAYS_IN_A_WEEK = 7;
    private final UserRepository userRepository;
    private final RandomMealPicker randomMealPicker;

    public MealService(UserRepository userRepository, RandomMealPicker randomMealPicker) {
        this.userRepository = userRepository;
        this.randomMealPicker = randomMealPicker;
    }

    public void addMeal(String userGuid, Meal meal) {
        User user = userRepository.get(userGuid).orElseThrow(() -> new UserNotFoundException(userGuid));
        user.getMeals().add(meal);
        userRepository.save(user);
    }

    public void removeMeal(String userGuid, Meal meal) {
        User user = userRepository.get(userGuid).orElseThrow(() -> new UserNotFoundException(userGuid));
        user.getMeals().remove(meal);
        userRepository.save(user);
    }

    public Map<DayOfWeek, Meal> getTheWeeksMeals(String userGuid) {
        User user = userRepository.get(userGuid).orElseThrow(() -> new UserNotFoundException(userGuid));

        List<Meal> randomMeals = randomMealPicker.pickRandomMeals(new RandomMealPickerRequest(user.getMeals(), DAYS_IN_A_WEEK));
        return IntStream.range(0, DAYS_IN_A_WEEK)
                        .mapToObj(i -> Map.entry(DayOfWeek.of(i + 1), randomMeals.get(i)))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
