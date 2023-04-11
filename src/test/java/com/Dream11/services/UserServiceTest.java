package com.Dream11.services;

import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.services.Utils.UserUtils;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepo userRepo; //This mocks the repository so we dont access the actual database.

    @InjectMocks
    private UserService userService; //This annotation tells Mockito to inject the mocked UserRepo object into the userService field whenever a new instance of the test class is created. This ensures that the userService object in the test class uses the mocked UserRepo object instead of the actual implementation.

    @Test
    void testAddUser(){
        //Arrange
        UserRequestDTO requestDto = new UserRequestDTO();
        requestDto.setName("test_user");
        requestDto.setCredits(100);
        User user = new User();
        user.setName(requestDto.getName());
        user.setCredits(requestDto.getCredits());

        UserResponseDTO expectedResponseDto = new UserResponseDTO();
        expectedResponseDto.setId("1");
        expectedResponseDto.setName(requestDto.getName());
        expectedResponseDto.setCredits(requestDto.getCredits());

        when(userRepo.save(any(User.class))).thenReturn(user);

        //Act
        UserResponseDTO actualResponseDto = userService.addUser(requestDto);

        //Assert
        //captures the argument of a method call for later use
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture()); //captures value
        User savedUser = captor.getValue();  // retrieve values


        assertEquals(savedUser.getId(), actualResponseDto.getId());
        assertEquals(expectedResponseDto.getName(), actualResponseDto.getName());
        assertEquals(expectedResponseDto.getCredits(), actualResponseDto.getCredits());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getCredits(), savedUser.getCredits());
    }
    @Test
    void testGetUsers(){
        //Arrange
        List<User> expectedUsers = new ArrayList<>();
        User user1 = UserUtils.createUser();
        User user2 = UserUtils.createUser();
        expectedUsers.add(user1);
        expectedUsers.add(user2);
        when(userRepo.findAll()).thenReturn(expectedUsers);

        //Act
        List<User> actualUsers = userService.getUsers();

        //Assert
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.get(0).getId(),actualUsers.get(0).getId());
        assertEquals(expectedUsers.get(0).getName(), actualUsers.get(0).getName());
        assertEquals(expectedUsers.get(0).getCredits(), actualUsers.get(0).getCredits());
        assertEquals(expectedUsers.get(1).getId(),actualUsers.get(1).getId());
        assertEquals(expectedUsers.get(1).getName(), actualUsers.get(1).getName());
        assertEquals(expectedUsers.get(1).getCredits(), actualUsers.get(1).getCredits());
    }
    @Test
    void getUserById() {
        //Arrange
       // User expectedUser = UserUtils.createUser();
        String id = "1";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName("test_user");
        expectedUser.setCredits(100);
        when(userRepo.findById(id)).thenReturn(Optional.of(expectedUser));

        //Act
        User actualUser = userService.getUserById(id);

        //Assert
        assertEquals(expectedUser, actualUser);
    }
    @Test
    void testGetUserByIdThrowsException(){
        String id = "1";
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()->{
            userService.getUserById(id);
        });
    }

    @Test
    void subtractUserCredits() throws Exception{
        //Arrange
        int credits = 50;
        User user = UserUtils.createUser();
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        //Act
        userService.subtractUserCredits(user.getId(), credits);

        //Assert
        assertEquals(50, user.getCredits());
        verify(userRepo, times(1)).save(user);

    }
    @Test
    void testSubtractUserCreditsNotEnoughCredits(){
        User user = UserUtils.createUser();
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, ()->{
            userService.subtractUserCredits(user.getId(), 200);
        });
        verify(userRepo, times(0)).save(user);
    }
    @Test
    void updateUserCreditsTest_ValidUserId() {

        //Arrange
        String userId = "testUser";
        String userName = "userName";
        int initialCredits = 10;
        int creditsToAdd = 5;
        User user = new User(userId,userName,initialCredits);
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        //Act
        userService.updateUserCredits(userId,creditsToAdd);

        //Assert
        verify(userRepo).findById(userId);
        assertEquals(initialCredits+creditsToAdd,user.getCredits()); //The line verify(userRepo).findById(userId) is a Mockito verification statement that checks whether the findById method of the userRepo mock object is called with the userId argument during the test.
        verify(userRepo).save(user);
    }

    @Test
    public void testUpdateUserCredits_invalidUserId() {
        // Arrange
        String userId = "invaliduser";
        int creditsToAdd = 5;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); //These 2 lines are used to capture system.out messages

        // Act
        userService.updateUserCredits(userId, creditsToAdd);

        // Assert
        verify(userRepo).findById(userId);
        assertEquals("Invalid userId\n", outContent.toString());
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCreditsUsingMatchUserStats() throws Exception{
        //Create test data
        List<MatchUserStats> matchUserStatsList = new ArrayList<>();
        MatchUserStats matchUserStats1 = new MatchUserStats();
        matchUserStats1.setUserId("1");
        matchUserStats1.setCreditChange(10);
        matchUserStats1.setCreditsSpentByUser(12);
        MatchUserStats matchUserStats2 = new MatchUserStats();
        matchUserStats2.setUserId("2");
        matchUserStats2.setCreditChange(-10);
        matchUserStats2.setCreditsSpentByUser(13);
        matchUserStatsList.add(matchUserStats1);
        matchUserStatsList.add(matchUserStats2);

        User user1 = new User("1","user1",10);
        User user2 = new User("2","user2",12);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        //mock dependencies
        when(userRepo.findAllById(anyList())).thenReturn(userList);
        //In other words, this line is telling Mockito to intercept calls to userService2.findUserListByIdList and return userList, no matter what arguments are passed to the method.

        //calling method under test
        userService.addCreditsUsingMatchUserStats(matchUserStatsList);

        // verify that the userRepo was called with the expected data
        verify(userRepo).saveAll(userList);

        // verify that the credits were updated correctly
        assertEquals(32, user1.getCredits());
        assertEquals(12, user2.getCredits());
    }


}