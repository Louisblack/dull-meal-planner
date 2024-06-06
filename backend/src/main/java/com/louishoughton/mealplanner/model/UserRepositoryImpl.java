package com.louishoughton.mealplanner.model;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> get(String userGuid) {
        return jpaUserRepository.findById(userGuid);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(user);
    }

    @Override
    public void update(User user) {
        jpaUserRepository.save(user);
    }
}
