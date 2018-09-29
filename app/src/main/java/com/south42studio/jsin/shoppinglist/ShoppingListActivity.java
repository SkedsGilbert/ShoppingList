package com.south42studio.jsin.shoppinglist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShoppingListActivity extends AppCompatActivity {

    public static final String TAG = "ShoppingListActivity";
    private FirebaseAuth mAuth;

    private TextView usernameTv;

    private Button addItemBttn;
    private EditText itemNameEt;
    private EditText itemDescriptionEt;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private void assignViews() {
        usernameTv = findViewById(R.id.username_tv);
        addItemBttn = findViewById(R.id.add_item_bttn);
        itemNameEt = findViewById(R.id.item_name_et);
        usernameTv = findViewById(R.id.username_tv);
        mRecyclerView = findViewById(R.id.recycler_list);
        itemDescriptionEt = findViewById(R.id.item_description_et);
    }

    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("shoplist/items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        assignViews();
        setOnclickListener();
       /* mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);*/

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
                Log.d("TAG","inside the click listener");
                String itemName = itemNameEt.getText().toString();
                String itemDesc = itemDescriptionEt.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // temp item creation delete once connected to the add button
                Map<String, Object> itemToSave = new HashMap<>();
                itemToSave.put("name", itemName);
                itemToSave.put("description", itemDesc);


                db.collection("items" )
                        .add(itemToSave)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d (TAG, "Item added with ID " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error adding item");
                            }
                        });

                itemNameEt.setText("");
                itemDescriptionEt.setText("");
            }
        });
    }
}
