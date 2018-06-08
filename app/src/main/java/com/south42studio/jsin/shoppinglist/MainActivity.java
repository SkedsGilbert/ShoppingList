package com.south42studio.jsin.shoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private TextView textViewEmail;
    private EditText editTextEmail;
    private TextView textViewPassword;
    private EditText editTextPassword;
    private Button button_Login;
    private TextView textViewTestText;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        textViewEmail = (TextView) findViewById(R.id.textView_email);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        textViewPassword = (TextView) findViewById(R.id.textView_password);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        button_Login = (Button) findViewById(R.id.button_Login);
        textViewTestText = (TextView) findViewById(R.id.textView_testText);

         setOnclick();

    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //TODO: check if user logged in then go to ListActivity
    }

    private void setOnclick(){
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String content = editTextEmail.getText().toString() +"  "+ editTextPassword.getText().toString();
                //textViewTestText.setText(content);
                determineLoginStatus();
            }
        });
    }

    private void determineLoginStatus(){
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String content = user.getUid();
                            textViewTestText.setText(content);
                            //updateUI(user);
                            //TODO: send user to their list
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(MainActivity.this, "Authentication failed.",
                            //       Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            //TODO: send user to their list
                            createUser(email,password);


                        }

                        // ...
                    }
                });
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String content = user.getEmail();
                            textViewTestText.setText(content);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


}

//TODO: Add all my strings to the string.xml then handle errors like wrong passwords
