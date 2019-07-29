package com.mutongli.innerclass;

/**
 * @author mtl
 * @Description: 匿名内部类
 * @date 2019/7/29 21:50
 */
public class AnonymousInnerDemo {
    public void setInf(Interface inf){
        inf.method();
    }

    public void test(final int a,int b){
//        b = 5; //如果对b没有改变值，则匿名内部类可以访问该变量，等同于被final修饰,如果改变了b的值，则匿名内部类访问该变量会编译报错
        int c = 0;
//        c = 3; //与变量b相同
        this.setInf(new Interface() {
            @Override
            public void method() {
                System.out.println("param1:"+a);
                System.out.println("param2:"+b); //匿名内部类只能访问外部的常量(被final修饰，或者在外部除了定义时赋值，后续没有再改变值的)
                System.out.println("param3:"+c);
                System.out.println("test => setInf => method");
            }
        });
    }

    public static void main(String[] args) {
        new AnonymousInnerDemo().test(1,2);
    }
}

interface Interface{
    void method();
}
