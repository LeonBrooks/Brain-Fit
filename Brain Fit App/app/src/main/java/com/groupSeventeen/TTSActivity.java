package com.groupSeventeen;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TTSActivity extends AppCompatActivity {
    private ArrayList<String> result = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK && data != null) {
                result = data.getStringArrayListExtra((RecognizerIntent.EXTRA_RESULTS));
                synchronized (SpeechHandler.waitObject){
                    SpeechHandler.waitObject.notifyAll();
                }
                //((TextView) findViewById(R.id.resultText)).setText(result.get(0));

            }
        }
    }

    public ArrayList<String> getResult() {
        return result;
    }
}
