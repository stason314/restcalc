package com.restcalc.ServerPack;

import java.sql.*;

/**
 * Created by Stanislav on 22.01.2018.
 */
public class Database {

    private Statement statement;
    public String date;
    public String condition;
    public String resultCalc;

    public Database() {
        try {
            Class.forName("org.h2.Driver").newInstance();
            String root = System.getProperty("user.dir");
            Connection conn = DriverManager.getConnection("jdbc:h2:"+ root, "root", "");
            statement = conn.createStatement();
            statement.execute("DROP TABLE CALCULATING IF EXISTS");
            statement.execute("CREATE TABLE CALCULATING(DATES VARCHAR(50), CONDITION CHAR(255),  RESULT VARCHAR(255));");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void into(String date, String condition, String result){
        try {
            char chars[];
            chars = condition.toCharArray();
            condition = "";
            for (int i = 0; i < chars.length; i++){
                if (chars[i] == '@') {
                    chars[i] = '^';
                }
                condition += chars[i];
            }
            statement.execute("INSERT INTO CALCULATING VALUES("+ "'" + date + "'"+ "," + "'"+condition + "'" + "," + "'" + result + "'" + ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void outQuery(){
        ResultSet result;
        try {
            result = statement.executeQuery("SELECT * FROM CALCULATING");


            while (result.next()) {
                date = result.getString("DATES");
                condition = result.getString("CONDITION");
                resultCalc = result.getString("RESULT");

                System.out.printf("%s %s %s \n", date, condition, resultCalc);
            }
            }catch(SQLException e){
            e.printStackTrace();
        }


    }
}
