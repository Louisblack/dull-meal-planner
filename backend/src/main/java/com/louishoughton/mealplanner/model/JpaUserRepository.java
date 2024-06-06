package com.louishoughton.mealplanner.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends CrudRepository<User, String> {

}
