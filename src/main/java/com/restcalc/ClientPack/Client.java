package com.restcalc.ClientPack;

import javax.swing.*;
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

        Form form = new Form();
        JFrame jFrame = new JFrame("Calc Rest Client");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(form.panel);
        jFrame.pack();
        jFrame.setSize(640,320);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
