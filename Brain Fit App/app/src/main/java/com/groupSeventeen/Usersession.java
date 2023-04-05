package com.groupSeventeen;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.groupSeventeen.Util.Gate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Usersession {
    private static Usersession instance;

    public static final String MESSAGE_ACTIVITY = "com.groupSeventeen.BrainFit.MESSAGE";

    private LocationHandler locationHandler;

    private List<Question> forward = new ArrayList<>();
    private List<Integer> setPoints = new ArrayList<>();

    private User user;
    private Gate gate;

    private Usersession(){

    }

    public void setUser(String email) {
        Usersession instance = getInstance();
        instance.user = Gate.getUserFromServer(Gate.getIdFormServer(email));
    }

    public User getUser() {
        return user;
    }

    public static Usersession getInstance(){
        if(Usersession.instance == null){
            Usersession.instance = new Usersession();
        }
        return Usersession.instance;
    }

    public static void switchActivity(AppCompatActivity from, Class to) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
    }

    public static void switchActivity(AppCompatActivity from, Class to, Serializable m) {
        Intent intent = new Intent(from, to);
        intent.putExtra(MESSAGE_ACTIVITY, m);
        from.startActivity(intent);
    }

    public static void switchActivity(AppCompatActivity from, Class to, String message) {
        Intent intent = new Intent(from, to);
        intent.putExtra(MESSAGE_ACTIVITY, message);
        from.startActivity(intent);
    }
    //Put into MatchSearch to regulate endless loop
    public Match startMatchSearch() {
        Match match = new Match(user);
        int tries = 1;
        while (match == null || !match.isMatched()){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            match = gate.sendMatchToServer(match);
            tries -= 1;
        }
        if(!match.isMatched()){
            // TODO: botgame
        }else{
            if(match.isPlayer1()){
                match.resetMatch();
                match = gate.sendMatchToServer(match);
            }else{
                // update questionlist set by player1
                match = gate.sendMatchToServer(match);
            }
        }
        return match;
    }

    public Pair<Match, Integer> answerQuestion(Match match, int answer) {
        match.answerCurrentQuestion(answer);
        if (answer == -1) {
            forward.add(match.getLatestQuestion());
        }
        match = gate.sendMatchToServer(match);
        return new Pair<>(match, 0);
    }

    //return code -1: other player left match, 0: ok
    public Pair<Match, Integer> waitForOpponent(Match match) {
        boolean answered = false;
        while (!answered) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            match = gate.sendMatchToServer(match);

            if (match.isOtherNull()) {
                return new Pair<>(match, -1);
            }

            answered =  match.otherAnswered();
        }

        return new Pair<>(match, 0);
    }

    // -1 not answered, 0 false, 1 sentOn, 2 correct
    public void calculateScore(Match match) {
        List<Integer> answersOpponent = match.getAnswersOther();
        List<Integer> answers = match.getAnswers();

        int points = 0;
        for(int i = 0; i < 5; i++){
            switch(answers.get(i)){
                //question was answered wrong, user gets no points
                case 0:
                    user.addQuestionWrong(1);
                    break;
                    //question was sent on
                case 1:
                    user.addQuestionSentOn(1);
                    //opponent answered correctly, user gets no points
                    if (i > answersOpponent.size() - 1 || answersOpponent.get(i) == -1) {
                        points += 1;
                    } else if(answersOpponent.get(i) == 2) {
                        break;
                        //opponent didnt answer
                    }else{
                        points += 2;
                        break;
                    }
                    //question was answered correctly
                case 2:
                    user.addQuestionRight(1);
                    //if opponent didnt answer or answered correctly, user gets 2 points
                    points += 2;
                    //if opponent answered false or sent on, user gets 4 points
                    if(i < answersOpponent.size() && (answersOpponent.get(i) == 0
                            || answersOpponent.get(i) == 1)){
                        points += 2;
                        break;
                    }
                    break;
            }

        }
        user.addPoints(points);
        addSetToCounter(points);
    }



    public static User loadProfile(int id){
        return Gate.getUserFromServer(id);
    }

    public static Team loadTeam(int id) {
        return Gate.getTeamFromServer(id);
    }

    public Team loadTeam() {
        return loadTeam(user.getTeam());
    }

    public List<User> globalPlayerStatistic(){
        // TODO: load map of best teams from database
        List<User> playerPointsPlaceholder = new ArrayList<>();
        return playerPointsPlaceholder;
    }

    public List<Team> globalTeamStatistics() {
        //TODO: Load list of best teams from database
        List<Team> teamPointsPlaceholder = new ArrayList<>();
        return teamPointsPlaceholder;
    }

    //placeholder while teams aren't available from server
    public static Team getPlaceholderTeam() {
        return new Team(-1, "PlaceholderTeam");
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public List<Question> getForwardedQuestions() {
        return forward;
    }

    public void addForwardQuestion(Question q) {
        forward.add(q);
    }

    public void clearForwardQuestions() {
        forward.clear();
    }

    public List<Integer> getAndResetSetPoints() {
        List<Integer> result = new ArrayList<>(setPoints);
        setPoints = new ArrayList<>();
        return result;
    }

    public void addSetToCounter(int points) {
        setPoints.add(points);
    }

    public LocationHandler getLocationHandler() {
        return locationHandler;
    }

    public boolean isLocationHandlerSet() {
        return locationHandler != null;
    }

    public double getLongitude() {
        if (locationHandler != null)
            return locationHandler.getLongitude();
        return 0.0;
    }

    public double getLatitude() {
        if (locationHandler != null)
            return locationHandler.getLatitude();
        return 0.0;
    }

    public double getSpeed() {
        if (locationHandler != null)
            return locationHandler.getSpeed();
        return 0.0;
    }

    public void setLocationHandler(LocationHandler locationHandler) {
        this.locationHandler = locationHandler;
    }
}
