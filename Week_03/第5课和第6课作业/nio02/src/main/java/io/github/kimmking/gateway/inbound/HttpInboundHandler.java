package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HttpRequestHeaderFilter;
import io.github.kimmking.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private OkhttpOutboundHandler handler;
    private HttpRequestHeaderFilter headerFilter;
    
    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        //handler = new HttpOutboundHandler(this.proxyServer);
        headerFilter = new HttpRequestHeaderFilter();
        handler = new OkhttpOutboundHandler(this.proxyServer);

    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            /**
             * 作业3 add headerFilter
             */
            headerFilter.filter(fullRequest,ctx);
            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
