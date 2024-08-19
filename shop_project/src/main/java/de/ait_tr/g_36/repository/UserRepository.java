package de.ait_tr.g_36.repository;

import de.ait_tr.g_36.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> findByUsername(String username);// Optional - удобно, чтобы не получить null pointer exception
}

