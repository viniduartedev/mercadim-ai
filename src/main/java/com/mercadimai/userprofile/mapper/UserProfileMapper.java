package com.mercadimai.userprofile.mapper;

import com.mercadimai.userprofile.dto.UserProfileResponse;
import com.mercadimai.userprofile.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileResponse toResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getAuthUserId(),
                profile.getNome(),
                profile.getEmail(),
                profile.getRole(),
                profile.isAtivo(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
