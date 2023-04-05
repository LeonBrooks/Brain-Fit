package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.groupSeventeen.Util.Gate;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ActMMatchSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_match_search);

        //start a new Thread/AsyncTask/etc for matchmaking
        ExecutorService service = Executors.newFixedThreadPool(1);
        final MatchMaker matchMaker = new MatchMaker(this);
        final Future<?> f = service.submit(matchMaker);

        final Button cancelButton = findViewById(R.id.button_amm_Cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                matchMaker.cancelled = true;
                Match match = matchMaker.match;
                match.setPlayerNull();
                Gate.sendMatchToServer(match);
                f.cancel(true);
                Usersession.switchActivity(ActMMatchSearch.this, ActMTeamHQ.class);
            }
        });
    }

    private static class MatchMaker implements Runnable {
        AppCompatActivity activity;
        public boolean cancelled = false;
        public Match match;

        public MatchMaker(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            match = new Match(Usersession.getInstance().getUser());
            int tries = 1;
            while (match == null || !match.isMatched()){
                if (cancelled) {
                    match.setPlayerNull();
                    match = Gate.sendMatchToServer(match);
                    return;
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                match = Gate.sendMatchToServer(match);
                tries -= 1;
            }
            if(!match.isMatched()){
            }else{
                if(match.isPlayer1()){
                    match.resetMatch();
                    match = Gate.sendMatchToServer(match);
                }else{
                    // update questionlist set by player1
                    while (match.getQuestions() == null || match.getQuestions().size() < 5) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        match = Gate.sendMatchToServer(match);
                    }
                }
            }
            Usersession.switchActivity(activity, ActGPlayForward.class, match);
        }
    }
}
