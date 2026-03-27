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
                profile.getName(),
                profile.getEmail(),
                profile.getRole(),
                profile.isActive(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
