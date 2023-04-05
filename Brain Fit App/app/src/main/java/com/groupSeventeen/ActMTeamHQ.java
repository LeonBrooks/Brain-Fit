package com.groupSeventeen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.groupSeventeen.Util.HelpMe;


import java.util.List;

public class ActMTeamHQ extends AppCompatActivity {
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_team_hq);

        if (!getLocationPermission()) {
            // permisson not granted
            requestLocation();
        }

        team = Usersession.getInstance().loadTeam();
        final List<User> teamUsers = team.getUsers();

        final Button achievementButton = findViewById(R.id.button_amt_Achievement);
        final Button logoutButton = findViewById(R.id.button_amt_Logout);
        final Button statisticsButton = findViewById(R.id.button_amt_Statistics);
        final Button matchButton = findViewById(R.id.button_amt_Search);
        final TextView teamName = findViewById(R.id.text_amt_Title);
        final TextView teamId = findViewById(R.id.text_amt_Id);

        teamName.setText(team.getTeamname());
        teamId.setText("Team ID: " + Integer.toString(team.getId()));

        final TextView teamScore = findViewById(R.id.text_amt_Teamscore);
        String scoreText = "Teamscore: " + team.getTeamPoints();
        teamScore.setText(scoreText);

        // Container for list of profiles
        /*
            Creates the list of profiles and the action for OnClick
            OnCreate needs to be recalled when list changes
         */
        final LinearLayout profileContainer = findViewById(R.id.ll_amt_teamlist);

        for (int i = 0; i < teamUsers.size(); i++) {
            TextView name = new TextView(this);
            TextView points = new TextView(this);
            final User current = teamUsers.get(i);
            String userText = current.getUsername() + ": ";
            name.setText(userText);// placeholder text
            points.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            name.setTextSize(18);
            name.setWidth((int) HelpMe.convertDpToPixel(200, ActMTeamHQ.this));

            String userPoints = current.getPoints() + " points";
            points.setText(userPoints);
            points.setTextSize(18);
            points.setWidth((int) HelpMe.convertDpToPixel(100, ActMTeamHQ.this));

            final LinearLayout line = new LinearLayout(this);
            line.addView(name);
            line.addView(points);

            line.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    // On click
                    //Toast.makeText(ActMTeamHQ.this, "Profile was clicked", Toast.LENGTH_SHORT).show();
                    Usersession.switchActivity(ActMTeamHQ.this, ActMProfile.class, current);
                }
            });
            profileContainer.addView(line);
        }

        achievementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMTeamHQ.this, ActMAchievement.class);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                logout();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMTeamHQ.this, ActMStatistic.class);
            }
        });

        matchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (teamUsers.size() == 1) {
                    Toast.makeText(ActMTeamHQ.this, "You need to have multiple users in your team to play!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (getLocationPermission()) {
                    if (!Usersession.getInstance().isLocationHandlerSet())
                        Usersession.getInstance().setLocationHandler(new LocationHandler(ActMTeamHQ.this));
                    Usersession.switchActivity(ActMTeamHQ.this, ActMMatchSearch.class);
                }
                else
                    requestLocation();
            }
        });

        final TextView title = findViewById(R.id.text_amt_Title);
        title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMTeamHQ.this, ActMAchievement.class);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(Integer.parseInt(android.os.Build.VERSION.SDK) > 5 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            logout();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    public void logout(){
        AlertDialog alertDialog = new AlertDialog.Builder(ActMTeamHQ.this).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Doing this will log you out");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();

                Usersession.switchActivity(ActMTeamHQ.this, ActMLogin.class);
                dialogInterface.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void requestLocation() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
    }

    private boolean getLocationPermission() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

}