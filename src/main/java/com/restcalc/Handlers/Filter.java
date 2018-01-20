package com.restcalc.Handlers;


import com.restcalc.HttpServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2018.
 */
public class Filter extends MessageToMessageDecoder<DefaultFullHttpRequest>{
    //FilterHandler производит фильтрацию запросов, проверку параметров и отсеивание неверных.
    private static final String PREFIX_URL ="/sum";
    private static final Pattern URL_PATTERN_FILTER = Pattern.compile(PREFIX_URL + "(?:\\?first=(\\d+)&second=(\\d+))");

    protected void decode(ChannelHandlerContext channelHandlerContext, DefaultFullHttpRequest defaultFullHttpRequest, List<Object> list) throws Exception {
        if (defaultFullHttpRequest.getMethod() != HttpMethod.GET){
            HttpServer.sendError(channelHandlerContext, "метод к данному ресурсу не применим", HttpResponseStatus.NOT_ACCEPTABLE);
            return;
        }

        String url = defaultFullHttpRequest.getUri();
        url = url == null ? "" : url.toLowerCase();
        if (!url.startsWith(PREFIX_URL)){
            HttpServer.sendError(channelHandlerContext, "ресурс не найден", HttpResponseStatus.NOT_FOUND);
            return;
        }

        Matcher matcher = URL_PATTERN_FILTER.matcher(url);
        if (!matcher.find())
            System.out.println("некоректно указаны параметры");
        try {
            long firstNumber = Long.parseLong(matcher.group(1));
            long secondNumber = Long.parseLong(matcher.group(2));
            defaultFullHttpRequest.headers().add("firstNumber", firstNumber);
            defaultFullHttpRequest.headers().add("secondNumber", secondNumber);
            list.add(defaultFullHttpRequest);
            defaultFullHttpRequest.retain();
        } catch (NumberFormatException e) {
            System.out.println("Неверный форомат параметров");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Server error");
    }
}


