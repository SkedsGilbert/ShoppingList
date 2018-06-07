package com.south42studio.jsin.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView textViewEmail;
    private EditText editTextEmail;
    private TextView textViewPassword;
    private EditText editTextPassword;
    private Button button_Login;
    private TextView textViewTestText;
    private FirebaseAuth mAuth;

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

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextEmail.getText().toString() +"  "+ editTextPassword.getText().toString();
                textViewTestText.setText(content);
            }
        });


    }
}
