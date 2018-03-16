package com.mutongli.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Interface{
    void doSomething();
    void somethingElse(String arg);
}
class RealObject implements Interface{
    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("somethingElse "+arg);
    }
}
class SimpleProxy implements  Interface{
    private Interface Interface;

    public SimpleProxy(Interface anInterface) {
        Interface = anInterface;
    }

    @Override
    public void doSomething() {
        System.out.println("Proxy Before");
        Interface.doSomething();
        System.out.println("Proxy After");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("Proxy Before");
        Interface.somethingElse(arg);
        System.out.println("Proxy After");
    }
}
class DynamicProxyHandler implements InvocationHandler{
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("***** proxy:"+proxy.getClass()+",method:"+method +",atgs:"+args);
        if(null != args){
            for (Object arg : args) {
                System.out.println("  " + arg);
            }
        }
        return method.invoke(proxy,args);
    }
}
public class SimpleProxyDemo {
    public static void consumer(Interface Interface){
        Interface.doSomething();
        Interface.somethingElse("bonono");
    }
    public static void main(String[] args) {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
        Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(),
                new Class[]{Interface.class},
                new DynamicProxyHandler(new RealObject()));
        consumer(proxy);
    }
}
