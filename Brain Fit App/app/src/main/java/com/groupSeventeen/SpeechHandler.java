package com.groupSeventeen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.format.Time;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SpeechHandler implements Callable<Integer> {


    public static enum Usage {
        FORWARD, QUESTION, ANSWER, CONTINUE, WAIT;
    }
    private Usage usage;

    private Question question;
    private TTSActivity context;
    private Intent intent;
    private List<String> answers;
    private int correct;
    private int points;
    private String correctAnswer;

    public static TextToSpeech tts;
    private static boolean ttsInit = false;
    public static Object waitObject = new Object();

    public SpeechHandler(ActGPlayForward context, Question question) {
        this.usage = Usage.FORWARD;
        this.question = question;
        this.context = context;
        initTTS();
    }

    public SpeechHandler(ActGPlayQuestion context, Question question, List<String> answers) {
        this.usage = Usage.QUESTION;
        this.question = question;
        this.context = context;
        this.answers = answers;
        initTTS();
    }

    public SpeechHandler(ActGPlayAnswer context, int correct, String rightAnswer, int points) {
        this.usage = Usage.ANSWER;
        this.correct = correct;
        this.correctAnswer = rightAnswer;
        this.points = points;
        this.context = context;
        initTTS();
    }

    public SpeechHandler(ActGEndNewGame context) {
        this.usage = Usage.CONTINUE;
        this.context = context;
        initTTS();
    }

    public SpeechHandler(ActGPlayWait context) {
        this.usage = Usage.WAIT;
        this.context = context;
        initTTS();
    }

    @Override
    public Integer call() throws Exception {
        while(!ttsInit);
        switch(usage) {
            case FORWARD:
                initIntent();
                return ttsForward();
            case QUESTION:
                initIntent();
                return ttsQuestion();
            case ANSWER:
                return ttsAnswer();
            case WAIT:
                return ttsWait();
            case CONTINUE:
                initIntent();
                return ttsContinue();
        }
        return -2;
    }

    private void initTTS() {
        if (tts == null) {
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setSpeechRate((float) 0.75);
                    ttsInit = true;
                }
            });
        }
    }

    private void initIntent() {
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk now!");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 5000);
    }

    private int ttsForward() {
        readText("The topic of this question is " + question.getCategory() + ". Do you want to send on this question?");
        try {
            context.startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Speech not supported", Toast.LENGTH_LONG).show();
        }

        try {
            synchronized (waitObject){
                waitObject.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> result = context.getResult();
        System.out.println(result);
        if (result.contains("yes")) {
            ((ActGPlayForward) context).submitResult(true);
            return 1;
        } else if (result.contains("no")) {
            ((ActGPlayForward) context).submitResult(false);
            return 0;
        }
        return -2;
    }

    private int ttsQuestion() {
        readText(question.getText());

        for (String s : answers) {
            readText(s);
        }

        try {
            context.startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Speech not supported", Toast.LENGTH_LONG).show();
        }

        try {
            synchronized (waitObject){
                waitObject.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> result = context.getResult();
        System.out.println(result);
        if (result.contains("a") || result.contains("A") || result.contains("hey") || result.contains("ey")) {
            ((ActGPlayQuestion) context).submitAnswer(0);
        } else if (result.contains("b") || result.contains("be") || result.contains("B") || result.contains("Be") || result.contains("bee")) {
            ((ActGPlayQuestion) context).submitAnswer(1);
        } else if (result.contains("c") || result.contains("C") || result.contains("ce") || result.contains("Ce") || result.contains("see")) {
            ((ActGPlayQuestion) context).submitAnswer(2);
        } else if (result.contains("d") || result.contains("D") || result.contains("de") || result.contains("De")) {
            ((ActGPlayQuestion) context).submitAnswer(3);
        } else if (answers.size() > 4 && (result.contains("e") || result.contains("E"))) {
            ((ActGPlayQuestion) context).submitAnswer(4);
        } else if (answers.size() > 5 && (result.contains("f") || result.contains("F") || result.contains("ef"))) {
            ((ActGPlayQuestion) context).submitAnswer(5);
        }
        return -2;
    }

    private int ttsAnswer() {
        if (correct == 0) {
            readText("False. Right would be " + correctAnswer + ". You got " + points + " points");
        } else if (correct == 1) {
            readText("Sent on question. You got " + points + " points.");
        } else {
            readText("Correct. You got " + points + " points");
        }
        ((ActGPlayAnswer) context).next();
        return -2;
    }

    private int ttsWait() {
        readText("Waiting for opponent to answer.");
        return -2;
    }

    private int ttsContinue() {
        readText("Continue playing?");

        try {
            context.startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Speech not supported", Toast.LENGTH_LONG).show();
        }

        try {
            synchronized (waitObject){
                waitObject.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> result = context.getResult();
        System.out.println(result);
        if (result.contains("yes")) {
            ((ActGEndNewGame) context).submitResult(true);
            return 1;
        } else if (result.contains("no")) {
            ((ActGEndNewGame) context).submitResult(false);
            return 0;
        }
        return -2;
    }

    private void readText(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean speaking = true;
            while (speaking){
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                speaking = tts.isSpeaking();
            }
        } else {
            Toast.makeText(context, "TTS is not initialized!", Toast.LENGTH_SHORT).show();
        }
    }
}
