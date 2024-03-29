package com.gdscsmwu.earthus.plogus.users.repository;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

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

    // 프로필 사진 삭제 : userProfile, profileUuid --> null
    @Modifying
    @Query("UPDATE Users u SET u.userProfile = null, u.profileUuid = null WHERE u.userUuid = :userUuid")
    void deleteUserProfile(Long userUuid);

}
