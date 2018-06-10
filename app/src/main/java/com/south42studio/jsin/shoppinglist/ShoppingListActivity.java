package com.south42studio.jsin.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView usernameTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        usernameTv = findViewById(R.id.username_tv);

        usernameTv.setText(currentUser.getEmail());
    }
}
