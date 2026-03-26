package com.mercadimai.user.service;

import com.mercadimai.exception.ConflictException;
import com.mercadimai.user.dto.CreateUserRequest;
import com.mercadimai.user.dto.UserResponse;
import com.mercadimai.user.entity.User;
import com.mercadimai.user.mapper.UserMapper;
import com.mercadimai.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email().trim().toLowerCase())) {
            throw new ConflictException("Já existe usuário cadastrado com esse email");
        }

        User user = User.builder()
                .name(request.name().trim())
                .email(request.email().trim().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .build();

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }
}
