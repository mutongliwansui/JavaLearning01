package com.mutongli.inf;

public class TestMain {
    public void fun(){
        System.out.println("测试方法");
    }
    public static class Tester {
        private String aab001;
        public static void main(String[] args) {
            TestMain tm = new TestMain();
            tm.fun();
        }

        public String getAab001() {
            return aab001;
        }

        public void setAab001(String aab001) {
            this.aab001 = aab001;
        }
    }
}
