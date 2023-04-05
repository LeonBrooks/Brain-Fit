package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class ActGPlayQuestion extends TTSActivity implements LocationListener {
    double currentSpeed;
    private Match match;
    private Button[] answers;
    private int[] answerIndices;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_play_question);

        LocationManager locationManager;
        // source: youtube.com/watch?v=ua4jY0lBvCA
        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permisson not granted
            Toast.makeText(this, "No permission to access location", Toast.LENGTH_SHORT).show();

        } else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            //Location lastLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //currentSpeed = lastLocation.getSpeed();
            //this.onLocationChanged(lastLocation);
        }

        final TextView question = findViewById(R.id.text_agpq_question);
        answers = new Button[6];
        answers[0] = findViewById(R.id.button_agpq_Answer1);
        answers[1] = findViewById(R.id.button_agpq_Answer2);
        answers[2] = findViewById(R.id.button_agpq_Answer3);
        answers[3] = findViewById(R.id.button_agpq_Answer4);
        answers[4] = findViewById(R.id.button_agpq_Answer5);
        answers[5] = findViewById(R.id.button_agpq_Answer6);

        match = (Match) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        if (match.getQuestions().size() < 5) {
            Usersession.switchActivity(this, ActGPlayWait.class, match);
        }

        Question q = match.getCurrentQuestion();
        String questionText = q.getText();
        question.setText(questionText);
        List<String> answerTexts = new LinkedList<String>();

        currentSpeed = Usersession.getInstance().getSpeed();

        int numberOfAnswers = 0;
        if (currentSpeed > 16.0) {
            numberOfAnswers = 6;
        } else if (currentSpeed > 9.0) {
            numberOfAnswers = 4;
        } else if (currentSpeed > 7.0) {
            numberOfAnswers = 5;
        } else {
            numberOfAnswers = 6;
        }


        answerTexts.add(q.getAnswers()[q.getCorrectAnswer()]);
        int pos = 0;
        for (int i = 0; i < numberOfAnswers - 1; i++) {
            if (pos == q.getCorrectAnswer())
                pos++;
            answerTexts.add(q.getAnswers()[pos]);
            pos++;
        }

        answerIndices = new int[6];
        List<Integer> indices = new ArrayList<>();
        indices.add(q.getCorrectAnswer());
        for (int i = 0; i < 6; i++) {
            if (i == q.getCorrectAnswer())
                continue;
            answerIndices[i] = -1;
            indices.add(i);
        }

        List<String> answersTTS = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            if (i < numberOfAnswers) {
                int random = ThreadLocalRandom.current().nextInt(0, answerTexts.size());
                String answerText = answerTexts.remove(random);
                int index = indices.remove(random);
                answerIndices[i] = index;

                answersTTS.add("Answer " + (char) ('A' + i) + ": " + answerText + ".");

                switch (i) {
                    case 0:
                        answers[i].setText("A: " + answerText);
                        break;
                    case 1:
                        answers[i].setText("B: " + answerText);
                        break;
                    case 2:
                        answers[i].setText("C: " + answerText);
                        break;
                    case 3:
                        answers[i].setText("D: " + answerText);
                        break;
                    case 4:
                        answers[i].setText("E: " + answerText);
                        break;
                    case 5:
                        answers[i].setText("F: " + answerText);
                        break;
                }
            } else {
                blurOutAnswerOption(answers[i]);
            }
        }

        SpeechHandler handler = new SpeechHandler(this, match.getCurrentQuestion(), answersTTS);
        ExecutorService service = Executors.newFixedThreadPool(1);
        final Future<Integer> f = service.submit(handler);

        answers[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                selectAnswer(answers[0], answers, answerIndices[0], match);
            }
        });
        answers[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                f.cancel(true);
                selectAnswer(answers[1], answers, answerIndices[1], match);
            }
        });
        answers[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                selectAnswer(answers[2], answers, answerIndices[2], match);
                f.cancel(true);
            }
        });
        answers[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                f.cancel(true);
                selectAnswer(answers[3], answers, answerIndices[3], match);
            }
        });
        answers[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                f.cancel(true);
                // Code here executes on main thread after user presses button
                selectAnswer(answers[4], answers, answerIndices[4], match);
            }
        });
        answers[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                f.cancel(true);
                // Code here executes on main thread after user presses button
                selectAnswer(answers[5], answers, answerIndices[5], match);
            }
        });


    }

    public void submitAnswer(int index) {
        SpeechHandler.tts.stop();
        selectAnswer(answers[index], answers, answerIndices[index], match);
    }

    private void selectAnswer(Button trigger, Button[] allAnswers, int answer, Match match) {
        trigger.setBackgroundColor(Color.BLUE);
        for (Button button : allAnswers) {
            button.setClickable(false);
        }

        Pair<Match, Integer> val = Usersession.getInstance().answerQuestion(match, answer);
        match = val.first;

        Usersession.switchActivity(ActGPlayQuestion.this, ActGPlayWait.class, match);

    }

    private void blurOutAnswerOption(Button answerToBlurOut) {
        answerToBlurOut.setAlpha(.5f);
        answerToBlurOut.setEnabled(false);
    }

    @Override
    public void onLocationChanged(Location location) {
        // km/h
        //currentSpeed = location.getSpeed() * 3.6f;
        //System.out.println(currentSpeed);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
