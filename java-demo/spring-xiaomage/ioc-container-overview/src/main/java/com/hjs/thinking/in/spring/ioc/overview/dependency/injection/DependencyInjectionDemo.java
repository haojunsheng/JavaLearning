package com.hjs.thinking.in.spring.ioc.overview.dependency.injection;

import com.hjs.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author 俊语
 * @date 2021/6/8 下午10:50
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);

        System.out.println("自定义Bean: "+userRepository.getUsers());

        // 依赖来源二：依赖注入（內建依赖）,
        System.out.println("内建依赖: "+userRepository.getBeanFactory());
        System.out.println("内建Bean和自定义Bean: " + (userRepository.getObjectFactory() == beanFactory));
        // 依赖查找（错误）
//        System.out.println(beanFactory.getBean(BeanFactory.class));


        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject());
        System.out.println("内建Bean" + (userFactory.getObject() == applicationContext));


        // 依赖来源三：容器內建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);

        whoIsIoCContainer(userRepository,applicationContext);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {


        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFactory()


        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplicationContext is BeanFactory

    }

}

