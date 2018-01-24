package com.restcalc.ClientPack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class Form {
    private JTextField calcField;
    private JButton calcButton;
    private JButton countButton;
    private JButton operationButton;
    private JButton onDateButton;
    private JButton ONOperationButton;
    private JButton popularButton;
    private JTextArea calcArea;
    private JTextField queryField;
    private JTextArea queryArea;
    public JPanel panel;

    public Form() {


        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exp = calcField.getText();
                try {
                    calcArea.setText(Requests.doGet(exp));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
        calcArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                e.consume();
            }
        });
        queryArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                e.consume();
            }
        });
    }
}
