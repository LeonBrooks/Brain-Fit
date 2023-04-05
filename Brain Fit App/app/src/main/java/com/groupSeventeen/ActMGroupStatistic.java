package com.groupSeventeen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.groupSeventeen.Util.HelpMe;

import java.util.List;

public class ActMGroupStatistic extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_group_statistic);

        Intent intent = getIntent();

        final Team team = (Team) intent.getSerializableExtra(Usersession.MESSAGE_ACTIVITY);

        final TextView title = findViewById(R.id.text_amg_Title);
        title.setText("Team: " + team.getTeamname());

        final TextView teampoints = findViewById(R.id.text_amg_score);
        teampoints.setText("Score: " + team.getTeamPoints());

        // Container for list of statistics
        /*
            Creates the list of statistics
            OnCreate needs to be recalled when list changes
         */
        final LinearLayout playerContainer = findViewById(R.id.ll_amg_globallist);

        // Sort users by points // TODO: sorting untested, but who can mess up a bubblesort
        final List<User> users = HelpMe.sortUserListDesc(team.getUsers());
        // Fills the list with users
        for (int i = 0; i < users.size(); i++) {

            final TextView user = new TextView(this);
            final TextView score = new TextView(this);
            user.setText(users.get(i).getUsername());
            score.setText(Integer.toString(users.get(i).getPoints()));
            score.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            user.setTextSize(18);
            score.setTextSize(18);
            user.setWidth((int) HelpMe.convertDpToPixel(200, ActMGroupStatistic.this));
            score.setWidth((int) HelpMe.convertDpToPixel(100, ActMGroupStatistic.this));

            user.setBackgroundColor(HelpMe.color(127/(i+1), 0, 0, 255));
            score.setBackgroundColor(HelpMe.color(127/(i+1), 0, 0, 255));

            final LinearLayout line = new LinearLayout(this);
            line.addView(user);
            line.addView(score);

           playerContainer.addView(line);
        }

        final Button backButton = findViewById(R.id.button_amg_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMGroupStatistic.this, ActMStatistic.class);
            }
        });

    }

}
