package com.miniproject.kitchenautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class add_groccery extends AppCompatActivity {

    EditText groccery, quantity;
    TextView submit;
    DatabaseReference rootRef,demoRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_groccery);
        groccery = (EditText)findViewById(R.id.groccery_name);
        quantity = (EditText)findViewById(R.id.quantity);
        submit = (TextView)findViewById(R.id.submit);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        demoRef = rootRef.child(user.getUid());

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
                    Toast.makeText(add_groccery.this,"Connect to Internet",Toast.LENGTH_SHORT).show();
                }
                String groccery_name = groccery.getText().toString();
                Double quantity_val = Double.parseDouble(quantity.getText().toString()) ;


//push creates a unique id in database
                demoRef.child("groceries").child(groccery_name).setValue(quantity_val);


                Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Task<Uri> downloadUrl = mStorageRef.getDownloadUrl();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });



            }
        });
    }
}
