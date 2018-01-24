package com.restcalc.ServerPack;

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


@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class RestServer implements Provider<Source> {

    private CalcClass calc;

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

        double test = calc.calculated("2+4");

        System.out.println(calc.getTime() + " " + test);
        DOMResult dom = new DOMResult();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.transform(source, dom);
            System.out.println(dom.getNode().getLocalName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StreamSource();
    }

    private Source doGet(MessageContext msgContext){
        String query_string = (String) msgContext.get(MessageContext.QUERY_STRING);
        StringBuffer text=new StringBuffer("");
        String exp=query_string.split("=")[1];
        System.out.println(query_string);
        try {

            text.append("<isbn>" + "Hello world!" + "</isbn>");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StreamSource( new StringReader(text.toString()) );
    }
    public static void main(String[] args)
    {
        Endpoint.publish("http://localhost:8080/calc", new RestServer());
    }


}