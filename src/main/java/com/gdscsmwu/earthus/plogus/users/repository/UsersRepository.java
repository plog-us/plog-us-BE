package com.gdscsmwu.earthus.plogus.users.repository;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Optional<Users> <-> orElse()

    // username으로 users 찾기
    Optional<Users> findByUsername(String username);

    // email로 users 찾기
    Optional<Users> findByEmail(String email);

    // username 중복체크
    boolean existsByUsername(String username);

    // email 중복체크
    boolean existsByEmail(String email);

    // userUuid로 users 찾기
    Users findByUserUuid(Long userUuid);

}
