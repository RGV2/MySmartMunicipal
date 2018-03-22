package com.dikshant.ashhar.mysmartmunicipal;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ashhar on 3/17/2018.
 */

public class Validation extends AsyncTask<String, String, Boolean> {
    Connection con = null;
    String qry;
    boolean valid = false;

    @Override
    protected Boolean doInBackground(String... strings) {
        String s= strings[0];
        try {
            ResultSet rSet;
            con=Starter.connection();

            Statement stmt = con.createStatement();
            if (s.length()==12 && android.text.TextUtils.isDigitsOnly(s)) {
                rSet = stmt.executeQuery("SELECT * FROM user WHERE aadhar = '" + s + "'");
            }else if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                rSet = stmt.executeQuery("SELECT * FROM user WHERE email = '" + s + "'");
            }else {
                rSet = stmt.executeQuery("SELECT * FROM user WHERE userid = '" + s + "'");
            }
            if (rSet.next())
                valid = true;
            con.close();
        }catch (SQLException se) {
            Log.e("ERROR  in Validation", se.getMessage());
        }catch (Exception e) {
            Log.e("ERROR  in Validation", e.getMessage());
        }
        return valid;
    }
}
