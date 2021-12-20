package com.example.manga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    Button signout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_profile:

                        Intent intent = new Intent(Dashboard.this, Profile.class);
                        startActivity(intent);
                        break;
                    case  R.id.bookmark:{
                        Intent intent1 = new Intent(Dashboard.this, Bookmark.class);
                        startActivity(intent1);
                        break;
                    }
                    case  R.id.m:{
                        Intent intent1 = new Intent(Dashboard.this, Seinen.class);
                        startActivity(intent1);
                        break;
                    }
                    case  R.id.d:{
                        Intent intent1 = new Intent(Dashboard.this, Shojo.class);
                        startActivity(intent1);
                        break;
                    }
                    case  R.id.mh:{
                        Intent intent1 = new Intent(Dashboard.this, Shonen.class);
                        startActivity(intent1);
                        break;
                    }
                    case  R.id.mha:{
                        Intent intent1 = new Intent(Dashboard.this, Kodomomuke.class);
                        startActivity(intent1);
                        break;
                    }
                    case R.id.logout:
                    {
                        FirebaseAuth.getInstance().signOut();
                        Intent inte = new Intent(Dashboard.this,MainActivity.class);
                        startActivity(inte);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });
    }
    public void i1(View view) {
        Intent i = new Intent(Dashboard.this,Seinen.class);
        startActivity(i);
    }
    public void i2(View view) {
        Intent i = new Intent(Dashboard.this,Shojo.class);
        startActivity(i);
    }
    public void i3(View view) {
        Intent i = new Intent(Dashboard.this,Shonen.class);
        startActivity(i);
    }
    public void i4(View view) {
        Intent i = new Intent(Dashboard.this,Kodomomuke.class);
        startActivity(i);
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}