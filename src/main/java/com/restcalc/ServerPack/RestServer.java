package com.restcalc.ServerPack;

import org.w3c.dom.NodeList;

import java.io.*;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class RestServer implements Provider<Source> {

    private CalcClass calc;
    public static Database database;

    public RestServer() {
        calc = new CalcClass();
    }

    @Resource
    protected WebServiceContext wsContext;

    @Override
    public Source invoke(Source request) {
        MessageContext msg_ctx = wsContext.getMessageContext();
        String httpMethod = (String) msg_ctx.get(MessageContext.HTTP_REQUEST_METHOD);
        System.out.println(httpMethod);
        switch (httpMethod){
            case "GET": return doGet(msg_ctx);
            case  "POST": return doPost(msg_ctx, request);
            default: throw new HTTPException(405);
        }

    }

    private Source doPost(MessageContext msgContext, Source source){

        DOMResult dom = new DOMResult();
        String resultCalc = "";
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.transform(source, dom);
            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();
            NodeList calcs = (NodeList) xp.evaluate("/calc", dom.getNode(),
                    XPathConstants.NODESET);
            String date = xp.evaluate("dates", calcs.item(0)).trim();
            String condition= xp.evaluate("condition", calcs.item(0)).trim();
            resultCalc= Double.toString(calc.calculated(condition));

            database.into(date, condition, resultCalc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
        xml.append("<response>" +  resultCalc + "</response>");
        return new StreamSource(new StringReader(xml.toString()));
    }

    private Source doGet(MessageContext msgContext){
        String query_string = (String) msgContext.get(MessageContext.QUERY_STRING);
        StringBuffer text=new StringBuffer("");
        String result = null;

        if (query_string == null){

            try {
                text.append("<result>" + "</result>");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            String[] arrS;
            arrS = query_string.split("=");
            String res=arrS[0];
            if (res.equals("count")){
                if (arrS.length == 1){
                    result = "Введите дату";
                }else {
                    String exp=arrS[1];
                    result = database.countQuery(exp);
                }
            }
            try {
                text.append("<count>" + result + "</count>");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new StreamSource( new StringReader(text.toString()) );
    }
    public static void main(String[] args) {
        database = new Database();
        Endpoint.publish("http://localhost:8080/calc", new RestServer());
    }


}