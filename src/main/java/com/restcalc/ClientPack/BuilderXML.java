package com.restcalc.ClientPack;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class BuilderXML {


    public static String newPut(String date, String condition, String result){

        String test = result.split(">")[2];
        test = test.split("<")[0];
        System.out.println(test);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<calc>" +
                "  <dates>" +
                "    "+date +
                "  </dates>" +
                "  <condition>" +
                condition +
                "  </condition>" +
                "  <result>" +
                test +
                "  </result>" +
                "</calc>";
        return xml;
    }
}
