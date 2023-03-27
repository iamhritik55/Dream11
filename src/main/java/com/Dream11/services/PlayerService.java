package com.Dream11.services;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.services.validation.PlayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Dream11.transformer.PlayerTransformer.*;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private UtilityService utilityService;
    public Player getPlayerFromId(String playerId) {
        Player playerValue = null;
        Optional<Player> player = playerRepo.findById(playerId);
        if (player.isPresent()) {
            playerValue = player.get();
        }
        return playerValue;

    }

    public List<Player> getPlayerListFromIdList(List<String> playerIdList) {
        return playerRepo.findAllById(playerIdList);
    }
    @Autowired
    PlayerValidation playerValidation;
    public PlayerResponseDTO addPlayer(PlayerRequestDTO playerRequestDTO) throws Exception{
        playerValidation.validatePlayer(playerRequestDTO);
        Player player = requestDtoToPlayer(playerRequestDTO);
        return playerToResponseDto(playerRepo.save(player));
    }

    public List<PlayerResponseDTO> getPlayers() {
        List<Player> players = playerRepo.findAll();
        List<PlayerResponseDTO> playerResponseDTOS = utilityService.createListOfPlayerResponseDTO(players);
        return playerResponseDTOS;
    }

    public PlayerResponseDTO getPlayer(String playerId) throws Exception {
        Optional<Player> player = playerRepo.findById(playerId);
        if (player.isPresent()) {
            return playerToResponseDto(player.get());
        } else {
            throw new Exception("Player with playerID - " + playerId + "doesn't exist");
        }
    }

    public List<String> playerIdListToNameList(List<String> playerIdList){
        List<Player> playerList=playerRepo.findAllById(playerIdList);
        List<String> playerNameList = new ArrayList<>();
        for(Player player: playerList){
            playerNameList.add(player.getName());
        }
        return playerNameList;
    }
}

