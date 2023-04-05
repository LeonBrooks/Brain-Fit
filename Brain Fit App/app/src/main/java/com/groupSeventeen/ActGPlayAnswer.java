package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActGPlayAnswer extends TTSActivity {
    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_play_answer);

        final TextView question = findViewById(R.id.text_agpa_question);
        final TextView scoreBoard = findViewById(R.id.text_agpa_Points);

        match = (Match) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        if (match.getQuestions().size() < 5) {
            Usersession.switchActivity(this, ActGPlayAnswer.class, match);
        }


        Question latest = match.getLatestQuestion();
        String questionText = latest.getText();
        question.setText(questionText);

        showResult(match.getLatestAnswer(), match);
        //evaluate score: unter einbetracht des ergebnisses des anderen --> abchecken, ob Gegener auch richtig war
        int points = showScore(match.getLatestAnswer(), match.getLatestAnswerOther(), scoreBoard);

        SpeechHandler handler = new SpeechHandler(this, match.getLatestAnswer(), latest.getAnswers()[latest.getCorrectAnswer()], points);
        ExecutorService service = Executors.newFixedThreadPool(1);
        final Future<Integer> f = service.submit(handler);

        Button b = findViewById(R.id.button_agpa_Continue);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.cancel(true);
                next();
            }
        });
    }

    public void next() {
        SpeechHandler.tts.stop();
        if (match.getIndex() == 5) {
            Usersession.switchActivity(ActGPlayAnswer.this, ActGEndNewGame.class, match);
        }  else {
            if (match.getSentOn() == 2) {
                Usersession.switchActivity(ActGPlayAnswer.this, ActGPlayQuestion.class, match);
            } else {
                Usersession.switchActivity(ActGPlayAnswer.this, ActGPlayForward.class, match);
            }
        }
    }

    private void showResult(int correct, Match match){
        // User has chosen the right answer
        final TextView labelCorrectUR = findViewById(R.id.text_agpa_SolutionWasRightLabel);
        final TextView rightanswerUR = findViewById(R.id.text_agpa_MiddleRightAnswer);
        // User has chosen the false answer
        final TextView labelRightAnswerUF = findViewById(R.id.text_agpa_FalseAnswerLabel);
        final TextView rightAnswerUF = findViewById(R.id.text_agpa_RightAnswer);
        final TextView sentOn = findViewById(R.id.test_agpa_QuestionSentOn);
        final TextView questionText = findViewById(R.id.text_agpa_question);
        final TextView line1 = findViewById(R.id.line_agpa_Line1);
        final TextView line2 = findViewById(R.id.line_agpa_Line2);

        Question latestQuestion = match.getLatestQuestion();

        if(correct == 2){
            // Show correct elements
            questionText.setVisibility(View.VISIBLE);
            labelRightAnswerUF.setVisibility(View.GONE);
            rightAnswerUF.setVisibility(View.GONE);
            labelCorrectUR.setVisibility(View.VISIBLE);
            rightanswerUR.setVisibility(View.VISIBLE);
            sentOn.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            //get right answer
            String rightAnswerText = latestQuestion.getAnswers()[latestQuestion.getCorrectAnswer()];
            rightanswerUR.setText(rightAnswerText);
        } else if (correct == 1){
            questionText.setVisibility(View.GONE);
            labelRightAnswerUF.setVisibility(View.GONE);
            rightAnswerUF.setVisibility(View.GONE);
            labelCorrectUR.setVisibility(View.GONE);
            rightanswerUR.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            sentOn.setVisibility(View.VISIBLE);
        } else {
            questionText.setVisibility(View.VISIBLE);
            // Show false elements
            labelCorrectUR.setVisibility(View.GONE);
            rightanswerUR.setVisibility(View.GONE);
            sentOn.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            //get right answer
            String rightAnswerText = latestQuestion.getAnswers()[latestQuestion.getCorrectAnswer()];
            rightAnswerUF.setText(rightAnswerText);
        }
    }

    private int showScore(int userCorrect, int opponentCorrect, TextView scoreBoard){
        String scoreboardText;
        int points = 0;
        if(userCorrect == 2) {
            if (opponentCorrect == 2) {
                scoreboardText = "Right!\n- 2 Points -\nYour opponent was also right";
                points = 2;
            } else if (opponentCorrect == -1) {
                scoreboardText = "Right!\n- 2 Points -\nYour opponent left the game";
                points = 2;
            } else {
                scoreboardText = "Right!\n- 4 Points -\nYour opponent was wrong or gave this question to a mate";
                points = 4;
            }
        } else if (userCorrect == 1) {
            if (opponentCorrect == 2) {
                scoreboardText = "- 0 Points -\nYour opponent answered this question correctly";
            } else if (opponentCorrect == -1) {
                scoreboardText = "- 1 Point -\nYour opponent left the game";
                points = 1;
            } else {
                scoreboardText = "- 2 Points -\nYour opponent was wrong or gave this question to a mate";
                points = 2;
            }
        }else{
            if (opponentCorrect == 2) {
                scoreboardText = "False!\n- 0 Points -\nYour opponent answered this question correctly";
            } else if (opponentCorrect == -1) {
                scoreboardText = "False!\n- 0 Points -\nYour opponent left the game";
            } else {
                scoreboardText = "False!\n- 0 Points -\nYour opponent was also wrong or gave this question to a mate";
            }
        }
        scoreBoard.setText(scoreboardText);
        return points;
    }
}
