package com.louishoughton.mealplanner.model;

import java.time.Instant;
import java.util.List;

public class User {
    private String guid;
    private String name;
    private List<Meal> meals;
    private Instant createdAt;

    public User() {
        this.createdAt = Instant.now();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
