package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Starter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        Checker checker=new Checker();
        checker.execute();
    }
    public class Checker extends AsyncTask<String, String, String>{
        Boolean check = false;
        String pref;

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            pref=sharedPreferences.getString("key","");
            String qry = "SELECT * FROM user where userid = '"+pref+"'";
            Connection con = connection();
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                ResultSet rSet = stmt.executeQuery(qry);
                if (rSet.next())
                    check = true;
                else
                    check = false;
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            if (check){
                Toast.makeText(getApplicationContext(), "Welcome Back "+pref, Toast.LENGTH_SHORT).show();
                Intent intentMain = new Intent(Starter.this, HomePage.class);
                startActivity(intentMain);
            } if (!check){
                Toast.makeText(getApplicationContext(), "Please Login to Continue", Toast.LENGTH_SHORT).show();

                Intent intentMain = new Intent(Starter.this, Login.class);
                startActivity(intentMain);
            }
        }
    }

    static Connection connection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/municipal_server", "root", "123456");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}