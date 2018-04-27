package com.dikshant.ashhar.mysmartmunicipal;

import android.os.AsyncTask;
import android.util.Log;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ashhar on 3/17/2018.
 */

public class Validation extends AsyncTask<String, String, Boolean> {
    //    Connection con = null;
    String qry;
    boolean invalid = false;

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean invalid = false;
        String data= strings[0],type = strings[1];
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ResultSet rSet=null;
            Connection con=Signup.connection();
            Statement stmt = con.createStatement();
            if(type.equals("user")){
                rSet = stmt.executeQuery("SELECT userid FROM user WHERE userid = '" + data + "'");
                if (rSet.next())
                    invalid = true;
            }
            if(type.equals("email")){
                rSet = stmt.executeQuery("SELECT email FROM user WHERE email = '" + data + "'");
                if (rSet.next())
                    invalid = true;
            }
            if(type.equals("aadhar")){
                rSet = stmt.executeQuery("SELECT aadhar FROM user WHERE aadhar = '" + data + "'");
                if (rSet.next())
                    invalid = true;
            }
            con.close();
            stmt.close();
            rSet.close();
        }catch (SQLException se) {
            Log.e("ERROR  in Validation", se.getMessage());
        }catch (Exception e) {
            Log.e("ERROR  in Validation", e.getMessage());
        }
        return invalid;
    }
}
