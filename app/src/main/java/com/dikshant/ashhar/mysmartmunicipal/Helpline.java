package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Helpline extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String dept;
    Spinner s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
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

        final Button button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    s1 = (Spinner) findViewById(R.id.spin_department);

                    dept = s1.getSelectedItem().toString();
                    Helpline.Fetcher fetcher= new Helpline.Fetcher();
                    fetcher.execute();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            Intent intentMain = new Intent(Helpline.this, Starter.class);
            startActivity(intentMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Helpline.this, Home.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_about) {
            Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Helpline.this, AboutUs.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_grievance) {
            Toast.makeText(getApplicationContext(), "Grievance", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Helpline.this, Grievance.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_helpline) {
            Toast.makeText(getApplicationContext(), "Helpline", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Helpline.this, Helpline.class);
            startActivity(intentMain);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Fetcher extends AsyncTask<String, String, String> {
        ResultSet rSet;
        Connection con = null;
        String qry;
        Boolean found=false, empty=false, connect=false;
        TextView textView=(TextView) findViewById(R.id.tv_helpline);



        @Override
        protected String doInBackground(String... strings) {


            try {

                con = Starter.connection();
                if (con!=null) {
                    connect=true;
                    if (dept.equals("Select Department"))
                        empty=true;
                    else {
                        qry = "SELECT * FROM helpline WHERE department = '" + dept + "'";
                        Statement stmt = con.createStatement();
                        rSet = stmt.executeQuery(qry);

                        if (rSet.next()) {
                            found=true;
                        }
                    }
//                    con.close();
                }
            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String data="",full="";
            try {
            if (empty)
                Toast.makeText(getApplicationContext(), "Please Select Department", Toast.LENGTH_SHORT).show();
            if (found){
                ResultSetMetaData rsmd = rSet.getMetaData();
                while (rSet.next()){
                    for (int i = 1 ; i<=rsmd.getColumnCount(); i++)
                        data = rSet.getString(2);
                    full=full + data+"\n\n";
                }
                textView.setText(full);
            }

            if (!connect)
                Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT).show();

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
