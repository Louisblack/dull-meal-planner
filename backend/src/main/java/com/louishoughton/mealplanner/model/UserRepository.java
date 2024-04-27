package com.louishoughton.mealplanner.model;

import java.util.Optional;

public interface UserRepository {

    Optional<User> get(String userGuid);

    void save(User user);

    void update(User user);

}
