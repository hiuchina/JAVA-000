package l04.nio;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * 应用模块名称: 用OKHttpClient访问Socket服务（http协议） <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/10/26 10:06 下午
 */
public class OkHttpClientVisitor {

    /**
     * socket服务地址信息
     */
    private static final String SERVER_URL ="http://localhost:8801";


    /**
     * 主方法
     * @param args
     */
    public static void main(String[] args) {
        try{
            System.out.println("访问8801端口服务成功，返回信息为：" + new OkVisitor().doOKHttpClientRequest(SERVER_URL));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("访问8801端口服务失败！");
        }
    }

    /**
     * 访问服务，并返回服务输出的信息。
     * @param url
     * @return
     * @throws IOException
     */
     public String doOKHttpClientRequest(String url)throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
