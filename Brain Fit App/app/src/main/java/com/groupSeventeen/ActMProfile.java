package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groupSeventeen.Util.Gate;
import com.groupSeventeen.Util.HelpMe;

import java.util.ArrayList;
import java.util.List;

public class ActMProfile extends AppCompatActivity {
    private User profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_profile);

        profile = (User) getIntent().getSerializableExtra(Usersession.MESSAGE_ACTIVITY);
        Team team = Gate.getTeamFromServer(profile.getTeam());

        // Container for profile stat
        /*
            Creates the list of stats
            OnCreate needs to be recalled when list changes
         */
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        left.add("Team: ");
        right.add(team.getTeamname());
        left.add("Points: ");
        right.add("" + profile.getPoints());
        left.add("Correctly answered: ");
        right.add("" + profile.getQuestionsRight());
        left.add("Falsely answered: ");
        right.add("" + profile.getQuestionsWrong());
        left.add("Questions sent on: ");
        right.add("" + profile.getQuestionsSentOn());
        left.add("Questions in inbox: ");
        right.add("" + profile.getInbox().size());
        final LinearLayout profileContainer = findViewById(R.id.ll_amp_playerstatlist);
        final TextView name = findViewById(R.id.text_amp_Title);
        final Button backButton = findViewById(R.id.button_amp_Back);
        name.setText(profile.getUsername());
        name.setTextSize(50);

        for (int i = 0; i < left.size(); i++) {
            TextView t1 = new TextView(this);
            TextView t2 = new TextView(this);
            t1.setText(left.get(i)); // placeholder text
            t1.setTextSize(18);
            t1.setWidth((int) HelpMe.convertDpToPixel(200, ActMProfile.this));

            t2.setText(right.get(i)); // placeholder text
            t2.setTextSize(18);
            t2.setWidth((int) HelpMe.convertDpToPixel(100, ActMProfile.this));
            t2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            final LinearLayout line = new LinearLayout(this);
            line.addView(t1);
            line.addView(t2);

            profileContainer.addView(line);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usersession.switchActivity(ActMProfile.this, ActMTeamHQ.class);
            }
        });
    }

}
