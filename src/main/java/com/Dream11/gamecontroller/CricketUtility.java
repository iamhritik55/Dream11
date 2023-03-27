package com.Dream11.gamecontroller;

import com.Dream11.context.CricketInningContext;
import com.Dream11.context.CricketMatchContext;
import com.Dream11.entity.Player;
import com.Dream11.services.PlayerService;
import com.Dream11.utility.PlayingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@Component
public class CricketUtility {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayingOrder playingOrder;
    private static SecureRandom random = new SecureRandom();
    public static CricketMatchContext cricketToss(CricketMatchContext matchContext){
        int toss = random.nextInt(2);
        //at 0 team1 bats and at 1 team2 bats
        if(toss==0){
            matchContext.setTossWinner(matchContext.getTeam1().getId());
        }
        else {
            matchContext.setTossWinner(matchContext.getTeam2().getId());
        }
        return matchContext;
    }

    public CricketInningContext assignPlayerListInningContext(CricketMatchContext matchContext,
                                                              CricketInningContext inningContext){
        if(!matchContext.isSecondInning()) {
            if(Objects.equals(matchContext.getTossWinner(), matchContext.getTeam1().getId())) {
                inningContext.setBattingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam1().getTeamPlayerIds()));
                inningContext.setBattingTeamId(matchContext.getTeam1().getId());

                inningContext.setBowlingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam2().getTeamPlayerIds()));
                inningContext.setBowlingTeamId(matchContext.getTeam2().getId());

            }
            else {
                inningContext.setBattingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam2().getTeamPlayerIds()));
                inningContext.setBattingTeamId(matchContext.getTeam2().getId());

                inningContext.setBowlingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam1().getTeamPlayerIds()));
                inningContext.setBowlingTeamId(matchContext.getTeam1().getId());

            }

            inningContext.setBattingPlayerList(playingOrder.battingOrder(inningContext.getBattingPlayerList()));
            inningContext.setBowlingPlayerList(playingOrder.bowlingOrder(inningContext.getBowlingPlayerList()));
        }
        else {
            inningContext = swapInningContextTeams(inningContext);
        }
        return inningContext;
    }

    public int getBowlingTeamRuns(CricketMatchContext matchContext, CricketInningContext inningContext){
        if(Objects.equals(inningContext.getBowlingTeamId(), matchContext.getTeam1().getId())){
            return matchContext.getTeam1().getTeamRuns();
        }
        else {
            return matchContext.getTeam2().getTeamRuns();
        }
    }
    public CricketInningContext swapInningContextTeams(CricketInningContext inningContext){
        String tempId = inningContext.getBattingTeamId();
        inningContext.setBattingTeamId(inningContext.getBowlingTeamId());
        inningContext.setBowlingTeamId(tempId);

        List<Player> tempPlayerList = inningContext.getBattingPlayerList();
        inningContext.setBattingPlayerList(inningContext.getBowlingPlayerList());
        inningContext.setBowlingPlayerList(tempPlayerList);

        inningContext.setBattingPlayerList(playingOrder.battingOrder(inningContext.getBattingPlayerList()));
        inningContext.setBowlingPlayerList(playingOrder.bowlingOrder(inningContext.getBowlingPlayerList()));

        return inningContext;

    }

    public CricketMatchContext addBattingTeamRuns(CricketMatchContext matchContext, CricketInningContext inningContext, int runs){
        if(Objects.equals(inningContext.getBattingTeamId(), matchContext.getTeam1().getId())){
            matchContext.getTeam1().setTeamRuns(runs);
        }
        else {
            matchContext.getTeam2().setTeamRuns(runs);
        }

        return matchContext;
    }
}
