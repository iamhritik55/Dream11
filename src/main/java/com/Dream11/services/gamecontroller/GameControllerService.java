package com.Dream11.services.gamecontroller;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameControllerService{
    public List<LeaderboardResponseDTO> startMatch(String matchId) throws Exception;
}
