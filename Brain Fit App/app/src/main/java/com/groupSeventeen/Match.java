package com.groupSeventeen;

import com.groupSeventeen.Util.Gate;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Match implements Serializable {
    private User player1;
    private User player2;
    private int sentOnPlayer1, sentOnPlayer2;
    private int indexPlayer1, indexPlayer2;
    //unified question list
    private List<Question> questions = new ArrayList<>();
    //added answer lists
    private List<Integer> answersPlayer1 = new ArrayList<>();
    private List<Integer> answersPlayer2 = new ArrayList<>();
    private boolean matched;
    private boolean playAgain1 = false;
    private boolean playAgain2 = false;
    //added updateQuestionsOnServer boolean
    private boolean updateQuestionsOnServer = false;

    public Match(User player1){
        this.player1 = player1;
        player2 = null;
        matched = false;
    }

    public Match(User player1, User player2, int sentOnPlayer1, int sentOnPlayer2, int indexPlayer1, int indexPlayer2, List<Question> questions, List<Integer> answersPlayer1, List<Integer> answersPlayer2, boolean matched, boolean playAgain1, boolean playAgain2) {
        this.player1 = player1;
        this.player2 = player2;
        this.sentOnPlayer1 = sentOnPlayer1;
        this.sentOnPlayer2 = sentOnPlayer2;
        this.indexPlayer1 = indexPlayer1;
        this.indexPlayer2 = indexPlayer2;
        this.questions = questions;
        this.answersPlayer1 = answersPlayer1;
        this.answersPlayer2 = answersPlayer2;
        this.matched = matched;
        this.playAgain1 = playAgain1;
        this.playAgain2 = playAgain2;
    }

    private void addQuestionsPlayer1(){
        Gate gate = Usersession.getInstance().getGate();
        Set<Question> inbox = gate.getInboxFromServer(player1.getId(),2);
        int random = 1;
        if (inbox == null) {
            random += 2;
        } else {
            questions.addAll(inbox);
            random += 2 - inbox.size();
        }
        questions.addAll(gate.getRandomQuestionsFromServer(random));
    }

    private void addQuestionsPlayer2(){
        Gate gate = Usersession.getInstance().getGate();
        Set<Question> inbox = gate.getInboxFromServer(player2.getId(),2);
        int random = 0;
        if (inbox == null) {
            random += 2;
            questions.addAll(gate.getRandomQuestionsFromServer(random));
        } else if (inbox.size() < 2){
            questions.addAll(inbox);
            random += 2 - inbox.size();
            questions.addAll(gate.getRandomQuestionsFromServer(random));
        } else {
            questions.addAll(inbox);
        }
    }

    public void addNewQuestions(){
        addQuestionsPlayer1();
        addQuestionsPlayer2();
        answersPlayer1 = new ArrayList<>();
        answersPlayer2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            answersPlayer1.add(-1);
            answersPlayer2.add(-1);
        }
    }

    public void setPlayerNull() {
        if (isPlayer1())
            player1 = null;
        else
            player2 = null;
    }

    public boolean isOtherNull() {
        if (isPlayer1())
            return player2 == null;
        return player1 == null;
    }

    public User getPlayer1() {
        return player1;
    }

    /*public void setPlayer1(User player1) {
        this.player1 = player1;
    }*/

    public User getPlayer2() {
        return player2;
    }

    /*public void setPlayer2(User player2) {
        this.player2 = player2;
    }*/

    public List<Question> getQuestions() {
        return questions;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getSentOn() {
        if (isPlayer1())
            return sentOnPlayer1;
        return sentOnPlayer2;
    }

    public int getIndex() {
        if (isPlayer1())
            return indexPlayer1;
        return indexPlayer2;
    }

    public int getSentOnPlayer1() {
        return sentOnPlayer1;
    }

    public int getSentOnPlayer2() {
        return sentOnPlayer2;
    }

    public int getIndexPlayer1() {
        return indexPlayer1;
    }

    public int getIndexPlayer2() {
        return indexPlayer2;
    }

    public boolean isPlayAgain1() {
        return playAgain1;
    }

    public void setPlayAgain1(boolean playAgain) {
        this.playAgain1 = playAgain;
    }

    public boolean isPlayAgain2() {
        return playAgain2;
    }

    public void setPlayAgain2(boolean playAgain2) {
        this.playAgain2 = playAgain2;
    }

    public boolean isUpdateQuestionsOnServer() {
        return updateQuestionsOnServer;
    }

    public int answerCurrentQuestion(int answer) {
        int correct = -1;
        if (isPlayer1()) {
            Question current = questions.get(indexPlayer1);
            correct = current.answer(answer);
            answersPlayer1.add(correct);
            if (answer == -1)
                sentOnPlayer1++;
            indexPlayer1++;
        } else {
            Question current = questions.get(indexPlayer2);
            correct = current.answer(answer);
            answersPlayer2.add(correct);
            if(answer == -1)
                sentOnPlayer2++;
            indexPlayer2++;
        }
        return correct;
    }

    public int getLatestAnswer() {
        if (isPlayer1()) {
            if (indexPlayer1 == 0)
                return -1;
            return answersPlayer1.get(indexPlayer1 - 1);
        }
        if (indexPlayer2 == 0)
            return -1;
        return answersPlayer2.get(indexPlayer2 - 1);
    }

    public int getLatestAnswerOther() {
        if (isPlayer1()) {
            if (indexPlayer2 == 0)
                return -1;
            return answersPlayer2.get(indexPlayer2 - 1);
        }
        if (indexPlayer1 == 0)
            return -1;
        return answersPlayer1.get(indexPlayer1 - 1);
    }

    public boolean otherAnswered() {
        if (isPlayer1()) {
            return indexPlayer2 >= indexPlayer1;
        }
        return indexPlayer2 <= indexPlayer1;
    }

    public Question getLatestQuestion() {
        if (isPlayer1()) {
            if (indexPlayer1 == 0)
                return questions.get(0);
            return questions.get(indexPlayer1 - 1);
        }
        if (indexPlayer2 == 0)
            return questions.get(0);
        return questions.get(indexPlayer2 - 1);
    }

    public Question getCurrentQuestion() {
        if (isPlayer1())
            return questions.get(indexPlayer1);
        return questions.get(indexPlayer2);
    }

    public List<Integer> getAnswersOther() {
        if (isPlayer1())
            return answersPlayer2;
        return answersPlayer1;
    }

    public List<Integer> getAnswers() {
        if (isPlayer1())
            return answersPlayer1;
        return answersPlayer2;
    }

    public boolean isPlayer1() {
        return Usersession.getInstance().getUser().getId() == player1.getId();
    }

    public void resetMatch() {
        updateQuestionsOnServer = true;
        questions = new ArrayList<>();
        addNewQuestions();
    }

    public List<Integer> getAnswersPlayer1() {
        return answersPlayer1;
    }

    public List<Integer> getAnswersPlayer2() {
        return answersPlayer2;
    }
}
