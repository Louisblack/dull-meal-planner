package com.louishoughton.mealplanner.model;

import java.time.Instant;
import java.util.Objects;

public class Meal {
    private String name;
    private Instant createdAt;

    public Meal(String name) {
        this.name = name;
    }

    public Meal() {
        this.createdAt = Instant.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
