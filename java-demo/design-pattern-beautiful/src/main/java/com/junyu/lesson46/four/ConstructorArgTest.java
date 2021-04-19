package com.junyu.lesson46.four;

/**
 * @author haojunsheng
 * @date 2021/4/15 23:39
 */
public class ConstructorArgTest {
    public static void main(String[] args) {
        ConstructorArg constructorArg = new ConstructorArg.Builder()
                .setArg("123")
                .setRef(false)
                .setType(Integer.class).build();
        System.out.println(constructorArg);
    }
}
