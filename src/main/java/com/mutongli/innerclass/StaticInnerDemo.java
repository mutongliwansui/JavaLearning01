package com.mutongli.innerclass;

/**
 * @author mtl
 * @Description: 静态内部类
 * @date 2019/7/29 21:25
 */
public class StaticInnerDemo {

    private String name;
    private static String password = "123";

    private static void printPassword(){
        System.out.println("outter:" + password);
    }

    private void printName(){
        System.out.println("outter:"+name);
    }

    static class Inner{
        public void test(){
//            System.out.println(StaticInnerDemo.this.name); //静态内部类无法访问外部类的非静态成员变量或者方法
            System.out.println(password); //可以访问外部类的静态变量和静态方法
//            StaticInnerDemo.this.printName();
            printPassword();
        }
        private static String password = "1234"; //静态内部类可以定义静态变量
        //静态内部类可以定义静态方法
        public static void testStatic(){
            System.out.println(password);
            System.out.println(StaticInnerDemo.password);
        }
    }

    public static void main(String[] args) {
        new StaticInnerDemo.Inner().test(); //静态内部类创建不需要依赖外部类对象，直接new 外部类名.内部类名()就可以创建了
    }
}
