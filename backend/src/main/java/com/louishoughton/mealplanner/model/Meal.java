package com.louishoughton.mealplanner.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String guid;
    private String name;
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "user_guid")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meal(String name) {
        this.name = name;
    }

    public Meal() {
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
