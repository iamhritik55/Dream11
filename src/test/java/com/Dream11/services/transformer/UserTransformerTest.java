package com.Dream11.services.transformer;

import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.UserRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTransformerTest {

    @Test
    void requestDtoToUser() {
        //Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setName("test_user");
        requestDTO.setCredits(100);

        //Act
        User user = UserTransformer.requestDtoToUser(requestDTO);

        //Assert
        assertEquals(requestDTO.getName(), user.getName());
        assertEquals(requestDTO.getCredits(), user.getCredits());
    }

    @Test
    void userToResponseDto() {
        //Arrange
        User user = new User();
        user.setId("1");
        user.setName("test_user");
        user.setCredits(100);

        // Act
        UserResponseDTO responseDto = UserTransformer.userToResponseDto(user);

        // Assert
        assertEquals(user.getId(), responseDto.getId());
        assertEquals(user.getName(), responseDto.getName());
        assertEquals(user.getCredits(), responseDto.getCredits());
    }
}