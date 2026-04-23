package com.mercadimai.userprofile.repository;

import com.mercadimai.userprofile.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByAuthUserIdAndAtivoTrue(String authUserId);

    Optional<UserProfile> findByAuthUserId(String authUserId);

    Page<UserProfile> findByAtivo(boolean ativo, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);
}
