package com.groupSeventeen;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private final int id;
    private String username;
    private String email;
    private Set<Integer> inbox = new HashSet<>();
    private int team;
    // location?
    private int points;
    private int gamesWon;
    private int gamesLose;
    private int questionsSentOn;
    private int questionsRight;
    private int questionsWrong;

    public User(int id, String username, String email){
            this.id = id;
            this.username = username;
            this.email = email;
            points = 0;
            gamesWon = 0;
            gamesLose = 0;
            questionsSentOn = 0;
            questionsRight = 0;
            questionsWrong = 0;
            team = -1;
    }

    public User(int id, String username, String email, Set<Integer> inbox, int team, int points, int gamesWon, int gamesLose, int questionsSentOn, int questionsRight, int questionsWrong) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.inbox = inbox;
        this.team = team;
        this.points = points;
        this.gamesWon = gamesWon;
        this.gamesLose = gamesLose;
        this.questionsSentOn = questionsSentOn;
        this.questionsRight = questionsRight;
        this.questionsWrong = questionsWrong;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public void addGameWon(){
        gamesWon += 1;
    }

    public void addGameLose(){
        gamesLose += 1;
    }

    public void addQuestionSentOn(int questionsSentOn){
        this.questionsSentOn += questionsSentOn;
    }

    public void addQuestionRight(int questionsRight){
        this.questionsRight += questionsRight;
    }

    public void addQuestionToQueue(int questionId){
        inbox.add(questionId);
    }

    public void addQuestionToQueue(Set<Integer> questionIds){
        inbox.addAll(questionIds);
    }

    public void addQuestionWrong(int questionsWrong){
        this.questionsWrong += questionsWrong;
    }

    public Set<Integer> getInbox() {
        return inbox;
    }

    public int getPoints() {
        return points;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLose() {
        return gamesLose;
    }

    public int getQuestionsSentOn() {
        return questionsSentOn;
    }

    public int getQuestionsRight() {
        return questionsRight;
    }

    public int getQuestionsWrong() {
        return questionsWrong;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int teamId) {
        this.team = teamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
