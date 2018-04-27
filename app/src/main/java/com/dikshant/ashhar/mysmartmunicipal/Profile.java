package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Profile extends AppCompatActivity {

    TextView name,email,contact,aadhar,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(TextView) findViewById(R.id.tv_profileName);
        email=(TextView) findViewById(R.id.tv_profileEmail);
        contact=(TextView) findViewById(R.id.tv_profileContact);
        aadhar=(TextView) findViewById(R.id.tv_profileAadhar);
        time=(TextView) findViewById(R.id.tv_profileTime);

        Fetch fetch = new Fetch();
        fetch.execute();
    }
    class Fetch extends AsyncTask<String, String, String> {

        Connection con = null;
        String username;
        Statement stmt;
        ResultSet rSet=null;

        @Override
        protected void onPreExecute() {
            SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("key", "");
        }

        @Override
        protected String doInBackground(String... strings) {
            con = Starter.connection();
            if (con != null) {
                try {
                    stmt = con.createStatement();
                    rSet = stmt.executeQuery("select * from user where userid = '" + username + "'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                while (rSet.next()) {
                    rSet.absolute(1);
                    name.setText("Name:"+rSet.getString("name"));
                    email.setText("E-mail: "+rSet.getString("email"));
                    contact.setText("Contact No.: "+rSet.getString("contact"));
                    aadhar.setText("AADHAR: "+rSet.getString("aadhar"));
                    time.setText("User since: "+rSet.getString("create_time"));
                    con.close();
                    stmt.close();
                    rSet.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
