package com.dikshant.ashhar.mysmartmunicipal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Dikshant Sharma on 03-04-2018.
 */

class Helpline {
    private String name,phoneno;
    public Helpline(String name,String phone){
        this.name=name;
        this.phoneno=phone;
    }
    public String getname(){
        return name;
    }
    public String getphone(){
        return phoneno;
    }

}
