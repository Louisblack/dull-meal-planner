package com.louishoughton.mealplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User {
    @Id
    private String guid;
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals;
    private Instant createdAt;

    public User() {
        this.createdAt = Instant.now();
        this.meals = new ArrayList<>();
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

    @JsonIgnore
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
