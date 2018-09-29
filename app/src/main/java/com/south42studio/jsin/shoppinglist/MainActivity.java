package com.south42studio.jsin.shoppinglist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.view.Gravity.CENTER;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button button_Login;
    private TextView textViewTestText;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private boolean isRegister = false;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email_et);
        editTextPassword = findViewById(R.id.password_et);
        button_Login = findViewById(R.id.login_bttn);
        textViewTestText = findViewById(R.id.testText1_tv);

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
                determineLoginStatus();
            }
        });
    }

    private void determineLoginStatus(){
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (!isRegister){
            Log.d(TAG, "determineLoginStatus:" + button_Login.getText().toString());
            try {

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
                                    proceedToShoppingList();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    String exception = ((FirebaseAuthException) task.getException()).getErrorCode();

                                    if (exception.equals("ERROR_USER_NOT_FOUND")) {
                                        showToast(getString(R.string.user_not_found));
                                        setCreateBttn(button_Login);

                                    } else {
                                        showToast((getString(R.string.invalid_login)));
                                    }

                                }


                            }
                        });
            } catch (Exception e){
                showToast(getString(R.string.blank_login_err_message));
            }
        }else{
                try {

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
                                    dbHandler.AddUsertoDB(user.getUid().toString(), user.getEmail());
//                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                                    // temp item creation delete once connected to the add button
//                                    Map<String, Object> userInfo = new HashMap<>();olo
//                                    userInfo.put("user_id", user.getUid());
//                                    userInfo.put("group_id", content);
//
//
//                                    db.collection("user")
//                                            .add(userInfo)
//                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                                @Override
//                                                public void onSuccess(DocumentReference documentReference) {
//                                                    Log.d (TAG, "Item added with ID " + documentReference.getId());
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d(TAG, "Error adding item");
//                                                }
//                                            });

                                    proceedToShoppingList();
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    showToast(task.getException().getLocalizedMessage());
                                    isRegister = false;
                                    button_Login.setText(R.string.login_button);

                                }

                            }
                        });
                } catch (Exception e){
                    showToast(getString(R.string.blank_login_err_message));
                }

        }
    }

    public void setCreateBttn(View view) {
        button_Login.setText(R.string.register_user);
        isRegister = true;
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(MainActivity.this, message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void proceedToShoppingList(){
        Intent intent = new Intent(this,ShoppingListActivity.class);
        startActivity(intent);
    }
}

//TODO: Add all my strings to the string.xml then handle errors like wrong passwords
