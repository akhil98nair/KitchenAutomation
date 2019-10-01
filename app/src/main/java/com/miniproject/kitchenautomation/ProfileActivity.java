package com.miniproject.kitchenautomation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class ProfileActivity extends navigation_activity {
    ImageView user_image;
    EditText mobile, address, emergency_no1, emergency_no2, lpg_service_no;
    TextView name, submit, email, edittext;
    DatabaseReference rootRef,demoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(FirebaseApp.getApps(this).size() == 0){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
        ViewGroup content = (ViewGroup) findViewById(R.id.display);
        getLayoutInflater().inflate(R.layout.activity_profile, content, true);
        final Calendar myCalendar = Calendar.getInstance();

        user_image = (ImageView)findViewById(R.id.user_image);
      edittext= (TextView) findViewById(R.id.Birthday);
      lpg_service_no = (EditText)findViewById(R.id.lpg_no);
       name = (TextView) findViewById(R.id.user_name);
         mobile = (EditText)findViewById(R.id.mobile_no);
         email = (TextView) findViewById(R.id.email_id);
         emergency_no1 = (EditText)findViewById(R.id.emergency_no);
         emergency_no2 = (EditText)findViewById(R.id.emergency_no1);
         address = (EditText)findViewById(R.id.address);
         submit = (TextView)findViewById(R.id.submit);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        demoRef = rootRef.child(user.getUid());

        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Profile");
        table_user.keepSynced(true);
        if(user != null){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .apply(new RequestOptions().override(600, 200))
                    .into(user_image);

//
            Log.d("myTag", user.getDisplayName().toString());
            name.setText(user.getDisplayName().toString());
            email.setText(user.getEmail().toString());
        }
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Map<String, Object> map = (Map<String, Object>) ds.getValue();
                Log.d("tagee", "Value is: " + map);
                String emergency_1 = ds.child("emergency_no1").getValue(String.class);
                emergency_no1.setText(emergency_1);
                emergency_no2.setText(ds.child("emergency_no2").getValue(String.class));
                Log.d("myTag1", emergency_1+"hello" );
                mobile.setText(ds.child("mobile").getValue(String.class));
                lpg_service_no.setText(ds.child("lpg_service_no").getValue(String.class));
                address.setText(ds.child("address").getValue(String.class));
                edittext.setText(ds.child("DOB").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()){
                    Toast.makeText(ProfileActivity.this,"Connect to Internet",Toast.LENGTH_SHORT).show();
                }
                String emergency1 = emergency_no1.getText().toString();
                String emergency2 = emergency_no2.getText().toString();
                String mobile_no = mobile.getText().toString();
                String lpg_service = lpg_service_no.getText().toString();
                String full_address = address.getText().toString();
                String dob = edittext.getText().toString();


//push creates a unique id in database
 demoRef.child("Profile").child("emergency_no1").setValue(emergency1);
                demoRef.child("Profile").child("emergency_no2").setValue(emergency2);
                demoRef.child("Profile").child("mobile").setValue(mobile_no);
                demoRef.child("Profile").child("lpg_service_no").setValue(lpg_service);
                demoRef.child("Profile").child("address").setValue(full_address);
                demoRef.child("Profile").child("DOB").setValue(dob);


            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
}


