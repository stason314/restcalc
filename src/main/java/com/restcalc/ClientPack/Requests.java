package com.restcalc.ClientPack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class Requests {

    public static final String CALC_URL = "http://localhost:8080/calc";


    public static String doGet(String exp) throws Exception{
        char chars[];
        chars = exp.toCharArray();
        exp = "";
        for (int i = 0; i < chars.length; i++){
            if (chars[i] == '^') {
                chars[i] = '@';
            }
            exp += chars[i];
        }
        URL url = new URL(CALC_URL + "?exp=" + exp);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        String xml = "";
        BufferedReader reader =	new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String next = null;
        while ((next = reader.readLine()) != null){
            xml += next;
        }


       return xml;

    }
    public static void doPost(String xml){

    }

}
