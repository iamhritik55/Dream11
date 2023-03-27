package com.Dream11.transformer;

import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.entity.User;

public class UserTransformer {
    public static User requestDtoToUser(UserRequestDTO requestDto) {
        User user = new User();
        user.setName(requestDto.getName());
        user.setCredits(requestDto.getCredits());
        return user;
    }

    public static UserResponseDTO userToResponseDto(User user) {
        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setName(user.getName());
        responseDto.setCredits(user.getCredits());
        return responseDto;
    }
}


