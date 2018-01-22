package com.restcalc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanislav on 22.01.2018.
 */
public class Client {


    public static void main(String args[])throws Exception{

        URL url = new URL("http://localhost:8080/calc?city=seoul");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        String xml = "";
        BufferedReader reader =	new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String next = null;
        while ((next = reader.readLine()) != null)
            xml += next;

        System.out.println(xml);
    }
}
