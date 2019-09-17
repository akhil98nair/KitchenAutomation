package com.miniproject.kitchenautomation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CardViewActivity extends navigation_activity {
    private static final String TAG = "hello";
    DatabaseReference demoRef, rootRef;


    private List<CarRecyclerViewItem> carItemList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ViewGroup content = (ViewGroup) findViewById(R.id.display);
        getLayoutInflater().inflate(R.layout.activity_card_view, content, true);

//        initializeCarItemList();

        // Create the recyclerview.

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        demoRef = rootRef.child(user.getUid());
        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference(user.getUid()+"/groceries");
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carItemList = new ArrayList<CarRecyclerViewItem>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    RecyclerView carRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
                    // Create the grid layout manager with 2 columns.
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(CardViewActivity.this, 1);
                    // Set layout manager.
                    carRecyclerView.setLayoutManager(gridLayoutManager);
                    String user = ds.getValue(String.class);
                    Log.d(TAG, user);

                    carItemList.add(new CarRecyclerViewItem(user, R.drawable.jar));
                    CarRecyclerViewDataAdapter carDataAdapter = new CarRecyclerViewDataAdapter(carItemList);
                    // Set data adapter.
                    carRecyclerView.setAdapter(carDataAdapter);
//                        emergency_no1.setText(ds.child("emergency_no1").getValue(String.class));
//                        emergency_no2.setText(ds.child("emergency_no2").getValue(String.class));
////                    Log.d("myTag", ds.child("name").getValue(String.class) );
//                        mobile.setText(ds.child("mobile").getValue(String.class));
//                        lpg_service_no.setText(ds.child("lpg_service_no").getValue(String.class));
//                        address.setText(ds.child("address").getValue(String.class));
//                        edittext.setText(ds.child("DOB").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Create car recycler view data adapter with car item list.


    }

    /* Initialise car items in list. */

    private void initializeCarItemList()
    {
        if(carItemList == null)
        {

//            carItemList = new ArrayList<CarRecyclerViewItem>();
//            carItemList.add(new CarRecyclerViewItem("Onion", R.drawable.jar));
//            carItemList.add(new CarRecyclerViewItem("Tomato", R.drawable.jar));
//            carItemList.add(new CarRecyclerViewItem("Potato", R.drawable.jar));
//            carItemList.add(new CarRecyclerViewItem("Apple", R.drawable.jar));

        }
    }
}
