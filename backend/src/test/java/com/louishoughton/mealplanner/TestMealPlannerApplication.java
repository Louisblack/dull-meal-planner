package com.louishoughton.mealplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMealPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.from(MealPlannerApplication::main).with(TestMealPlannerApplication.class).run(args);
	}

}
