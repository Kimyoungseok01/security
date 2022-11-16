package com.gangwondog.openAPI.domain.login;

import com.gangwondog.openAPI.domain.user.UserEntityP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<UserEntityP, Long>, LoginCustomRepository {
    Optional<UserEntityP> findByEmail(String email);
    boolean existsByEmail(String email);
}
