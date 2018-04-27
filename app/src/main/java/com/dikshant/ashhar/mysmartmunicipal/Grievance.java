package com.dikshant.ashhar.mysmartmunicipal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

public class Grievance extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    double longitude,latitude;
    EditText editText;
    TextView textView;
    Location location;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance);
        editText = (EditText) findViewById(R.id.et_grievance);
        textView = (TextView) findViewById(R.id.tv_cordinates);
        spinner = (Spinner) findViewById(R.id.spinner_dept);

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


        final Button locate = (Button) findViewById(R.id.btn_locate);
        ActivityCompat.requestPermissions(Grievance.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        locate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                GPS gps = new GPS(getApplicationContext());
                location = gps.getLocation();
                if( location == null){
                    textView.setText("GPS unable to get Value");
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    textView.setText("Latitude: "+latitude+"\n"+"Longitude: "+longitude);
                }
            }
        });

        Button submit = (Button) findViewById(R.id.btn_Gsubmit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (location==null)
                    textView.setError("Location not Selected");
                if (spinner.getSelectedItem().toString().equals("Select Department"))
                    ((TextView) spinner.getSelectedView()).setError("Department not selected");
                if (editText.length()==0)
                    editText.setError("Enter Description");
                if (editText.length()>2000)
                    editText.setError("Description too long");
                if (location!=null && !(spinner.getSelectedItem().toString().equals("Select Department")) && (editText.length()!=0) && (editText.length()<=2000)){
                    GrievanceData grievanceData = new GrievanceData();
                    grievanceData.execute();
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
            Intent intentMain = new Intent(Grievance.this, Starter.class);
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
            Intent intentMain = new Intent(Grievance.this, Home.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_about) {
            Toast.makeText(getApplicationContext(), "About USSSSSSSSS", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Grievance.this, AboutUs.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_grievance) {
            Toast.makeText(getApplicationContext(), "Grievance", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Grievance.this, Grievance.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_helpline) {
            Toast.makeText(getApplicationContext(), "Helpline", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Grievance.this, Helpline.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_extra) {
            Toast.makeText(getApplicationContext(), "Extras", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Grievance.this, Extras.class);
            startActivity(intentMain);
        } else if (id == R.id.nav_contact) {
            Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(Grievance.this, Contact.class);
            startActivity(intentMain);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class GrievanceData extends AsyncTask<String, String, String> {
        ResultSet rSet;
        Connection con = null;
        String qry,toastShow,pref;
        Boolean connect=false, inserted = false;
        int count=0;

        @Override
        protected String doInBackground(String... strings) {
            try {
                SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
                pref=sharedPreferences.getString("key","");
                con = Starter.connection();
                if (con!=null) {
                    connect=true;
                    qry = "INSERT INTO grievances (latitude,longitude,department,grievance,user_name) VALUES(?,?,?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(qry);
                    stmt.setString(1, String.valueOf(latitude));
                    stmt.setString(2, String.valueOf(longitude));
                    stmt.setString(3, spinner.getSelectedItem().toString());
                    stmt.setString(4, editText.getText().toString());
                    stmt.setString(5, pref);
                    if (stmt.executeUpdate() > 0) {
                        inserted = true;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("totalGrievances");
                        editor.apply();
                        rSet = stmt.executeQuery("select * from grievances where user_name = '" + pref + "'");
                        while (rSet.next())
                           count++;
                        editor.putString("totalGrievances", String.valueOf(count));
                        editor.apply();
                    } else
                        toastShow = "Unable to Submit";
                stmt.close();
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
            try {
                if (inserted){
                    Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Grievance.this,Home.class);
                    startActivity(intent);
                }
                if (!inserted)
                    Toast.makeText(getApplicationContext(), toastShow, Toast.LENGTH_SHORT).show();
                if (!connect)
                    Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT).show();
                con.close();
                rSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
