package com.south42studio.jsin.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView usernameTv;

    private Button addItemBttn;
    private EditText itemNameEt;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private void assignViews() {
        usernameTv = findViewById(R.id.username_tv);
        addItemBttn = findViewById(R.id.add_item_bttn);
        itemNameEt = findViewById(R.id.item_name_et);
        usernameTv = findViewById(R.id.username_tv);
        mRecyclerView = findViewById(R.id.recycler_list);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assignViews();
        setOnclickListener();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Need to Add a list adapter!


        usernameTv.setText(currentUser.getEmail());
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //TODO: check if user logged in then go to ListActivity
    }

    private void setOnclickListener(){
        addItemBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameTv.setText(itemNameEt.getText().toString());
            }
        });
    }
}
