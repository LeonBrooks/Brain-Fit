package com.groupSeventeen;

import java.io.Serializable;

public class Question implements Serializable {
    private final int id;
    private final String category;
    private final String text;
    private final String[] answers;
    // between 0-5
    private final int correctAnswer;

    //answeredCorrectly removed/moved to Match
    //private int answeredCorrectly;
    // -1 not answered, 0 false, 1 sentOn ,2 correct

    public Question(int id, String category, String text,  String[] answers, int correctAnswer) {
        this.id = id;
        this.category = category;
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    // (-1 not answered), 0 false, 1 sentOn ,2 correct
    public int answer(int answer) {
        if (answer == -1)
            return 1;
        if (answer == correctAnswer)
            return 2;
        return 0;
    }

    public String getCategory() {
        return category;
    }
}
