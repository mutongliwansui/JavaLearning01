package com.mutongli.concurrent;

import com.mutongli.concurrent.item.OrderDelayItem;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 模拟订单定时失效(如果订单数量过于庞大的，全部载入内存，估计要挂了)，数据量比较大的可以按照下面的进行处理
 * 1.可以先在数据库里把未支付的订单进行排序，按创建时间升序，【过期时间排序可省略，原因可以看注解】，
 *     取出一定数量的订单载入到队列，具体每次载入多少条，需要看服务器性能
 * 2.处理线程对队列里的未支付订单进行失效处理，当线程检测到队列为空的时候，再次返回步骤1进行数据库读取
 * 3.对于删除未支付订单的，或者支付了订单，改变了订单的状态，使得不需要再等待支付，这些操作除了更新数据库外，还需要
 *    在队列里进行查询，如果有发现就从队列移除
 * 注：一般来说同一类型的订单的过期时间是一样的，大概十几分钟左右，所以对于载入后用户创建的订单，直接存入数据库就可以了。
 * 此处的模拟只能应用于日订单量不超过万级别的，对于大型网站，则可以使用云计算进行处理，避免单机作战
 */
public class TestDelayedQueue {
    private static final Logger LOGGER = Logger.getLogger(TestDelayedQueue.class);

    public static void main(String[] args) {
        //创建未支付订单队列，并把未支付的订单存入队列，其中订单都有失效时间
        DelayQueue queue = new DelayQueue();
        OrderDelayItem item1 = OrderDelayItem.newInstance("sc101010101",30, TimeUnit.SECONDS, new Date());
        OrderDelayItem item2 = OrderDelayItem.newInstance("sc101010102",30, TimeUnit.SECONDS, new Date());
        OrderDelayItem item3 = OrderDelayItem.newInstance("sc101010103",30, TimeUnit.SECONDS, new Date());
        queue.add(item1);
        queue.add(item2);
        queue.add(item3);
        LOGGER.info("queue size:"+queue.size());
        //使用单线程池执行
        Executors.newSingleThreadExecutor().execute(new TestDelayedQueue.CertainOrder(queue));
    }

    /**
     * 订单处理类
     */
    static class CertainOrder implements Runnable{
        private DelayQueue<OrderDelayItem> queue;

        public CertainOrder(DelayQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true){
                OrderDelayItem order = null;
                try {
                    order = queue.take();
                    LOGGER.info("["+System.currentTimeMillis()+"]order[ordercode:"+order.getOrdercode()+"] has expired,now remove it");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.error("get order failed!",e);
                }
            }
        }
    }
    @Test
    public void test2(){
        OrderDelayItem item = OrderDelayItem.newInstance("sc101010101",5,TimeUnit.MINUTES,"2018-03-01 16:25:52");
        long delay = item.getDelay(TimeUnit.SECONDS);
        System.out.println("item remain "+delay + " seconds");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        delay = item.getDelay(TimeUnit.SECONDS);
        System.out.println("item remain "+delay + " seconds");
    }
    @Test
    public void test3(){
        long result = TimeUnit.MICROSECONDS.convert(1000,TimeUnit.SECONDS);
        System.out.println(result);
    }
}
