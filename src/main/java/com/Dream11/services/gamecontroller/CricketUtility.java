package com.Dream11.services.gamecontroller;

import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.enums.PlayerStatus;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CricketUtility {

    private static final SecureRandom random = new SecureRandom();

    public static CricketMatchContext cricketToss(CricketMatchContext matchContext) {
        int toss = random.nextInt(2);
        //at 0 team1 bats and at 1 team2 bats
        if (toss == 0) {
            matchContext.setTossWinner(matchContext.getTeam1().getId());
        } else {
            matchContext.setTossWinner(matchContext.getTeam2().getId());
        }
        return matchContext;
    }

    public int getBowlingTeamRuns(CricketMatchContext matchContext, CricketInningContext inningContext) {
        if (Objects.equals(inningContext.getBowlingTeamId(), matchContext.getTeam1().getId())) {
            return matchContext.getTeam1().getTeamRuns();
        } else {
            return matchContext.getTeam2().getTeamRuns();
        }
    }

    public CricketMatchContext addBattingTeamRuns(CricketMatchContext matchContext, CricketInningContext inningContext, int runs) {
        if (Objects.equals(inningContext.getBattingTeamId(), matchContext.getTeam1().getId())) {
            matchContext.getTeam1().setTeamRuns(runs);
        } else {
            matchContext.getTeam2().setTeamRuns(runs);
        }

        return matchContext;
    }

    public Player findPlayerOnStrike(List<Player> playerList) throws Exception {
        Optional<Player> optional = playerList.stream().filter(player1 -> player1.getPlayerStatus() == PlayerStatus.ON_STRIKE).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new Exception("No player ON_STRIKE");

    }

    public Player findPlayerOffStrike(List<Player> playerList) throws Exception {
        Optional<Player> optional =
                playerList.stream().filter(player1 -> player1.getPlayerStatus() == PlayerStatus.OFF_STRIKE).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new Exception("No player OFF_STRIKE");

    }

    public Player findPlayerBowling(List<Player> playerList) throws Exception {
        Optional<Player> optional =
                playerList.stream().filter(player1 -> player1.getPlayerStatus() == PlayerStatus.BOWLING).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new Exception("No player BOWLING");

    }

    public void swapOnStrikeOffStrike(List<Player> playerList) throws Exception {
        Player onStrike = playerList.stream()
                .filter(player -> player.getPlayerStatus() == PlayerStatus.ON_STRIKE)
                .findFirst()
                .orElse(null);

        Player offStrike = playerList.stream()
                .filter(player -> player.getPlayerStatus() == PlayerStatus.OFF_STRIKE)
                .findFirst()
                .orElse(null);

        if (onStrike != null && offStrike != null) {
            onStrike.setPlayerStatus(PlayerStatus.OFF_STRIKE);
            offStrike.setPlayerStatus(PlayerStatus.ON_STRIKE);
        }
    }

    private void changeStatus(List<Player> playerList, PlayerStatus fromStatus, PlayerStatus toStatus) {
        playerList.stream().filter(player -> player.getPlayerStatus() == fromStatus).findFirst().ifPresent(player -> player.setPlayerStatus(toStatus));
    }

    public void nextBowler(List<Player> playerList) throws Exception {
        //set bowling to played
        changeStatus(playerList, PlayerStatus.BOWLING, PlayerStatus.PLAYED);
        Optional<Player> first = playerList.stream()
                .filter(player -> player.getPlayerStatus() == PlayerStatus.NOT_PLAYING)
                .findFirst();

        if (first.isPresent()) {
            first.get().setPlayerStatus(PlayerStatus.BOWLING);
        } else {
            Optional<Player> played = playerList.stream()
                    .filter(player -> player.getPlayerStatus() == PlayerStatus.PLAYED)
                    .findFirst();
            if (played.isPresent()) {
                playerList.forEach(player -> player.setPlayerStatus(PlayerStatus.NOT_PLAYING));
                played.get().setPlayerStatus(PlayerStatus.BOWLING);
            } else {
                throw new Exception("playerList invalid");
            }

        }

    }

    public void nextBatsman(List<Player> playerList) throws Exception {
        Player player = findPlayerOnStrike(playerList);
        player.setPlayerStatus(PlayerStatus.PLAYED);
        Optional<Player> nextPlayer = playerList.stream().filter(player1 -> player1.getPlayerStatus() == PlayerStatus.NOT_PLAYING).findFirst();
        if (nextPlayer.isPresent()) {
            nextPlayer.get().setPlayerStatus(PlayerStatus.ON_STRIKE);
        } else {
            throw new Exception("No unplayed players found");
        }
    }

    public PlayerStats fetchPlayerStats(List<PlayerStats> playerStatsList, Player player) throws Exception{
        for (PlayerStats playerStats: playerStatsList){
            if(Objects.equals(playerStats.getPlayerId(), player.getId())){
                return playerStats;
            }
        }
        throw new Exception("No PlayerStat found corresponding to the give player");
    }
}
