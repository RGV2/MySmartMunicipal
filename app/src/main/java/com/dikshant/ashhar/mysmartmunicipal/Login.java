package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    EditText uid, pwd;
    public String user_id, pass, qry;

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
                    EstablishCon establishCon = new EstablishCon();
                    establishCon.execute();
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }
            }
        });
    }

    class EstablishCon extends AsyncTask<String, String, String> {

        Boolean ifLogin=false;
        @Override
        protected String doInBackground(String... strings) {
            Connection con = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/municipal_server", "root", "aezakmi1");
                user_id=uid.getText().toString();
                pass=pwd.getText().toString();
                qry = "SELECT * FROM user WHERE userid = '" + user_id + "' and password = '" + pass+"'";
                Statement stmt = con.createStatement();
                ResultSet rSet = stmt.executeQuery(qry);
                if (rSet.next())
                    ifLogin=true;

            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            }catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            }
            catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
           if (ifLogin) {
               Intent intentMain = new Intent(Login.this,HomePage.class);
               intentMain.putExtra("user_id",user_id);
               startActivity(intentMain);
               Toast.makeText(getApplicationContext(), "Hello " + user_id, Toast.LENGTH_SHORT).show();
           }else
               Toast.makeText(getApplicationContext(), "Wrong Credentials" + user_id, Toast.LENGTH_SHORT).show();
        }
    }
}


