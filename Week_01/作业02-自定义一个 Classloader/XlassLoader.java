package l01.hello;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 应用模块名称: 【类加载器】练习<p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author
 * @since 2020/10/19 11:30 下午
 */
public class XlassLoader extends ClassLoader {
    private String classPath="";

    public XlassLoader(String classPath){
        this.classPath = classPath;
    }

    /**
     * 主方法
     * @param args
     */
    public static void main(String[] args) {
        try{
            Class xlass = new XlassLoader("/l01/hello/").findClass("Hello");
            Object obj = xlass.newInstance();
            Method hello = xlass.getDeclaredMethod("hello");
            hello.invoke(obj);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查找类方法
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException{
        try{
            byte[] data = loadByte(className);
            return defineClass(className,data,0,data.length);
        }catch (Exception e){
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 加载xlass文件
     * @param className
     * @return
     * @throws Exception
     */
    private byte[] loadByte(String className) throws Exception {
        className = className.replaceAll("\\.","/");
        FileInputStream fis = new FileInputStream(classPath+className+".xlass");
        int len =  fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return  decodeData(data);
    }

    /** 解码文件数据
     * 解码xlass
     * @param data
     * @return
     */
    private byte[] decodeData(byte[] data){
        for(int i=0;i<data.length;i++){
            data[i] = (byte) (255 - data[i]);
        }
        return data;
    }
}
