package com.mercadimai.userprofile.service;

import com.mercadimai.exception.UserProfileNotFoundException;
import com.mercadimai.userprofile.dto.UserProfileResponse;
import com.mercadimai.userprofile.entity.UserProfile;
import com.mercadimai.userprofile.mapper.UserProfileMapper;
import com.mercadimai.userprofile.repository.UserProfileRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Transactional(readOnly = true)
    public Optional<UserProfile> findActiveByAuthUserId(String authUserId) {
        return userProfileRepository.findByAuthUserIdAndActiveTrue(authUserId);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String authUserId) {
        UserProfile profile = userProfileRepository.findByAuthUserIdAndActiveTrue(authUserId)
                .orElseThrow(() -> new UserProfileNotFoundException("Nenhum perfil interno ativo encontrado para o usuário autenticado"));

        return userProfileMapper.toResponse(profile);
    }
}
