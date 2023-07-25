package com.pickupluck.ecogging.domain.user.repository;

import com.pickupluck.ecogging.domain.user.entity.Corporate;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {

    public Optional<Corporate> findByCorpName(String name);

    public Optional<Corporate> findByCorpRegisterNumber(String registerNumber);

    @Query("select c from Corporate c " +
            "inner join User u " +
            "on c.manager.id = u.id " +
            "and u.email = :email")
    public Optional<Corporate> findByManagerEmail(String email);

    public Optional<Corporate> findByManager(User manager);

}
