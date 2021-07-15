package com.hjs.thinking.in.spring.bean.definition;

import com.hjs.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean 垃圾回收（GC）示例
 *
 * @author haojunsheng
 * @date 2021/6/29 23:30
 */
public class BeanGarbageCollectionDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        applicationContext.register(BeanInitializationDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(UserFactory.class));
        System.out.println("准备关闭");
        // 关闭 Spring 应用上下文
        applicationContext.close();
        System.out.println("已关闭");
        Thread.sleep(5000L);
        // 强制触发 GC
        // 在ApplicationContext关闭之前，GC是不会回收Bean的，纵然显示的调用也是如此。
        // 而在ApplicationContext关闭之后，JVM会在垃圾回收周期中去回收掉Bean。
        System.gc();
        Thread.sleep(5000L);
        System.out.println("回收成功");
        System.out.println(applicationContext.getBean(UserFactory.class));
    }
}
