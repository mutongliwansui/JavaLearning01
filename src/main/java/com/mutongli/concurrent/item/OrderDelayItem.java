package com.mutongli.concurrent.item;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderDelayItem  implements Delayed {

    private static final Logger LOGGER = Logger.getLogger(OrderDelayItem.class);

    private String ordercode; //订单编码
    private long exptime; //过期时间(豪秒)
    private long createtime; //创建时间(毫秒)
    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MILLISECONDS;
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private OrderDelayItem() {
    }
    /**
     * 创建订单项，并赋予创建时间和过期时间
     * @param ordercode 订单号
     * @param exptime 过期时间
     * @param unit 过期时间单位
     * @param date 创建时间
     * @return
     */
    public static OrderDelayItem newInstance(String ordercode, long exptime, TimeUnit unit, Date date){
        OrderDelayItem item = new OrderDelayItem();
        item.setOrdercode(ordercode);
        item.setCreatetime(date.getTime());
        item.setExptime(DEFAULT_TIMEUNIT.convert(exptime,unit) +  item.createtime);
        return item;
    }
    /**
     * 创建订单项，并赋予创建时间和过期时间
     * @param ordercode 订单号
     * @param exptime 过期时间
     * @param unit 过期时间单位
     * @param createtime 创建时间
     * @param pattern 创建时间格式(默认yyyy-mm-dd hh:mi:ss)
     * @return
     */
    public static OrderDelayItem newInstance(String ordercode, long exptime, TimeUnit unit, String createtime, String pattern){
        OrderDelayItem item = null;
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(createtime);
            item = newInstance(ordercode,exptime,unit,date);
        } catch (ParseException e) {
            LOGGER.error("parse createtime failed",e);
        }finally {
            return item;
        }
    }

    /**
     * 创建订单项，并赋予创建时间和过期时间
     * @param ordercode 订单号
     * @param exptime 过期时间
     * @param unit 过期时间单位
     * @param createtime 创建时间(默认格式yyyy-mm-dd)
     * @return
     */
    public static OrderDelayItem newInstance(String ordercode, long exptime, TimeUnit unit, String createtime){
        return newInstance(ordercode,exptime,unit,createtime,DEFAULT_TIME_PATTERN);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.exptime - System.currentTimeMillis(),DEFAULT_TIMEUNIT);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf(this.exptime).compareTo(Long.valueOf(((OrderDelayItem)o).getExptime()));
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public long getExptime() {
        return exptime;
    }

    public void setExptime(long exptime) {
        this.exptime = exptime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
