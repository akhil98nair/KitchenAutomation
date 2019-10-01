package com.miniproject.kitchenautomation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LPGViewActivity extends navigation_activity {
    private static final String CHANNEL_ID = "1" ;
    TextView lpg_gas;
    private List<CarRecyclerViewItem> carItemList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.display);
        getLayoutInflater().inflate(R.layout.activity_card_view_lpg, content, true);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference(user.getUid()+"/lpg_gas");




        lpg_gas = (TextView) findViewById(R.id.lpg_value);

        initializeCarItemList();

        // Create the recyclerview.
        RecyclerView carRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        carRecyclerView.setLayoutManager(gridLayoutManager);

        // Create car recycler view data adapter with car item list.
        BuyCarRecyclerViewDataAdapter carDataAdapter = new BuyCarRecyclerViewDataAdapter(carItemList);
        // Set data adapter.
        carRecyclerView.setAdapter(carDataAdapter);

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Map<String, Object> map = (Map<String, Object>) ds.getValue();
                Log.d("tagee", ds.child("value").getValue(Integer.class)+"kite");
                createNotificationChannel();
                String gas = ds.child("value").getValue(Integer.class)+" PPM";
                lpg_gas.setText(gas);
                if(ds.child("value").getValue(Integer.class)>400){
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(LPGViewActivity.this);
                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    builder.setContentTitle("Alert!!!!!")
                            .setContentText("GAS IS LEAKING  " + gas)
                            .setSmallIcon(R.drawable.notification)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    /* Initialise car items in list. */
    private void initializeCarItemList()
    {
        if(carItemList == null)
        {
            carItemList = new ArrayList<CarRecyclerViewItem>();
            carItemList.add(new CarRecyclerViewItem("Gas leakage", R.drawable.lpg));


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
} }
