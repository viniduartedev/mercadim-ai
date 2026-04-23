package com.mercadimai.userprofile.service;

import com.mercadimai.exception.ResourceNotFoundException;
import com.mercadimai.exception.UserProfileNotFoundException;
import com.mercadimai.userprofile.dto.UserProfileResponse;
import com.mercadimai.userprofile.entity.UserProfile;
import com.mercadimai.userprofile.mapper.UserProfileMapper;
import com.mercadimai.userprofile.repository.UserProfileRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Transactional(readOnly = true)
    public Page<UserProfileResponse> list(Boolean active, Pageable pageable) {
        if (active == null) {
            return userProfileRepository.findAll(pageable).map(userProfileMapper::toResponse);
        }

        return userProfileRepository.findByAtivo(active, pageable).map(userProfileMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getById(Long id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de usuário não encontrado"));

        return userProfileMapper.toResponse(profile);
    }

    @Transactional(readOnly = true)
    public Optional<UserProfile> findActiveByAuthUserId(String authUserId) {
        return userProfileRepository.findByAuthUserIdAndAtivoTrue(authUserId);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String authUserId) {
        UserProfile profile = userProfileRepository.findByAuthUserIdAndAtivoTrue(authUserId)
                .orElseThrow(() -> new UserProfileNotFoundException("Nenhum perfil interno ativo encontrado para o usuário autenticado"));

        return userProfileMapper.toResponse(profile);
    }
}
