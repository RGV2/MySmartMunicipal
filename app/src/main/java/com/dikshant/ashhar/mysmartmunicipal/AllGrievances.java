package com.dikshant.ashhar.mysmartmunicipal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AllGrievances extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_grievances);

        ll = (LinearLayout) findViewById(R.id.linearLayout);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String totalGrievances = sharedPreferences.getString("totalGrievances", "");
        System.out.println("-------------------------------------------------------------------------"+totalGrievances);

        Fetch fetch = new Fetch();
        fetch.execute();

        Button button =(Button) findViewById(R.id.btn_Gview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(getApplicationContext(),radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    class Fetch extends AsyncTask<String, String, String> {

        Connection con = null;
        Boolean found = false, empty = false, connect = false, proceed = false;
        int counter=0;
        String username,totalGrievance;
        Statement stmt;
        ResultSet rSet;


        @Override
        protected void onPreExecute() {
            SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("key", "");
            totalGrievance=sharedPreferences.getString("totalGrievances","");
            counter= Integer.parseInt(totalGrievance);

        }

        @Override
        protected String doInBackground(String... strings) {
            con = Starter.connection();
            if (con != null) {
                try {
                    stmt = con.createStatement();
                    rSet = stmt.executeQuery("select gid, department from grievances where user_name = '" + username + "'");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), totalGrievance, Toast.LENGTH_SHORT).show();
//            for (int i = 1; i<=counter; i++) {
//                try {
//                    while (rSet.next()) {
//                        radioButton = new RadioButton(getBaseContext());
//                        radioButton.setId(i);
//                        radioButton.setText(rSet.getString("gid")+":"+rSet.getString("department"));
//                        System.out.println("-------------------------------------------------------"+radioButton.getText());
////                        int value = Integer.parseInt(string.replaceAll("[^0-9]", ""));
//                        radioGroup.addView(radioButton);
//
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//
//                }
//            }
//            radioGroup.addView(radioButton);
//            ll.removeView(findViewById(R.id.radioGroup));
            final RadioButton[] rb = new RadioButton[5];
//            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
            for(int i=0; i<5; i++){
                rb[i]  = new RadioButton(getApplicationContext());
                rb[i].setText("btn"+i);
                rb[i].setId(i + 100);
                radioGroup.addView(rb[i]);
            }
//            ll.addView(radioGroup);//you add the whole RadioGroup to the layout
        }
    }
}