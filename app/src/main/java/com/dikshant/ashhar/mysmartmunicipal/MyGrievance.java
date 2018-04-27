package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MyGrievance extends AppCompatActivity {
    TextView textViewGID,textViewDESC,textViewDEPT,textViewLOC,textViewSTATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grievance);
        textViewGID = (TextView) findViewById(R.id.tv_gid);
        textViewDESC = (TextView) findViewById(R.id.tv_Gview);
        textViewDEPT = (TextView) findViewById(R.id.tv_Gdept);
        textViewLOC = (TextView) findViewById(R.id.tv_Glocation);
        textViewSTATUS = (TextView) findViewById(R.id.tv_Gtime);

        Fetch fetch = new Fetch();
        fetch.execute();

    }
    class Fetch extends AsyncTask<String, String, String> {

        Connection con = null;
        String username;
        Statement stmt;
        ResultSet rSet=null;
        int gid = getIntent().getIntExtra("gid",0);

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
                    rSet = stmt.executeQuery("select * from grievances where user_name = '" + username + "' and gid = '" + gid + "'");
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
                    textViewGID.setText("Grievance ID: G"+rSet.getString("gid"));
                    textViewDESC.setText(rSet.getString("grievance"));
                    textViewDEPT.setText("Deaprtment: "+rSet.getString("department"));
                    textViewLOC.setText("Location:\nLatitude:"+rSet.getString("latitude")+"\nLongitude:"+rSet.getString("longitude"));
                    textViewSTATUS.setText("Date & Time: "+rSet.getString("time_stamp"));
                    }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

