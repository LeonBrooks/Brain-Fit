package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActGPlayForward extends TTSActivity {
    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_play_forward);

        final TextView topic = findViewById(R.id.text_agpf_Topic);
        final Button yesToForwarding = findViewById(R.id.button_agpf_Yes);
        final Button noToForwarding = findViewById(R.id.button_agpf_No);

        match = (Match) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        //gettopic
        String topicPlaceHolder = match.getCurrentQuestion().getCategory();
        setTopic(topicPlaceHolder,topic);

        if (match.getIndex() - 2 > match.getSentOn()) {
            Usersession.getInstance().answerQuestion(match, -1);
            Usersession.switchActivity(ActGPlayForward.this, ActGPlayWait.class, match);
            return;
        }

        SpeechHandler handler = new SpeechHandler(this, match.getCurrentQuestion());
        ExecutorService service = Executors.newFixedThreadPool(1);
        final Future<Integer> f = service.submit(handler);

        yesToForwarding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //question in list of forwarding
                f.cancel(true);
                submitResult(true);
            }
        });

        noToForwarding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                f.cancel(true);
                submitResult(false);
            }
        });
    }

    public void submitResult(boolean sendForward) {
        SpeechHandler.tts.stop();
        if (sendForward) {
            Usersession.getInstance().answerQuestion(match, -1);
            Usersession.switchActivity(ActGPlayForward.this, ActGPlayWait.class, match);
        } else {
            Usersession.switchActivity(ActGPlayForward.this, ActGPlayQuestion.class, match);
        }
    }

    private void setTopic(String topic, TextView display){
        display.setText(topic);
    }
}
