package com.miniproject.kitchenautomation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ProfileActivity extends navigation_activity {
    ImageView user_image;
    EditText name, mobile, email, address;
    TextView submit, edittext;
    DatabaseReference rootRef,demoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.display);
        getLayoutInflater().inflate(R.layout.activity_profile, content, true);
        final Calendar myCalendar = Calendar.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
      edittext= (TextView) findViewById(R.id.Birthday);
       name = (EditText)findViewById(R.id.name);
         mobile = (EditText)findViewById(R.id.mobile_no);
         email = (EditText)findViewById(R.id.email);
         address = (EditText)findViewById(R.id.address);
         submit = (TextView)findViewById(R.id.submit);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        demoRef = rootRef.child(user.getUid());
        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference(user.getUid());
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    name.setText(ds.child("name").getValue(String.class));
//                    Log.d("myTag", ds.child("name").getValue(String.class) );
                    mobile.setText(ds.child("mobile").getValue(String.class));
                    email.setText(ds.child("email").getValue(String.class));
                    address.setText(ds.child("address").getValue(String.class));
                    edittext.setText(ds.child("DOB").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                String user_name = name.getText().toString();
                String mobile_no = mobile.getText().toString();
                String email_id = email.getText().toString();
                String full_address = address.getText().toString();
                String dob = edittext.getText().toString();


//push creates a unique id in database
 demoRef.child("Profile").child("name").setValue(user_name);
                demoRef.child("Profile").child("mobile").setValue(mobile_no);
                demoRef.child("Profile").child("email").setValue(email_id);
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


