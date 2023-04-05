package com.groupSeventeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthCredential;
import com.groupSeventeen.Util.Gate;
import com.groupSeventeen.Util.HttpGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ActMLogin extends AppCompatActivity {

    private static final String TAG = "ActMLogin";

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseAuth.getInstance().signOut();
        //QuestionCreator.createQuestions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_login);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser fbUser = mAuth.getCurrentUser();

        /*QuestionCreator.createQuestions();

        Team t1 = Gate.createTeamOnServer("TestDummiesClub");
        User u1 = Gate.createUserOnServer("Dummie1", "dummie1@test.de");
        u1.addPoints((int) (Math.random() * 500));
        Gate.sendUserToServer(u1);
        User u2 = Gate.createUserOnServer("Dummie2", "dummie2@test.de");
        u2.addPoints((int) (Math.random() * 500));
        Gate.sendUserToServer(u2);
        User u3 = Gate.createUserOnServer("Dummie3", "dummie3@test.de");
        u3.addPoints((int) (Math.random() * 500));
        Gate.sendUserToServer(u3);
        User u4 = Gate.createUserOnServer("Dummie4", "dummie4@test.de");
        u4.addPoints((int) (Math.random() * 500));
        Gate.sendUserToServer(u4);
        User u5 = Gate.createUserOnServer("Dummie5", "dummie5@test.de");
        u5.addPoints((int) (Math.random() * 500));
        Gate.sendUserToServer(u5);

        t1.addUser(u1);
        t1.addUser(u2);
        t1.addUser(u3);
        t1.addUser(u4);
        t1.addUser(u5);


        Team t2 = Gate.createTeamOnServer("ProQuizzers");
        User uu1 = Gate.createUserOnServer("Pro1", "Pro1@test.de");
        uu1.addPoints((int) (Math.random() * 1000));
        Gate.sendUserToServer(uu1);
        User uu2 = Gate.createUserOnServer("Pro2", "Pro2@test.de");
        uu2.addPoints((int) (Math.random() * 1000));
        Gate.sendUserToServer(uu2);
        User uu3 = Gate.createUserOnServer("Pro3", "Pro3@test.de");
        uu3.addPoints((int) (Math.random() * 1000));
        Gate.sendUserToServer(uu3);
        User uu4 = Gate.createUserOnServer("Pro4", "Pro4@test.de");
        uu4.addPoints((int) (Math.random() * 1000));
        Gate.sendUserToServer(uu4);
        User uu5 = Gate.createUserOnServer("Pro5", "Pro5@test.de");
        uu5.addPoints((int) (Math.random() * 1000));
        Gate.sendUserToServer(uu5);

        t2.addUser(uu1);
        t2.addUser(uu2);
        t2.addUser(uu3);
        t2.addUser(uu4);
        t2.addUser(uu5);*/



        if(fbUser == null) {
            final EditText emailText = findViewById(R.id.textInput_aml_Username);
            final EditText passwordText = findViewById(R.id.textInput_aml_Password);
            final Button loginButton = findViewById(R.id.button_aml_Login);
            final TextView registerLink = findViewById(R.id.text_aml_Register);

            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    signIn(emailText.getText().toString(), passwordText.getText().toString());
                }
            });

            registerLink.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    Usersession.switchActivity(ActMLogin.this, ActMRegister.class);
                }
            });
        }else{
            // log out, if any user is logged in
            loggedIn();
        }
    }

    public void loggedIn(){
        Usersession session = Usersession.getInstance();
        session.setUser(mAuth.getCurrentUser().getEmail());
        session.setGate(new Gate());

        Usersession.switchActivity(ActMLogin.this, ActMTeamHQ.class);
    }

    private void signIn(final String email, String password){
        if (email.equals("")) {
            Toast.makeText(ActMLogin.this, "Your email is missing", Toast.LENGTH_SHORT).show();
        }else if(password.equals("")) {
            Toast.makeText(ActMLogin.this, "Your password is missing", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "loggedInUser: success");
                        Usersession session = Usersession.getInstance();
                        session.setUser(email);
                        session.setGate(new Gate());

                        Usersession.switchActivity(ActMLogin.this, ActMTeamHQ.class);
                    } else {
                        Log.d(TAG, "loggedInUser: no success");
                        Toast.makeText(ActMLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}