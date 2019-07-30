package com.mutongli.jdbc;

import java.sql.*;
import java.util.Enumeration;

/**
 * @author mtl
 * @Description: DriverManager学习
 * @date 2019/7/30 22:30
 */
public class JBCDemo {
    public void test() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //com.mysql.jdbc.Driver类里面有个静态代码块
        /**
         * static {
         *         try {
         *             DriverManager.registerDriver(new Driver()); //在此处把Driver注册到DriverManager
         *         } catch (SQLException var1) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         */
        Connection connection = DriverManager.getConnection("jdbc:mysql://rm-bp1wj174j4k96rl396o.mysql.rds.aliyuncs.com:3306/cbt_db"
                , "mtl", "Ly520101");
        PreparedStatement ps = connection.prepareStatement("select * from users ");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            //rs.getXX(index); index是从1开始的
            int userid = rs.getInt(1);
            String username = rs.getString(2);
            String email = rs.getString(3);
            System.out.println(userid+":"+username+":"+email);
        }
        rs.close();
        ps.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new JBCDemo().test();
    }
}
