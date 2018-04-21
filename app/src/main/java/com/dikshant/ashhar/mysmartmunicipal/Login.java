package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    EditText uid, pwd;
    String userId, pass, qry, qry1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uid = (EditText) findViewById(R.id.uid);
        pwd = (EditText) findViewById(R.id.pwd);
        final Button login = (Button) findViewById(R.id.login_BTN);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EstablishCon establishCon = new EstablishCon();
                    establishCon.execute();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });

        final TextView signup = (TextView) findViewById(R.id.signup_tV);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentMain = new Intent(Login.this, Signup.class);
                    startActivity(intentMain);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });
    }

    class EstablishCon extends AsyncTask<String, String, String> {

        Boolean empty = false, ifLogin = false;

        @Override
        protected String doInBackground(String... strings) {
            Connection con = null;

            try {
                con = Starter.connection();
                userId = uid.getText().toString();
                pass = pwd.getText().toString();
                if (userId.equals("") || pass.equals(""))
                    empty = true;
                else {
                    qry = "SELECT * FROM user WHERE userid = '" + userId + "' and password = '" + pass + "'";
                    qry1 = "SELECT email FROM user where userid='" + userId + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rSet = stmt.executeQuery(qry);

                    if (rSet.next()) {
                        ifLogin = true;
                        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
                        Editor editor = sharedPreferences.edit();
                        editor.putString("key", userId);
                        ResultSet rSet1 =stmt.executeQuery(qry1);
                        editor.putString("key1", rSet1.toString());
                        editor.commit();
                    }
                }
                con.close();

            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (ifLogin) {
                Intent intentMain = new Intent(Login.this, Home.class);
                intentMain.putExtra("userId", userId);
                startActivity(intentMain);
            }if (!ifLogin)
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            if (empty)
                Toast.makeText(getApplicationContext(), "Please enter User ID and Password", Toast.LENGTH_SHORT).show();
            }
    }
}
