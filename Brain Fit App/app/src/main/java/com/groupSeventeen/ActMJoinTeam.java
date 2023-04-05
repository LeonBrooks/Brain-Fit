package com.groupSeventeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupSeventeen.Util.Gate;

public class ActMJoinTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_join_team);

        final Button randomTeamButton = findViewById(R.id.button_amj_JoinRandom);
        final Button createTeamButton = findViewById(R.id.button_amj_CreateTeam);
        final Button joinTeamButton = findViewById(R.id.button_amj_JoinTeam);
        final EditText createTeamName = findViewById(R.id.textInput_amj_TeamnameCreateTeam);
        final EditText joinTeamName = findViewById(R.id.textInput_amj_TeamnameJoinTeam);

        randomTeamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Team t = Gate.getRandomTeamFromServer();
                t.addUser(Usersession.getInstance().getUser());
                Usersession.switchActivity(ActMJoinTeam.this, ActMTeamHQ.class);
            }
        });

        createTeamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String teamname = createTeamName.getText().toString();
                if(!teamname.equals("")) {
                    Team t = Gate.createTeamOnServer(teamname);
                    t.addUser(Usersession.getInstance().getUser());
                    Usersession.switchActivity(ActMJoinTeam.this, ActMTeamHQ.class);
                }else {
                    Toast.makeText(ActMJoinTeam.this, "Your teamname is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        joinTeamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String id = joinTeamName.getText().toString();
                if(!id.equals("")) {
                    int i = -1;
                    try {
                        i = Integer.parseInt(id);

                        Team t = Gate.getTeamFromServer(i);
                        if (t == null){
                            Toast.makeText(ActMJoinTeam.this, "There is no team with the ID " + id, Toast.LENGTH_SHORT).show();
                        } else {
                            t.addUser(Usersession.getInstance().getUser());
                            Usersession.switchActivity(ActMJoinTeam.this, ActMTeamHQ.class);
                        }
                    } catch (NumberFormatException e){
                        Toast.makeText(ActMJoinTeam.this, "ID must be numeric", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActMJoinTeam.this, "Your ID is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(Integer.parseInt(android.os.Build.VERSION.SDK) > 5 && keyCode == KeyEvent.KEYCODE_BACK ||keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0){
            // Back key and middle Key block
            return true;
        }else{
            Toast.makeText(ActMJoinTeam.this, keyCode, Toast.LENGTH_SHORT).show();
            return super.onKeyDown(keyCode, event);
        }
    }
}
