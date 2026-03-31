package com.mercadimai.userprofile.repository;

import com.mercadimai.userprofile.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByAuthUserIdAndActiveTrue(String authUserId);

    Optional<UserProfile> findByAuthUserId(String authUserId);

    boolean existsByEmailIgnoreCase(String email);
}
