package com.restcalc.ServerPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Stanislav on 22.01.2018.
 */
public class Database {

    public Database() {
        try {
            Class.forName("org.h2.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:h2:history", "root", "");
            Statement statement = conn.createStatement();



        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
