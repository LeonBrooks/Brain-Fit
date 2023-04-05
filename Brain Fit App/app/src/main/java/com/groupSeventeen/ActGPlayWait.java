package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActGPlayWait extends TTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_play_wait);

        Match match = (Match) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        //TODO timeout if opponent doesnt answer
        if (match.getQuestions().size() < 5) {
            Usersession.switchActivity(this, ActGPlayAnswer.class, match);
        }

        SpeechHandler handler = new SpeechHandler(this);
        ExecutorService service = Executors.newFixedThreadPool(2);
        final Future<Integer> f = service.submit(handler);


        ExecutorService serviceListener = Executors.newFixedThreadPool(2);
        final OpponentListener listener = new OpponentListener(match, this);
        final Future<?> fListener = serviceListener.submit(listener);
    }

    private class OpponentListener implements Runnable {
        private Match match;
        private AppCompatActivity activity;

        public OpponentListener(Match match, AppCompatActivity activity) {
            this.match = match;
            this.activity = activity;
        }

        @Override
        public void run() {
            match = Usersession.getInstance().waitForOpponent(match).first;
            Usersession.switchActivity(activity, ActGPlayAnswer.class, match);
        }
    }
}
