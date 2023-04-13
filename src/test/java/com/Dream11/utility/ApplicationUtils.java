package com.Dream11.utility;

import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.Team;

import java.util.List;

import static com.Dream11.services.utils.PlayerUtils.createPlayer;
import static com.Dream11.services.utils.PlayerUtils.createPlayerResponseDto;
import static com.Dream11.services.utils.TeamUtils.createTeam;


public class ApplicationUtils {

    public static final String TEST_MATCH_ID = "642c158b627bab003c45a31e";
    public static final String TEST_TEAM1_ID = "1";
    public static final String TEST_TEAM2_ID = "2";
    public static final String TEST_TEAM1_NAME = "India";
    public static final String TEST_TEAM2_NAME = "Australia";
    public static final PlayerResponseDTO PLAYER1RESPONSE = createPlayerResponseDto("1", "Virat Kohli",
            PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final PlayerResponseDTO PLAYER2RESPONSE = createPlayerResponseDto("2", "Rohit Sharma",
            PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final PlayerResponseDTO PLAYER3RESPONSE = createPlayerResponseDto("3", "Ravindra Jadeja",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER4RESPONSE = createPlayerResponseDto("4", "Hardik Pandya",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER5RESPONSE = createPlayerResponseDto("5", "KL Rahul",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER6RESPONSE = createPlayerResponseDto("6", "Shikhar Dhawan",
            PlayerTitle.ALL_ROUNDER, 4, 4, 12);
    public static final PlayerResponseDTO PLAYER7RESPONSE = createPlayerResponseDto("7", "Ishant Sharma",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER8RESPONSE = createPlayerResponseDto("8", "Yuzvendra Chahal",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER9RESPONSE = createPlayerResponseDto("9", "Rishabh Pant",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER10RESPONSE = createPlayerResponseDto("10", "Jasprit Bumrah",
            PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final PlayerResponseDTO PLAYER11RESPONSE = createPlayerResponseDto("11", "Ravichandran Ashwin",
            PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final PlayerResponseDTO PLAYER12RESPONSE = createPlayerResponseDto("12", "Aaron Finch",
            PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final PlayerResponseDTO PLAYER13RESPONSE = createPlayerResponseDto("13", "Ashton Agar",
            PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final PlayerResponseDTO PLAYER14RESPONSE = createPlayerResponseDto("14", "Alex Carey",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER15RESPONSE = createPlayerResponseDto("15", "Pat Cummins",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER16RESPONSE = createPlayerResponseDto("16", "Josh Hazlewood",
            PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final PlayerResponseDTO PLAYER17RESPONSE = createPlayerResponseDto("17", "Moises Henriques",
            PlayerTitle.ALL_ROUNDER, 4, 4, 12);
    public static final PlayerResponseDTO PLAYER18RESPONSE = createPlayerResponseDto("18", "Mitchell Marsh",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER19RESPONSE = createPlayerResponseDto("19", "Glenn Maxwell",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER20RESPONSE = createPlayerResponseDto("20", "Ben McDermott",
            PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final PlayerResponseDTO PLAYER21RESPONSE = createPlayerResponseDto("21", "Riley Meredith",
            PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final PlayerResponseDTO PLAYER22RESPONSE = createPlayerResponseDto("22", "Kane Richardson",
            PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final Player PLAYER1 = createPlayer("1", "Virat Kohli", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final Player PLAYER2 = createPlayer("2", "Rohit Sharma", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final Player PLAYER3 = createPlayer("3", "Ravindra Jadeja", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER4 = createPlayer("4", "Hardik Pandya", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER5 = createPlayer("5", "KL Rahul", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER6 = createPlayer("6", "Shikhar Dhawan", PlayerTitle.ALL_ROUNDER, 4, 4, 12);
    public static final Player PLAYER7 = createPlayer("7", "Ishant Sharma", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER8 = createPlayer("8", "Yuzvendra Chahal", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER9 = createPlayer("9", "Rishabh Pant", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER10 = createPlayer("10", "Jasprit Bumrah", PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final Player PLAYER11 = createPlayer("11", "Ravichandran Ashwin", PlayerTitle.STRONG_BOWLER, 3, 5,
            10);
    public static final Player PLAYER12 = createPlayer("12", "Aaron Finch", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final Player PLAYER13 = createPlayer("13", "Ashton Agar", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
    public static final Player PLAYER14 = createPlayer("14", "Alex Carey", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER15 = createPlayer("15", "Pat Cummins", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER16 = createPlayer("16", "Josh Hazlewood", PlayerTitle.AVERAGE_BATSMAN, 3, 2, 7);
    public static final Player PLAYER17 = createPlayer("17", "Moises Henriques", PlayerTitle.ALL_ROUNDER, 4, 4, 12);
    public static final Player PLAYER18 = createPlayer("18", "Mitchell Marsh", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER19 = createPlayer("19", "Glenn Maxwell", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER20 = createPlayer("20", "Ben McDermott", PlayerTitle.AVERAGE_BOWLER, 2, 3, 7);
    public static final Player PLAYER21 = createPlayer("21", "Riley Meredith", PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final Player PLAYER22 = createPlayer("22", "Kane Richardson", PlayerTitle.STRONG_BOWLER, 3, 5, 10);
    public static final Team TEAM1 = createTeam(TEST_TEAM1_ID, TEST_TEAM1_NAME,
            List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
    public static final Team TEAM2 = createTeam(TEST_TEAM2_ID, TEST_TEAM2_NAME,
            List.of("12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"));
    public static final List<Player> ALL_PLAYERS = List.of(PLAYER1, PLAYER2, PLAYER3, PLAYER4, PLAYER5, PLAYER6,
            PLAYER7, PLAYER8, PLAYER9, PLAYER10, PLAYER11, PLAYER12, PLAYER13, PLAYER14, PLAYER15, PLAYER16, PLAYER17,
            PLAYER18, PLAYER19, PLAYER20, PLAYER21, PLAYER22);
    public static final List<PlayerResponseDTO> TEAM1_PLAYERS = List.of(PLAYER1RESPONSE, PLAYER2RESPONSE,
            PLAYER3RESPONSE, PLAYER4RESPONSE, PLAYER5RESPONSE, PLAYER6RESPONSE, PLAYER7RESPONSE, PLAYER8RESPONSE,
            PLAYER9RESPONSE, PLAYER10RESPONSE, PLAYER11RESPONSE);
    public static final List<PlayerResponseDTO> TEAM2_PLAYERS = List.of(PLAYER12RESPONSE, PLAYER13RESPONSE,
            PLAYER14RESPONSE, PLAYER15RESPONSE, PLAYER16RESPONSE, PLAYER17RESPONSE, PLAYER18RESPONSE, PLAYER19RESPONSE,
            PLAYER20RESPONSE, PLAYER21RESPONSE, PLAYER22RESPONSE);


}
