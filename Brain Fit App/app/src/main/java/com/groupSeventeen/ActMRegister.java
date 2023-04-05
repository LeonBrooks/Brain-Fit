package com.groupSeventeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.groupSeventeen.Util.Gate;

public class ActMRegister extends AppCompatActivity {

    private static final String TAG = "ActMRegister";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_register);

        mAuth = FirebaseAuth.getInstance();

        final EditText username = (EditText) findViewById((R.id.textInput_amr_Username));
        final EditText emailText = (EditText) findViewById(R.id.textInput_amr_Email);
        final EditText passwordText = (EditText) findViewById(R.id.textInput_amr_Password);
        final EditText passwordRepeatText = (EditText) findViewById(R.id.textInput_amr_PasswordRepeat);
        final ImageView backButton = findViewById(R.id.image_amr_Back);
        final Button registerButton = findViewById(R.id.button_amr_Register);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Usersession.switchActivity(ActMRegister.this, ActMLogin.class);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                register(username.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(), passwordRepeatText.getText().toString());
            }
        });
    }

    private void register(final String username, final String email, String password, String passwordRepeat){
        if(username.equals("")) {
            Toast.makeText(ActMRegister.this, "Your username is missing", Toast.LENGTH_SHORT).show();
        }else if (email.equals("")) {
            Toast.makeText(ActMRegister.this, "Your email is missing", Toast.LENGTH_SHORT).show();
        }else if (password.equals("")) {
            Toast.makeText(ActMRegister.this, "Your password is missing", Toast.LENGTH_SHORT).show();
        }else if (password.length() < 6) {
            Toast.makeText(ActMRegister.this, "Your password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(passwordRepeat)){
            Toast.makeText(ActMRegister.this, "Your password and the repetition are not identical", Toast.LENGTH_SHORT).show();
        }else if (!(email.contains("@") && email.contains("."))){
            Toast.makeText(ActMRegister.this, "Your email is not an valid email", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createdUser: success");
                        Usersession session = Usersession.getInstance();
                        Gate.createUserOnServer(username, email.toLowerCase());
                        session.setUser(email);
                        session.setGate(new Gate());

                        Usersession.switchActivity(ActMRegister.this, ActMJoinTeam.class);
                    } else {
                        Log.d(TAG, "createdUser: no success");
                        Toast.makeText(ActMRegister.this, "This account already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}