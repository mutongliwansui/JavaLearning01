package com.mutongli.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestArrayBlockQueue {
    @Test
    public void test(){
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        Productor productor = new Productor(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(productor).start();
        new Thread(consumer).start();
    }
}

class Productor implements Runnable{

    private BlockingQueue queue = null;

    public Productor(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Consumer implements Runnable{

    private BlockingQueue queue = null;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
