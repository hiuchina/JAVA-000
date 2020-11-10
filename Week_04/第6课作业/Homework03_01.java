package java0.conc0303;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * executor.submit
 */
public class Homework03_01 {
    
    public static void main(String[] args) {
        
        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        /**
         * Future 获取返回值
         */
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> result = executor.submit(new Callable<Integer>() {
            // 异步执行 下面方法
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        executor.shutdown();
        try {
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
         
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }







    private static int sum() {
        return fibo(36);
    }

    /*private static int fibo(int a) {
        if(a < 2){
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }*/

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        int a1 = 0, a2 = 1;
        for (int i = 2;i <= a;i++) {
            a2 = a1 + (a1 = a2);
        }
        return a1 + a2;
    }

    /*private static int fibo(int a) {
        if(a<=0)
            return 0;
        if(a == 1)
            return 1;

        int f1 = 1;
        int f2 = 1;
        int result = 0;
        int i = 2;
        while(i++ <= a) {
            result = f1 + f2;
            f1 = f2;
            f2 =result;
        }
        return result;
    }*/
}
