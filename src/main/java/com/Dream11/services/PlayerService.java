package com.Dream11.services;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.transformer.PlayerTransformer;
import com.Dream11.services.validation.PlayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Dream11.services.transformer.PlayerTransformer.*;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private UtilityService utilityService;

    public List<Player> getPlayerListFromIdList(List<String> playerIdList) { // TODO: 30/03/23 refactor name
        return playerRepo.findAllById(playerIdList);
    }
    @Autowired
    PlayerValidation playerValidation;

    public PlayerResponseDTO addPlayer(PlayerRequestDTO playerRequestDTO) throws Exception {
        playerValidation.validatePlayer(playerRequestDTO);
        Player player = requestDtoToPlayer(playerRequestDTO);
        return playerToResponseDto(playerRepo.save(player));
    }

    public List<PlayerResponseDTO> getPlayers() {
        List<Player> players = playerRepo.findAll();
        return createListOfPlayerResponse(players);
    }

    public PlayerResponseDTO getPlayer(String playerId) throws Exception {
        return playerToResponseDto(playerRepo.findById(playerId).orElseThrow(
                () -> new Exception("Player with playerID " + "- " + playerId + " doesn't exist")));
    }

    public List<String> playerIdListToNameList(List<String> playerIdList){
        List<Player> playerList=playerRepo.findAllById(playerIdList);
        List<String> playerNameList = PlayerTransformer.playerListToNameList(playerList);
        return playerNameList;
    }
}

