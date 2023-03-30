package com.Dream11.utility;

import com.Dream11.services.UserService;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class MatchUserUtility {
    @Autowired
    private UserService userService;
    public static MatchUserStats updateTeamPoints(MatchUserStats matchUserStats, List<Player> playerList) {
        List<String> userPlayerList = matchUserStats.getChosenPlayerIdList();
        Collections.sort(userPlayerList);

        for (String s : userPlayerList) {
            for (Player player : playerList) {
                if (Objects.equals(s, player.getId())) {
                    int teamPoints = matchUserStats.getTeamPoints();
                    teamPoints += player.getPlayerPoints();
                    matchUserStats.setTeamPoints(teamPoints);
                    break;
                }
            }
        }
        return matchUserStats;
    }

    public  List<MatchUserStats> distributeCredits(List<MatchUserStats> matchUserStatsList) throws Exception {

        //Sorting the array based on team points (Descending order)
        matchUserStatsList.sort((m1,m2)-> Integer.compare(m2.getTeamPoints(), m1.getTeamPoints()));

        int creditPool = 0;
        int numberOfWinners = 0;
        if(matchUserStatsList.isEmpty())
            throw new Exception("MatchUserStats list is empty");
        int winnerPoints = matchUserStatsList.get(0).getTeamPoints();
        //Calculating pointsPool and number of winners.

        //Unable to use lambda bcoz variables used in lambda should be final or effectively final

        for (MatchUserStats matchUserStats : matchUserStatsList) {
            creditPool += matchUserStats.getCreditsSpentByUser();
            if (winnerPoints == matchUserStats.getTeamPoints()) {
                numberOfWinners++;
            }
        }
        //distributing points equally to all the winners
        int pointsToDistribute = creditPool / numberOfWinners;
        if (numberOfWinners != 2 && matchUserStatsList.size() != 2) {
            for (MatchUserStats matchUserStats : matchUserStatsList) {
                if (numberOfWinners == 0) {
                    matchUserStats.setCreditChange(-matchUserStats.getCreditsSpentByUser());
                } else {
                    matchUserStats.setCreditChange(pointsToDistribute - matchUserStats.getCreditsSpentByUser());
                    numberOfWinners--;
                }
            }
        }

        userService.addCreditsUsingMatchUserStats(matchUserStatsList);
        return matchUserStatsList;
    }
}
