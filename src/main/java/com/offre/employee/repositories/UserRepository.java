package com.offre.employee.repositories;

import com.offre.employee.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Long> {

    @Query("Select u from users u where u.email = :email AND u.password = :password ")
    User connexion(@Param("email") String email, @Param("password") String password);
}
