package com.miniproject.kitchenautomation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
    private static final String CHANNEL_ID = "2";
    DatabaseReference demoRef, rootRef;


    private List<GroceryRecyclerViewItem> carItemList = null;

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), add_groccery.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0);
//


            }
        });

        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference(user.getUid()+"/groceries");
        table_user.addValueEventListener(new ValueEventListener() {
            private int Unique_Integer_Number = 1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carItemList = new ArrayList<GroceryRecyclerViewItem>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    RecyclerView carRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list1);
                    // Create the grid layout manager with 2 columns.
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(CardViewActivity.this, 1);
                    // Set layout manager.
                    carRecyclerView.setLayoutManager(gridLayoutManager);
                    createNotificationChannel();
                    String user = ds.getKey();
                    Double kg_val =ds.getValue(double.class);
                    if(kg_val < 0.5){
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(CardViewActivity.this);
                        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                        builder.setContentTitle("Alert!!!!!")
                                .setContentText(user + " is about to finish order ASAP")
                                .setSmallIcon(R.drawable.notification)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

// notificationId is a unique int for each notification that you must define
                        notificationManager.notify(Unique_Integer_Number, builder.build());
                    }
                    Unique_Integer_Number++;

                    String kg = kg_val.toString();
                    Log.d(TAG, kg);
                    carItemList.add(new GroceryRecyclerViewItem(user, kg, R.drawable.jar));
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
