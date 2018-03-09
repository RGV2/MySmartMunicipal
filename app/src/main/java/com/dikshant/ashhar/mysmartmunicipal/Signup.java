package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Signup extends AppCompatActivity {

    EditText nameET,mailET,userET,contactET,aadharET,passET,passConfET;
    String name,mail,user,contact,aadhar,pass,passConf,qry,toastShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameET = (EditText) findViewById(R.id.name_ET);
        mailET = (EditText) findViewById(R.id.mail_ET);
        userET = (EditText) findViewById(R.id.user_ET);
        contactET = (EditText) findViewById(R.id.contact_ET);
        aadharET = (EditText) findViewById(R.id.aadhar_ET);
        passET = (EditText) findViewById(R.id.pass_ET);
        passConfET = (EditText) findViewById(R.id.paasConf_ET);

        final Button button = (Button) findViewById(R.id.submit_BTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Signup.SigningUp signingUp = new Signup.SigningUp();
                    signingUp.execute();
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }
            }
        });
    }

    class SigningUp extends AsyncTask<String, String, String> {

        Boolean ifSignup=false;
        @Override
        protected String doInBackground(String... strings) {
            Connection con = null;
            if (passET.getText().toString().equals(passConfET.getText().toString())) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/municipal_server", "root", "123456");
                    name = nameET.getText().toString();
                    mail = mailET.getText().toString();
                    user = userET.getText().toString();
                    contact = contactET.getText().toString();
                    aadhar = aadharET.getText().toString();
                    pass = passET.getText().toString();
                    passConf = passConfET.getText().toString();
                    if (name!=null && mail!=null && user!=null && contact!=null && aadhar!=null && pass!=null){
                        qry = "INSERT INTO user (userid,email,password,name,contact,aadhar) VALUES(?,?,?,?,?,?)";
                        PreparedStatement stmt = con.prepareStatement(qry);
                        stmt.setString(1, user);
                        stmt.setString(2, mail);
                        stmt.setString(3, pass);
                        stmt.setString(4, name);
                        stmt.setString(5, contact);
                        stmt.setString(6, aadhar);
                        if (stmt.executeUpdate() > 0)
                            ifSignup = true;
                    }else
                        toastShow = "Please enter all Fields";

                } catch (SQLException se) {
                    Log.e("ERRO", se.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e("ERRO", e.getMessage());
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }

            }
            else
                toastShow="Password doesn't match";
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (ifSignup) {
                Intent intentMain = new Intent(Signup.this,Login.class);
                startActivity(intentMain);
                Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getApplicationContext(), toastShow, Toast.LENGTH_SHORT).show();
        }
    }

}
