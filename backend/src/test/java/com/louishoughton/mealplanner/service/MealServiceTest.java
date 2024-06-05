package com.louishoughton.mealplanner.service;

import com.louishoughton.mealplanner.model.Meal;
import com.louishoughton.mealplanner.model.User;
import com.louishoughton.mealplanner.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RandomMealPicker randomMealPicker;

    private MealService mealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mealService = new MealService(userRepository, randomMealPicker);
    }

    @Test
    void shouldAddMealToUser() {
        String userGuid = "userGuid";
        Meal meal = new Meal();
        User user = new User();
        when(userRepository.get(userGuid)).thenReturn(Optional.of(user));

        mealService.addMeal(userGuid, meal);

        assertTrue(user.getMeals().contains(meal));
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenAddingMealToNonExistentUser() {
        String userGuid = "userGuid";
        Meal meal = new Meal();
        when(userRepository.get(userGuid)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> mealService.addMeal(userGuid, meal));
    }

    @Test
    void shouldRemoveMealFromUser() {
        String userGuid = "userGuid";
        Meal meal = new Meal();
        User user = new User();
        user.getMeals().add(meal);
        when(userRepository.get(userGuid)).thenReturn(Optional.of(user));

        mealService.removeMeal(userGuid, meal);

        assertFalse(user.getMeals().contains(meal));
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenRemovingMealFromNonExistentUser() {
        String userGuid = "userGuid";
        Meal meal = new Meal();
        when(userRepository.get(userGuid)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> mealService.removeMeal(userGuid, meal));
    }

    @Test
    void shouldReturnWeeksMealsForUser() {
        String userGuid = "userGuid";
        User user = new User();
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < MealService.DAYS_IN_A_WEEK; i++) {
            meals.add(new Meal());
        }
        user.setMeals(meals);
        when(userRepository.get(userGuid)).thenReturn(Optional.of(user));
        when(randomMealPicker.pickRandomMeals(any(RandomMealPickerRequest.class))).thenReturn(meals);

        Map<DayOfWeek, Meal> weeksMeals = mealService.getTheWeeksMeals(userGuid);

        assertEquals(MealService.DAYS_IN_A_WEEK, weeksMeals.size());
        verify(randomMealPicker).pickRandomMeals(any(RandomMealPickerRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenGettingWeeksMealsForNonExistentUser() {
        String userGuid = "userGuid";
        when(userRepository.get(userGuid)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> mealService.getTheWeeksMeals(userGuid));
    }
}
