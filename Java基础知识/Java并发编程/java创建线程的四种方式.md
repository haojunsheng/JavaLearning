[TOC]

# 1. 继承Thread类创建线程类

```java
package com.thread;  
  
public class FirstThreadTest extends Thread{  
    int i = 0;  
    //重写run方法，run方法的方法体就是现场执行体  
    public void run()  
    {  
        for(;i<100;i++){  
        System.out.println(getName()+"  "+i);  
          
        }  
    }  
    public static void main(String[] args)  
    {  
        for(int i = 0;i< 100;i++)  
        {  
            System.out.println(Thread.currentThread().getName()+"  : "+i);  
            if(i==20)  
            {  
                new FirstThreadTest().run();  
                new FirstThreadTest().run();  
            }  
        }  
    }  
  
}  
```
# 2. 通过Runable接口创建线程类

```
public class RunnableThreadTest implements Runnable  
{ 
private int i;  
public void run()  
{  
    for(i = 0;i <100;i++)  
    {  
        System.out.println(Thread.currentThread().getName()+" "+i);  
    }  
}  
public static void main(String[] args)  
{  
    for(int i = 0;i < 100;i++)  
    {  
        System.out.println(Thread.currentThread().getName()+" "+i);  
        if(i==20)  
        {  
            RunnableThreadTest rtt = new RunnableThreadTest();  
            new Thread(rtt,"新线程1").start();  
            new Thread(rtt,"新线程2").start();  
        }  
    }  
  
}  
}
```

# 3. 通过Callable和FutureTask创建线程

a. 创建Callable接口的实现类，并实现call()方法；
b. 创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callback对象的call()方法的返回值；
c. 使用FutureTask对象作为Thread对象的target创建并启动新线程；
d. 调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。

```java
import java.util.concurrent.Callable;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.FutureTask;  

public class CallableThreadTest implements Callable<Integer>
{

    public static void main(String[] args)
    {
        CallableThreadTest ctt = new CallableThreadTest();
        FutureTask<Integer> ft = new FutureTask<Integer>(ctt);
//        Thread thread = new Thread(ft,"有返回值的线程");
//        thread.start();
        for(int i = 0;i < 100;i++)
        {
            System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
            if(i==20)
            {
                new Thread(ft,"有返回值的线程").start();
            }
        }
        try
        {
            System.out.println("子线程的返回值："+ft.get());
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public Integer call() throws Exception
    {
        int i = 0;
        for(;i<100;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }

}  
```

# 4. 通过线程池创建线程

```java
/**

- */
  package com.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**

- @author Maggie
  *
   */
  public class ThreadPool 
  {
  /* POOL_NUM */
  private static int POOL_NUM = 10;

  /**

  - Main function
    */
    public static void main(String[] args)
    {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for(int i = 0; i<POOL_NUM; i++)
    {
    	RunnableThread thread = new RunnableThread();
    	executorService.execute(thread);
    }
    }
    }

class RunnableThread implements Runnable
{
	private int THREAD_NUM = 10;
	public void run()
	{
		for(int i = 0; i<THREAD_NUM; i++)
		{
			System.out.println("线程" + Thread.currentThread() + " " + i);
		} 
	}
}
```


