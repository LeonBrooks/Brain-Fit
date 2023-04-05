package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groupSeventeen.Util.Gate;
import com.groupSeventeen.Util.HelpMe;

import java.util.List;

public class ActMStatistic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_statistic);

        final List<Team> teams = Gate.getLeaderboardFromServer(30);

        final Button backButton = findViewById(R.id.button_ams_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMStatistic.this, ActMTeamHQ.class);
            }
        });

        // Container for list of statistics
        /*
            Creates the list of statistics
            OnCreate needs to be recalled when list changes
         */
        final LinearLayout teamsContainer = findViewById(R.id.ll_ams_globallist);

        // Fills the achievement list with achievement containers
        for (int i = 0; i < teams.size(); i++) {
            final TextView team = new TextView(this);
            final TextView score = new TextView(this);
            team.setText("#" + i + " : " + teams.get(i).getTeamname());
            score.setText(Integer.toString(teams.get(i).getTeamPoints()));
            score.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            team.setTextSize(18);
            team.setWidth((int) HelpMe.convertDpToPixel(200, ActMStatistic.this));
            score.setTextSize(18);
            score.setWidth((int) HelpMe.convertDpToPixel(100, ActMStatistic.this));

            team.setBackgroundColor(HelpMe.color(127/(i+1), 0, 0, 255));
            score.setBackgroundColor(HelpMe.color(127/(i+1), 0, 0, 255));

            final LinearLayout line = new LinearLayout(this);
            line.addView(team);
            line.addView(score);

            final int I = i;
            line.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Call new activity with parameters
                    Usersession.switchActivity(ActMStatistic.this,ActMGroupStatistic.class, teams.get(I));
                }
            });

            teamsContainer.addView(line);
        }
    }

}
