package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 应用模块名称: 过滤器：在HttpRequest的Header里放自己的名字<p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/11/4 8:59 下午
 */
public class HttpRequestHeaderFilter implements HttpRequestFilter{

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("nio","huangxiaochun");
        ctx.channel().writeAndFlush(fullRequest);
    }
}
