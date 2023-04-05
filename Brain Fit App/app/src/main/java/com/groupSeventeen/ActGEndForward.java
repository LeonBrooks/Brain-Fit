package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groupSeventeen.Util.Gate;
import com.groupSeventeen.Util.HelpMe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ActGEndForward extends AppCompatActivity {
    // Does this need to be here, or can it be inside of the onCreate function
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_end_forward);

        //T create list of sent on question, send on questions to team members
        final List<Question> forward = Usersession.getInstance().getForwardedQuestions();
        // Get users of team
        team = Usersession.getInstance().loadTeam();
        final List<User> teamUsers = new ArrayList(team.getUsers());

        int selfIndex = -1;
        for (int i = 0; i < teamUsers.size(); i++) {
            User user = teamUsers.get(i);
            if (user.getId() == Usersession.getInstance().getUser().getId()) {
                selfIndex = i;
                break;
            }
        }

        if (selfIndex >= 0)
            teamUsers.remove(selfIndex);

        // Help for forwarding
        final TextView help = findViewById(R.id.text_agef_Help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // On click
                if (help.getText().length() == 5) {
                    help.setText("Click on a User to choose whom to forward the questions.\n\nClicking on question decides which question is forwarded");
                } else {
                    help.setText("Help?");
                }
            }
        });

        // Question Forwarding
        /*
            Clicking on a User to choose a user to whom you forward the questions.
            If no user chosen default user is last in list

            Clicking on question decides which question is forwarded to the currently chosen user.
         */
        final HashMap<Question, User> QUMap = new HashMap<>();
        // Container for list of profiles
        /*
            Creates the list of profiles and the action for OnClick
         */
        final LinearLayout userContainer = findViewById(R.id.ll_agef_teamList);

        // Needed to determine color of text
        final List<TextView> user_text_list = new ArrayList<>();
        final List<TextView> current_user = new ArrayList<>();

        // Contains index of current chosen user;
        final AtomicInteger chosen_user = new AtomicInteger(0);

        // insert Teamsize here
        int ts = teamUsers.size();

        for (int i = 0; i < ts; i++) {
            final TextView textView = new TextView(this);
            user_text_list.add(textView);
            // Set color for each text
            textView.setTextColor(HelpMe.scaledColor(i, ts));
            textView.setText(teamUsers.get(i).getUsername()); // Display username
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Set Color of currently chosen text
                    for (TextView user : user_text_list) {
                        user.setBackgroundColor(HelpMe.color(0, 0, 0, 0));
                    }
                    textView.setBackgroundColor(HelpMe.color(32, 0, 0, 255));
                    current_user.remove(0);
                    current_user.add(textView);
                    chosen_user.set(user_text_list.indexOf(textView));
                    Toast.makeText(ActGEndForward.this, "Profile " + chosen_user.get(), Toast.LENGTH_SHORT).show(); // Debug
                }
            });
            if (i == 0) {
                textView.setBackgroundColor(HelpMe.color(32, 0, 0, 255));
            }
            userContainer.addView(textView);
        }
        // Chosen user set as first user in list
        current_user.add(user_text_list.get(0));

        // Container for list of questions
        /*
            Creates the list of questions and the action for OnClick
         */
        final LinearLayout questionContainer = findViewById(R.id.ll_agef_questionList);
        final List<TextView> question_text_list = new ArrayList<>();
        for (int i = 0; i < forward.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setText("Question " + i + " Category: " + forward.get(i).getCategory()); // Display question category
            final int index = i;
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Setting the color for text
                    textView.setBackgroundColor(HelpMe.color(32, 0, 0, 255));
                    textView.setTextColor(current_user.get(0).getCurrentTextColor());
                    textView.setBackgroundColor(HelpMe.color(0, 0, 0, 0));

                    // Add or Replace entry in HashMap
                    // This should work. Gets user and question from the list that server gives us. Index is determined by index of their corresponding textview index
                    QUMap.put(forward.get(index), teamUsers.get(chosen_user.get()));
                }
            });
            question_text_list.add(textView);
            questionContainer.addView(textView);
        }

        final Button submit = findViewById(R.id.button_agef_Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On click
                if (QUMap.size() < forward.size()) {
                    // popup not done with forwarding
                    Toast.makeText(ActGEndForward.this, "Unassigned questions!", Toast.LENGTH_SHORT).show();
                } else {
                    // Forward questions
                    for (Map.Entry<Question, User> pair : QUMap.entrySet()){
                        Gate.sendInboxToServer(pair.getValue().getId(), pair.getKey().getId());
                    }
                    Usersession.getInstance().clearForwardQuestions();
                    Usersession.switchActivity(ActGEndForward.this, ActGEndResult.class);
                }
            }
        });
    }

}
