package com.miniproject.kitchenautomation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class navigation_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_activity);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView1.getHeaderView(0);
        ImageView image = (ImageView)hView.findViewById(R.id.imageView);
        TextView textName = (TextView)hView.findViewById(R.id.nameTextView);
        TextView emailId = (TextView)hView.findViewById(R.id.emailidTextView);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
        Glide.with(this)
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(image);

//
            Log.d("myTag", user.getDisplayName().toString());
        textName.setText(user.getDisplayName().toString());
        emailId.setText(user.getEmail().toString());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), CardViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();
        } else if (id == R.id.nav_groccery) {
            Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();
    } else if (id == R.id.nav_LPG) {
            Intent intent = new Intent(getApplicationContext(), LPGViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();
        }
        else if(id == R.id.signout){
//            GoogleSignInOptions gso = new GoogleSignInOptions.
//                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                    build();
//            GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
//            googleSignInClient.signOut();
//
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();

        }
        else if(id == R.id.seemap){
//            double latitude = 40.714728;
//            double longitude = -73.998672;
//            String label = "I'm Here!";
//            String uriBegin = "geo:" + latitude + "," + longitude;
//            String query = latitude + "," + longitude + "(" + label + ")";
//            String encodedQuery = Uri.encode(query);
//            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
//            Uri uri = Uri.parse(uriString);
//            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//            startActivity(mapIntent);
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
