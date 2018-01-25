package com.restcalc.ClientPack;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class BuilderXML {


    public static String newPut(String date, String condition){

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<calc>" +
                "  <dates>" +
                "    "+date +
                "  </dates>" +
                "  <condition>" +
                condition +
                "  </condition>" +
                "</calc>";
        return xml;
    }
}
