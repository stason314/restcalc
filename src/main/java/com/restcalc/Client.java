package com.restcalc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanislav on 22.01.2018.
 */
public class Client {


    public static void main(String args[])throws Exception{

        URL url = new URL("http://localhost:8080/calc?city=seoul");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.connect();

        String xml = "";
        BufferedReader reader =	new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String next = null;
        while ((next = reader.readLine()) != null)
            xml += next;

        System.out.println(xml);

        /*

        URL url2 = new URL("http://localhost:8080/calc?city=seoul");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("GET");
       // conn2.setDoOutput(true);
        conn2.connect();

      //  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn2.getOutputStream()));
        //writer.append("Helloooooo");*/
    }
}
