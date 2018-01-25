package com.restcalc.ClientPack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class Requests {

    public static final String CALC_URL = "http://localhost:8080/calc";


    public static String doGetDate(String date) throws Exception{
        /*char chars[];
        chars = exp.toCharArray();
        exp = "";
        for (int i = 0; i < chars.length; i++){
            if (chars[i] == '^') {
                chars[i] = '@';
            }
            exp += chars[i];
        }*/
        URL url = new URL(CALC_URL + "?exp=" );
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

    public static String doPost(String xml) throws Exception{

        URL url = new URL(CALC_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml");

        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(xml);
        osw.close();
        System.out.println("HERE");
        if (conn.getResponseCode() == 200)
        {
            InputStreamReader isr;
            isr = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            System.out.println(sb.toString());
            return sb.toString();
        }
        else
            return "cannot calculate this: " + conn.getResponseCode();
    }

    public static String doGet(String exp) throws Exception {
        URL url = new URL(CALC_URL  + "?exp");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        String xml = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String next = null;
        while ((next = reader.readLine()) != null) {
            xml += next;
        }


        return xml;
    }

}
