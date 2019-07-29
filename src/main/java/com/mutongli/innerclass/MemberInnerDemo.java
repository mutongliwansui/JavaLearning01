package com.mutongli.innerclass;

/**
 * @author mtl
 * @Description: 成员内部类
 * @date 2019/7/29 21:36
 */
public class MemberInnerDemo {
    private String name = "1";
    private static String password = "1234";

    private void testOutter(){
        System.out.println("outter:"+name);
    }
    private void test(){
        System.out.println("outter:"+name);
    }
    private static void testStatic(){
        System.out.println("outter:"+password);
    }
    class Inner{
        private String name = "2";
//        private static String password = "123"; //成员内部类不能定义静态变量

        //成员内部类不能定义静态方法
//        public static void testStatic(){
//
//        }

        public void test(){
            String name = "3";
            System.out.println(name);
            System.out.println(this.name);
            System.out.println(MemberInnerDemo.this.name);
            testOutter();
            MemberInnerDemo.this.test(); //内部类和外部类存在同一个方法时，可以通过外部类名.this.方法名(参数)来调用
            testStatic();
        }
    }

    public static void main(String[] args) {
        new MemberInnerDemo().new Inner().test(); //成员内部类的实例需要依赖外部类的实例对象
    }
}
