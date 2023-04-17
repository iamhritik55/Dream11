package com.Dream11.utility;

import com.Dream11.services.MatchService;
import com.Dream11.services.PlayerService;
import com.Dream11.services.TeamService;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.gamecontroller.CricketUtility;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.Player;
import com.Dream11.services.transformer.PlayerTransformer;
import com.Dream11.services.validation.CricketMatchValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ContextUtility {
    @Autowired
    MatchService matchService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    CricketMatchValidation matchValidation;
    @Autowired
    private PlayingOrder playingOrder;
    @Autowired
    TeamService teamService;

    public CricketMatchContext createAndValidateCricketContext(String matchId) throws Exception {
        CricketMatchContext matchContext = new CricketMatchContext();
        Match match = matchService.getMatchById(matchId);
        matchContext.setMatch(match);
        matchValidation.matchCompletedValidation(match);
        matchContext.setTeam1(teamService.getTeamById(match.getTeam1Id()));
        matchContext.setTeam2(teamService.getTeamById(match.getTeam2Id()));
        matchContext.setSecondInning(false);
        return CricketUtility.cricketToss(matchContext);
    }

    public CricketInningContext fetchInningContext(CricketMatchContext matchContext,
                                                   CricketInningContext inningContext) throws Exception {
        if (!matchContext.isSecondInning()) {

            if (Objects.equals(matchContext.getTossWinner(), matchContext.getTeam1().getId())) {
                inningContext.setBattingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam1().getTeamPlayerIds()));
                inningContext.setBattingTeamId(matchContext.getTeam1().getId());

                inningContext.setBowlingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam2().getTeamPlayerIds()));
                inningContext.setBowlingTeamId(matchContext.getTeam2().getId());
            } else {
                inningContext.setBattingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam2().getTeamPlayerIds()));
                inningContext.setBattingTeamId(matchContext.getTeam2().getId());

                inningContext.setBowlingPlayerList(playerService.getPlayerListFromIdList(matchContext.getTeam1().getTeamPlayerIds()));
                inningContext.setBowlingTeamId(matchContext.getTeam1().getId());

            }

            inningContext.setBattingPlayerList(playingOrder.battingOrder(inningContext.getBattingPlayerList()));
            inningContext.setBowlingPlayerList(playingOrder.bowlingOrder(inningContext.getBowlingPlayerList()));
            List<Player> combinedPlayerList = new ArrayList<>(inningContext.getBattingPlayerList());
            combinedPlayerList.addAll(inningContext.getBowlingPlayerList());
            inningContext.setPlayerStatsList(PlayerTransformer.createPlayerStatList(combinedPlayerList));
        } else {
            inningContext = swapInningContextTeams(inningContext);
        }
        return inningContext;
    }

    public CricketInningContext swapInningContextTeams(CricketInningContext inningContext) throws Exception {
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

}