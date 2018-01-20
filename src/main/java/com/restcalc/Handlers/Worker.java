package com.restcalc.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;


/**
 * Created by Stanislav on 20.01.2018.
 */
public class Worker extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DefaultFullHttpRequest request = (DefaultFullHttpRequest) msg;

        int firstNumber = Integer.parseInt(request.headers().get("firstNumber"));
        int secondNumber = Integer.parseInt(request.headers().get("secondNumber"));

        long sum = firstNumber + secondNumber;

        ByteBuf content = Unpooled.copiedBuffer("результат:" + sum, Charset.forName("windows-1251"));
        DefaultFullHttpResponse response =
                new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        content);

        response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.ACCEPT_CHARSET, Charset.forName("UTF-8"));

        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);

        request.release();

    }
}
