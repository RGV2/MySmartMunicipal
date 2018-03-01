package com.dikshant.ashhar.mysmartmunicipal;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Login extends AppCompatActivity {
    Boolean aBoolean = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextView textView = (TextView)findViewById(R.id.tV);
        final Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Con con = new Con();
                con.execute();
                if(aBoolean){
                    button.setText("Connected");
                    textView.setText("Welcome");
                }
            }
        });
    }
    class Con extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/municipal_server","root", "123456")!=null)
                aBoolean=true;

        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
            return null;
        }
    }
}

