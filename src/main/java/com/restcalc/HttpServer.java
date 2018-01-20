package com.restcalc;

import com.restcalc.Handlers.Filter;
import com.restcalc.Handlers.Worker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cors.*;

import java.nio.charset.Charset;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap server = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        private final CorsConfig corsConfig = CorsConfig
                                .anyOrigin()
                                .allowNullOrigin()
                                .allowCredentials().build();

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new HttpResponseEncoder())
                                    .addLast(new HttpRequestDecoder())
                                    .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
                                    .addLast(new CorsHandler(corsConfig))
                                    .addLast(new Filter())
                                    .addLast(new Worker());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 500)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = server.bind("localhost", 8080).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            if (channelFuture != null) channelFuture.channel().close().awaitUninterruptibly();
        }
    }

    public static void sendError(ChannelHandlerContext ctx, String errorMessage, HttpResponseStatus status) {
        ByteBuf content = Unpooled.copiedBuffer(errorMessage, Charset.forName("windows-1251"));
        FullHttpResponse response =
                new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        status,
                        content);

        response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.ACCEPT_CHARSET, Charset.forName("windows-1251"));

        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}