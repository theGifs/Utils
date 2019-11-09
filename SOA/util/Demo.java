package com.bz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Demo {


    public static void main(String[] args) {

        String s = HttpClientUtil.doPost("https://www.zhipin.com/job_detail/b9b4ee5426ac741c1H150t29GVI~.html?ka=search_list_2");
//        String s = HttpClientUtil.doGet("http://www.baidu.com");
        System.out.println(s);
    }

    public static void tes2() {

        Contener contener = new Contener();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{

            if(contener.size() != 5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(contener.size()+"--------------------");
        }).start();

        new Thread(()->{

            for (int i = 0; i <10 ; i++) {
                contener.add(i);
                System.out.println(contener.size()+"*****");
                if(contener.size() == 5){
                    latch.countDown();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();




    }












    public static void tes() {

        Contener c = new Contener();

        new Thread(()->{
            while (true){
                if(c.size() == 5){
                    System.out.println(c.size()+"''''''''''''''''''");
                    break;
                }
            }
        }).start();


        new Thread(()->{
                for (int i = 0; i < 10; i++) {
                    c.add(i);
                    System.out.println("ccccc"+c.size());

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }).start();

    }
}
class Contener{

    List<Integer> list = new ArrayList<>();

    public void add(int id){
        list.add(id);
    }
    public int size(){
        return list.size();
    }
}
