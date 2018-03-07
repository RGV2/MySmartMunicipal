package com.dikshant.ashhar.mysmartmunicipal;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends AppCompatActivity {

    EstablishCon estCon = new EstablishCon();
    Connection connection;
    EditText uid,pwd;
    String user_id,pass,qry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uid = (EditText) findViewById(R.id.uid);
        pwd = (EditText) findViewById(R.id.pwd);

        final Button button = (Button) findViewById(R.id.login_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    user_id = uid.getText().toString();
                    pass = pwd.getText().toString();

                    qry = "SELECT * FROM municipal_server WHERE userid = " + user_id + " and password = " + pass;

                    connection=estCon.doInBackground();
                    Statement stmt = connection.createStatement();
                    ResultSet rSet = stmt.executeQuery(qry);
                    if(rSet.next())
                        Toast.makeText(getApplicationContext(),"Hello "+user_id,Toast.LENGTH_SHORT).show();

//                    con.execute();
//                    if (con != null) {
//                        button.setText("Connected");
//                    }
                }catch (SQLException se) {
                    Log.e("ERRO", se.getMessage());
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }
            }
        });
    }

    class EstablishCon extends AsyncTask<String, String, Connection> {

        @Override
        protected Connection doInBackground(String... strings) {
            Connection con=null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/municipal_server","root", "123456");

            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return con;
        }
    }
}


