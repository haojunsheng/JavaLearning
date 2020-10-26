package com.hjs.jvm.basic_1;

/**
 * @author 俊语
 * @date 2020/10/26 19:43
 */
public class Foo_01_1 {
    static boolean boolValue;
    public static void main(String[] args) {
        boolValue = true;
        if (boolValue) {
            System.out.println("Hello, Java!");
        }
        if (boolValue == true) {
            System.out.println("Hello, JVM!");
        }
    }
}
