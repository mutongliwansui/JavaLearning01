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
        Enumeration<Driver> driver = DriverManager.getDrivers();
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
