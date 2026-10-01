package com.healthapp.userservice.service;

import com.healthapp.userservice.dto.UpdateProfileRequest;
import com.healthapp.userservice.dto.UserResponse;
import com.healthapp.userservice.entity.User;
import com.healthapp.userservice.exception.UserNotFoundException;
import com.healthapp.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateProfile(Long id, UpdateProfileRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (request.fullName() != null) user.setFullName(request.fullName());
        if (request.dateOfBirth() != null) user.setDateOfBirth(request.dateOfBirth());
        if (request.bloodGroup() != null) user.setBloodGroup(request.bloodGroup());
        if (request.knownAllergies() != null) user.setKnownAllergies(request.knownAllergies());
        if (request.knownConditions() != null) user.setKnownConditions(request.knownConditions());
        if (request.address() != null) user.setAddress(request.address());

        user = userRepository.save(user);
        return UserResponse.from(user);
    }
}
