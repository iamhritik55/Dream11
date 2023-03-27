package com.Dream11.services;

import com.Dream11.context.CricketInningContext;
import com.Dream11.context.CricketMatchContext;
import com.Dream11.entity.*;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLOutput;
import java.util.*;

import static com.Dream11.utility.ApplicationUtils.FIFTEEN_POINTS;
import static com.Dream11.utility.ApplicationUtils.WICKET;

@Service
public class MatchUserService {

    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    MatchRepo matchRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UtilityService utilityService;
    @Autowired
    UserService userService;

    public MatchUserStats addMatchUserStats(MatchUserStats matchUserStats) throws Exception {
        String matchId = matchUserStats.getMatchId();
        Optional<Match> optionalMatch = matchRepo.findById(matchId);
        String userId = matchUserStats.getUserId();
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalMatch.isPresent() && optionalUser.isPresent()) {
            Match matchObj = optionalMatch.get();
            if (matchObj.getStatus()== MatchStatus.PLAYED) {
                throw new Exception("This match is already played please choose another one.");
            }
            String matchUserId = matchUserStats.getMatchId() + "_" + matchUserStats.getUserId();
            matchUserStats.setMatch_userId(matchUserId);
            return matchUserStatsRepo.save(matchUserStats);
        } else {
            throw new Exception("IDs not found.");
        }
    }

    public List<MatchUserStats> getAllStats() {
        return matchUserStatsRepo.findAll();
    }

    public MatchUserStats getUserStats(String match_userId) throws Exception {
        MatchUserStats matchUserStats = new MatchUserStats();
        //finding matchUserStats by passing matchId and userId
        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(match_userId);
        if (optionalStats.isPresent()) {
            MatchUserStats stats = optionalStats.get();

            //making a list to store player names
            List<String> players = new ArrayList<>();

            //if player present then fetch player names by their ids
            List<Player> listOfPlayerIds = playerRepo.findAllById(stats.getChosenPlayerIdList());
            if (!CollectionUtils.isEmpty(listOfPlayerIds)) {
                for (Player player : listOfPlayerIds) {
                    players.add(player.getName());
                }
            }
            matchUserStats.setMatch_userId(stats.getMatch_userId()); // TODO: 16/03/23 creaete a transformer
            matchUserStats.setMatchId(stats.getMatchId());
            matchUserStats.setUserId(stats.getUserId());
            matchUserStats.setCreditChange(stats.getCreditChange());
            matchUserStats.setTeamPoints(stats.getTeamPoints());
            matchUserStats.setChosenPlayerIdList(stats.getChosenPlayerIdList());
//            matchUserStats.setPlayerName(players);
            matchUserStats.setCreditsSpentByUser(stats.getCreditsSpentByUser());
            return matchUserStats;
        } else {
            throw new Exception("Data not found");
        }
    }

    public void createUserTeam(String match_userId, List<String> playerIds) throws Exception {
        Optional<MatchUserStats> optionalMatchUserStats = matchUserStatsRepo.findById(match_userId);
        if (optionalMatchUserStats.isEmpty()) {
            throw new Exception("ID not found.");
        }
        utilityService.validatePlayerIds(playerIds);

        utilityService.validateTeamSize(playerIds);
        MatchUserStats updateMatchUserStats = optionalMatchUserStats.get();
        String userId = updateMatchUserStats.getUserId();
        System.out.println(userId);
        int totalCost = utilityService.calculateTeamCost(playerIds);

        utilityService.restrictPlayerIds(playerIds);

        //updating the credits of user
        userService.subtractUserCredits(userId, totalCost);
        updateMatchUserStats.setCreditsSpentByUser(totalCost);
        updateMatchUserStats.setChosenPlayerIdList(playerIds);

        matchUserStatsRepo.save(updateMatchUserStats);
    }

    public List<MatchUserStats> updateMatchUserStats(CricketMatchContext matchContext,
    CricketInningContext inningContext) throws Exception{

        //first fetch matchUser from db, if it does not exist throw and exception.
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchContext.getMatch().getMatchId());

        List<Player> playerList = new ArrayList<>();
        playerList.addAll(inningContext.getBattingPlayerList());
        playerList.addAll(inningContext.getBowlingPlayerList());
        List<MatchUserStats> matchUserStatsList1 = new ArrayList<>();

        //update team points
        for(MatchUserStats matchUserStats: matchUserStatsList){
            matchUserStatsList1.add(updateTeamPoints(matchUserStats,playerList));
        }

        //update credits
        matchUserStatsList1 = distributeCredits(matchUserStatsList1);

        //save the data
        return matchUserStatsRepo.saveAll(matchUserStatsList1);

    }
    private MatchUserStats updateTeamPoints(MatchUserStats matchUserStats, List<Player> playerList){
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

    private List<MatchUserStats> distributeCredits(List<MatchUserStats> matchUserStatsList) throws Exception{
        //Sorting the array based on team points (Descending order)
        matchUserStatsList.sort(new Comparator<MatchUserStats>() {
            @Override
            public int compare(MatchUserStats m1, MatchUserStats m2) {
                return Integer.compare(m2.getTeamPoints(), m1.getTeamPoints());
            }
        });

        int creditPool=0;
        int numberOfWinners = 0;
        int winnerPoints = matchUserStatsList.get(0).getTeamPoints();
        //Calculating pointsPool and number of winners.
        for (MatchUserStats matchUserStats: matchUserStatsList){
            creditPool+=matchUserStats.getCreditsSpentByUser();
            if(winnerPoints==matchUserStats.getTeamPoints())
                numberOfWinners++;
        }
        //distributing points equally to all the winners
        int pointsToDistribute = creditPool/numberOfWinners;
        if(numberOfWinners!=2 && matchUserStatsList.size()!=2) {
            for (MatchUserStats matchUserStats : matchUserStatsList) {
                if (numberOfWinners == 0) {
                    matchUserStats.setCreditChange(-matchUserStats.getCreditsSpentByUser());
                } else {
                    matchUserStats.setCreditChange(pointsToDistribute);
                    numberOfWinners--;
                }
            }
        }
        userService.addCreditsUsingMatchUserStats(matchUserStatsList);
        return matchUserStatsList;
    }

    public List<MatchUserStats> findByMatchId(String matchId) throws Exception{

        List<MatchUserStats> matchUserStatsList= matchUserStatsRepo.findByMatchId(matchId);
        if(matchUserStatsList==null){
            throw new Exception("No users registered for this match");
        }
        return matchUserStatsList;
    }
}
