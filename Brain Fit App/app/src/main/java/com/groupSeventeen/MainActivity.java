package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> result = new ArrayList<>();
    private boolean correctInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //((TextView)findViewById(R.id.resultText)).setText("Hat geklappt!");

    }

    public void doStuff() {
        /*Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk now!");
        i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1000);
        Toast.makeText(getApplicationContext(), "Talk now", Toast.LENGTH_LONG).show();
        //TODO: voice output
        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_LONG).show();
        }
        // TODO: check if the right string
        if (result.equals("a")) {
            correctInput = true;
            Toast.makeText(getApplicationContext(), "It worked!", Toast.LENGTH_LONG).show();
        } else {
            // voice output: "xxx is no vaild input"
            Toast.makeText(getApplicationContext(), "Wrong input " + result, Toast.LENGTH_LONG).show();
        }
        */

        String rec = PackageManager.FEATURE_MICROPHONE;
        if (!rec.equals("android.hardware.microphone")) {
            Toast.makeText(this, "No microphone", Toast.LENGTH_SHORT).show();
        } else {
            Recognizer voice = new Recognizer(this);
            voice.startListening();
            VoiceListener listener = new VoiceListener(voice, this);
            ExecutorService service = Executors.newFixedThreadPool(1);
            Future<?> future = service.submit(listener);
        }

    }

    public void onClick(View v) {
        //while(!correctInput) {
        System.out.println("pressed");
        doStuff();
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK && data != null) {
                result = data.getStringArrayListExtra((RecognizerIntent.EXTRA_RESULTS));
                ((TextView) findViewById(R.id.resultText)).setText(result.get(0));

            }
        }
    }

    private class VoiceListener implements Runnable {
        private Recognizer voice;
        private Context context;

        public VoiceListener(Recognizer voice, Context context) {
            this.context = context;
            this.voice = voice;
        }

        @Override
        public void run() {
            while (true) {

                String res = voice.words;
            }
        }
    }

}
