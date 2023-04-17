package com.Dream11.services.validation;

import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchUserValidationTest {
    @Mock
    private MatchRepo matchRepo;
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private MatchUserValidation matchUserValidation;

    @Test
    void testValidateMatchUserIds_withValidMatchIdAndUserIdAndUnplayedMatch_shouldNotThrowException() {
        String matchId = "1";
        String userId = "2";
        Match match = new Match();
        User user = new User();
        match.setStatus(MatchStatus.UNPLAYED);

        when(matchRepo.findById(matchId)).thenReturn(Optional.of(match));
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> matchUserValidation.validateMatchUserIds(matchId, userId));
    }

    @Test
    void testValidateMatchUserIds_withInvalidUserId_shouldThrowException() {
        String matchId = "validMatch";
        String userId = "invalidUser";
        Match match = new Match();
        match.setStatus(MatchStatus.UNPLAYED);

        when(matchRepo.findById(matchId)).thenReturn(Optional.of(match));
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            matchUserValidation.validateMatchUserIds(matchId, userId);
        });
        assertEquals("User Id not found", exception.getMessage());
    }

    @Test
    void testValidateMatchUserIds_withInvalidMatchId_shouldThrowException() {
        String matchId = "invalidMatch";
        String userId = "validUser";
        User user = new User();

        when(userRepo.findById(matchId)).thenReturn(Optional.of(user));
        when(matchRepo.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            matchUserValidation.validateMatchUserIds(matchId, userId);
        });
        assertEquals("Match Id not found", exception.getMessage());
    }

    @Test
    void testValidateMatchUserIds_withPlayedMatch_shouldThrowException(){
        String matchId = "1";
        String userId = "2";
        Match match = new Match();
        match.setStatus(MatchStatus.PLAYED);
        User user = new User();

        when(matchRepo.findById(matchId)).thenReturn(Optional.of(match));
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            matchUserValidation.validateMatchUserIds(matchId,userId);
        });
        assertEquals("This match is already played please choose another match", exception.getMessage());
    }
}