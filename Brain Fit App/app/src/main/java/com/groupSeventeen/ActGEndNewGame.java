package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.groupSeventeen.Util.Gate;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ActGEndNewGame extends TTSActivity {
    private Future<?> future;
    private Match match;
    private boolean pressedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_end_new_game);

        match = (Match) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        Usersession.getInstance().calculateScore(match);

        Button end = findViewById(R.id.button_agen_No);
        Button cont = findViewById(R.id.button_agen_Yes);

        SpeechHandler handler = new SpeechHandler(this);
        ExecutorService service = Executors.newFixedThreadPool(1);
        final Future<Integer> f = service.submit(handler);

        pressedButton = false;

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (future != null)
                    future.cancel(true);
                stopPlaying(match);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pressedButton) {
                    submitResult(true);
                }
            }
        });
    }

    public void submitResult(boolean cont) {
        SpeechHandler.tts.stop();
        pressedButton = true;
        if (cont) {
            ExecutorService service = Executors.newFixedThreadPool(1);
            MatchWaiter waiter = new MatchWaiter(ActGEndNewGame.this, match);
            future = service.submit(waiter);
        } else {
            stopPlaying(match);
        }
    }

    private void stopPlaying(Match match) {
        match.setPlayerNull();
        match = Gate.sendMatchToServer(match);
        Usersession.switchActivity(this, ActGEndForward.class);
    }



    private class MatchWaiter implements Runnable {
        private Match match;
        private AppCompatActivity activity;

        public MatchWaiter(AppCompatActivity activity, Match match) {
            this.match = match;
            this.activity = activity;
        }

        @Override
        public void run() {
            continuePlaying();
        }

        private void continuePlaying() {
            if (match.isPlayer1()) {
                match.setPlayAgain1(true);
            } else {
                match.setPlayAgain2(true);
            }

            match = Gate.sendMatchToServer(match);
            if (match == null) {
                Usersession.switchActivity(activity, ActGEndForward.class);
                return;
            }

            while (match == null || !(match.isPlayAgain1() && match.isPlayAgain2())) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (match == null || match.isOtherNull()) {
                    //Toast.makeText(activity, "Opponent left game!", Toast.LENGTH_SHORT).show();
                    //match.setPlayerNull();
                    Usersession.switchActivity(activity, ActGEndForward.class);
                    return;
                }
                if (!match.isPlayer1() && (match.getIndexPlayer1() == 0 || match.getIndexPlayer2() == 0)) {
                    break;
                }
                match.setQuestions(new ArrayList<Question>());
                match = Gate.sendMatchToServer(match);
            }
            if (match.isPlayer1()) {
                match.resetMatch();
                match = Gate.sendMatchToServer(match);
            } else {
                while (match == null || match.getIndexPlayer2() != 0) {
                    if (match == null) {
                        Usersession.switchActivity(activity, ActGEndForward.class);
                        return;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    match.setQuestions(new ArrayList<Question>());
                    match = Gate.sendMatchToServer(match);
                }
            }

            Usersession.switchActivity(activity, ActGPlayForward.class, match);
        }
    }
}
