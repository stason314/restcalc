package com.restcalc.ServerPack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> countQuery(String date){
        ResultSet resultSet;
        List<String> list = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT * FROM CALCULATING WHERE DATES = '"  + date + "'");
            while (resultSet.next()){
                date = resultSet.getString("DATES");
                condition = resultSet.getString("CONDITION");
                resultCalc = resultSet.getString("RESULT");

                System.out.printf("%s %s %s \n", date, condition, resultCalc);
            }

        }catch (SQLException e){

        }
        return null;
    }
}
