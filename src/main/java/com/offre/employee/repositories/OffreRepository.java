package com.offre.employee.repositories;

import com.offre.employee.models.Offre;
import com.offre.employee.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OffreRepository extends CrudRepository<Offre, Long> {
    Optional<Offre> findById(String offre_id);


    @Modifying
    @Query(
            value = "insert into offres_users (users_id, offres_id) values (:user_id, :offre_id)",
            nativeQuery = true)
    void postule(@Param("user_id") Long user_id, @Param("offre_id") Long offre_id);


    @Query(
            value = "select count(*) as somme from offres_users where users_id=:user_id and offres_id= :offre_id",
            nativeQuery = true)
    Integer hasPostuled(@Param("user_id") Long user_id, @Param("offre_id") Long offre_id);

}
