package com.Dream11.services;

import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepo userRepo; //This mocks the repository so we dont access the actual database.

    @InjectMocks
    private UserService userService; //This annotation tells Mockito to inject the mocked UserRepo object into the userService field whenever a new instance of the test class is created. This ensures that the userService object in the test class uses the mocked UserRepo object instead of the actual implementation.

    @Test
    void updateUserCreditsTest_ValidUserId() throws Exception {

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
    public void testUpdateUserCredits_invalidUserId() throws Exception{
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