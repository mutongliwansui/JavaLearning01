package com.mutongli.innerclass;

/**
 * @author mtl
 * @Description: 局部内部类
 * @date 2019/7/29 22:01
 */
public class LocalInnerDemo {
    private String name = "1";

    public void test(){
        final String password1 = "5";
        String passsword = "3";
//        passsword = "4"; //局部内部类和匿名内部类一样，只能访问外部方法或者作用域里的常量(final修饰，或者定义后不再改变值的)
        class Inner{
            private String name = "2";
            private void testInner(){
                System.out.println();
                System.out.println(LocalInnerDemo.this.name);
                System.out.println(name);
                System.out.println(password1);
                System.out.println(passsword);
            }
        }
        new Inner().testInner(); //局部内部类的有效范围和局部变量一样，只有当前方法或者作用域有效;
    }

    public static void main(String[] args) {
        new LocalInnerDemo().test();
    }
}
