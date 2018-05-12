package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view=navigationView.getHeaderView(0);
        TextView textView_name = (TextView)view.findViewById(R.id.tv_name);
        TextView textView_mail = (TextView)view.findViewById(R.id.tv_mail);
        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String mail=sharedPreferences.getString("key1","");
        String name=sharedPreferences.getString("key2","");
        textView_name.setText(name);
        textView_mail.setText(mail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Contact.this,Home.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("key");
            editor.apply();
            Intent intentMain = new Intent(Contact.this, Starter.class);
            startActivity(intentMain);

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
            Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, Home.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_about) {
            Toast.makeText(getApplicationContext(), "About USSSSSSSSS", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, AboutUs.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_grievance) {
            Toast.makeText(getApplicationContext(), "Grievance", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, Grievance.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_helpline) {
            Toast.makeText(getApplicationContext(), "Helpline", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, Helpline.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_extra) {
            Toast.makeText(getApplicationContext(), "Extras", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, Extras.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_contact) {
            Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Contact.this, Contact.class);
            startActivity(intentMain);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
