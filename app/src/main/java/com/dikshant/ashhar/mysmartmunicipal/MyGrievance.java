package com.dikshant.ashhar.mysmartmunicipal;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    }
//    class Fetcher extends AsyncTask<String, String, String> {
//        ResultSet rSet;
//        Connection con = null;
//        String qry;
//        Boolean found=false, empty=false, connect=false;
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                con = Starter.connection();
//                if (con!=null) {
//                    connect=true;
//                    qry = "SELECT * FROM grievances WHERE gid = '" + dept + "'";
//                    Statement stmt = con.createStatement();
//                    rSet = stmt.executeQuery(qry);
//
//                        if (rSet.next()) {
//                            found=true;
//                        }
//
//                }
//            } catch (SQLException se) {
//                Log.e("ERRO", se.getMessage());
//            } catch (Exception e) {
//                Log.e("ERRO", e.getMessage());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            String data="",full="";
//            try {
//                if (empty)
//                    Toast.makeText(getApplicationContext(), "Please Select Department", Toast.LENGTH_SHORT).show();
//                if (found){
//                    ResultSetMetaData rsmd = rSet.getMetaData();
//                    while (rSet.next()){
//                        for (int i = 1 ; i<=rsmd.getColumnCount(); i++)
//                            data = rSet.getString(2);
//                        full=full + data+"\n\n";
//                    }
//                    textView.setText(full);
//                }
//
//                if (!connect)
//                    Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT).show();
//
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
