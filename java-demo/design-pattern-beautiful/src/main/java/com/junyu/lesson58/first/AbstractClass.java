package com.junyu.lesson58.first;

/**
 * @author 俊语
 * @date 2021/5/9 下午5:54
 */
public abstract class AbstractClass {
    public final void templateMethod() {
        //...
        method1();
        //...
        method2();
        //...
    }

    protected abstract void method1();

    protected abstract void method2();
}
