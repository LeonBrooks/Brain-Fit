package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.groupSeventeen.Util.Gate;

import java.util.List;

public class ActGEndResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_end_result);

        Button b = findViewById(R.id.button_ager_continue);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usersession.switchActivity(ActGEndResult.this, ActMTeamHQ.class);
            }
        });

        List<Integer> results = Usersession.getInstance().getAndResetSetPoints();
        int sum = 0;
        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i);
        }
        Gate.sendUserToServer(Usersession.getInstance().getUser());
        Team team = Gate.getTeamFromServer(Usersession.getInstance().getUser().getTeam());
        team.addTeamPoints(sum);
        Gate.sendTeamToServer(team);

        // Container for list of points from the game
        final LinearLayout questionContainer = findViewById(R.id.ll_ager_results);
        for (int i = 0; i < results.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText("Points in round " + i + ": " + results.get(i)); // placeholder text
            textView.setTextSize(16);
            questionContainer.addView(textView);
        }

        final TextView textView = findViewById(R.id.text_ager_SumPoints);
        textView.setText(Integer.toString(sum));
    }
}
