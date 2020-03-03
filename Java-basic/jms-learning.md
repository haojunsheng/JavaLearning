<!--ts-->
   * [前言](#前言)
   * [1. 深入掌握JMS（一）：JSM基础](#1-深入掌握jms一jsm基础)
      * [1.1 JMS基本概念](#11-jms基本概念)
      * [1.2 几个重要概念](#12-几个重要概念)
   * [2. 深入掌握JMS（二）：一个JMS例子](#2-深入掌握jms二一个jms例子)
   * [3. 深入掌握JMS（三）：MessageListener](#3-深入掌握jms三messagelistener)
   * [4. 深入掌握JMS（四）：实战Queue](#4-深入掌握jms四实战queue)
   * [5. 深入掌握JMS（五）：实战Topic](#5-深入掌握jms五实战topic)
   * [6. 深入掌握JMS（六）：消息头](#6-深入掌握jms六消息头)
   * [7. 深入掌握JMS（七）：DeliveryMode例子](#7-深入掌握jms七deliverymode例子)
   * [8. 深入掌握JMS（八）：JMSReplyTo](#8-深入掌握jms八jmsreplyto)
   * [9. 深入掌握JMS（九）：Selector](#9-深入掌握jms九selector)
   * [10 .<a href="http:// http//www.360doc.com/content/090712/20/18042_4241252.html" rel="nofollow">深入掌握JMS（十）:JMSCorrelationID&amp;Selector</a>](#10-深入掌握jms十jmscorrelationidselector)
   * [11. <a href="http://www.360doc.com/content/090712/21/18042_4241975.html" rel="nofollow">深入掌握JMS（十一）:TemporaryQueue&amp;TemporaryTopic</a>](#11-深入掌握jms十一temporaryqueuetemporarytopic)
   * [12. <a href="http://www.360doc.com/content/090712/21/18042_4242116.html" rel="nofollow">深入掌握JMS（十二）:MDB</a>](#12-深入掌握jms十二mdb)

<!-- Added by: anapodoton, at: Tue Mar  3 17:24:10 CST 2020 -->

<!--te-->

# 前言

# 1. 深入掌握JMS（一）：JSM基础

## 1.1 JMS基本概念 

​     JMS(Java Message Service) 即Java消息服务。它提供标准的产生、发送、接收消息的接口简化企业应用的开发。它支持两种消息通信模型：点到点（point-to-point）（P2P）模型和发布/订阅（Pub/Sub）模型。P2P 模型规定了一个消息只能有一个接收者;Pub/Sub 模型允许一个消息可以有多个接收者。

​    对于点到点模型，消息生产者产生一个消息后，把这个消息发送到一个Queue（队列）中，然后消息接收者再从这个Queue中读取，一旦这个消息被一个接收者读取之后，它就在这个Queue中消失了，所以一个消息只能被一个接收者消费。

​    与点到点模型不同，发布/订阅模型中，消息生产者产生一个消息后，把这个消息发送到一个Topic中，这个Topic可以同时有多个接收者在监听，当一个消息到达这个Topic之后，所有消息接收者都会收到这个消息。      

简单的讲，点到点模型和发布/订阅模型的区别就是前者是一对一，后者是一对多。

## 1.2 几个重要概念

- Destination：消息发送的目的地，也就是前面说的Queue和Topic。创建好一个消息之后，只需要把这个消息发送到目的地，消息的发送者就可以继续做自己的事情，而不用等待消息被处理完成。至于这个消息什么时候，会被哪个消费者消费，完全取决于消息的接受者。

- Message：从字面上就可以看出是被发送的消息。它有下面几种类型
  - StreamMessage：Java 数据流消息，用标准流操作来顺序的填充和读取。
  - MapMessage：一个Map类型的消息；名称为 string 类型，而值为 Java 的基本类型。
  - TextMessage：普通字符串消息，包含一个String。
  - ObjectMessage：对象消息，包含一个可序列化的Java 对象
  - BytesMessage：二进制数组消息，包含一个byte[]。
  - XMLMessage:  一个XML类型的消息。

最常用的是TextMessage和ObjectMessage。

- Session：与JMS提供者所建立的会话，通过Session我们才可以创建一个Message。

- Connection：与JMS提供者建立的一个连接。可以从这个连接创建一个会话，即Session。

- ConnectionFactory: 那如何创建一个Connection呢？这就需要下面讲到的ConnectionFactory了。通过这个工厂类就可以得到一个与JMS提供者的连接，即Conection。

- Producer：消息的生产者，要发送一个消息，必须通过这个生产者来发送。

- MessageConsumer：与生产者相对应，这是消息的消费者或接收者，通过它来接收一个消息。

前面多次提到JMS提供者，因为JMS给我们提供的只是一系列接口，当我们使用一个JMS的时候，还是需要一个第三方的提供者，它的作用就是真正管理这些Connection，Session，Topic和Queue等。

​    通过下面这个简图可以看出上面这些概念的关系。

  ConnectionFactory---->Connection--->Session--->Message
  Destination + Session------------------------------------>Producer
  Destination + Session------------------------------------>MessageConsumer

​    那么可能有人会问: ConnectionFactory和Destination 从哪儿得到?

​    这就和JMS提供者有关了. 如果在一个JavaEE环境中, 可以通过JNDI查找得到, 如果在一个非JavaEE环境中, 那只能通过JMS提供者提供给我们的接口得到了.

# 2. 深入掌握JMS（二）：一个JMS例子

 前一讲简单的介绍了一下JMS的基本概念, 这一讲结合一个例子让大家深入理解前一讲的基本概念. 首先需要做的是选择一个JMS提供者, 如果在JavaEE环境中可以不用考虑这些. 我们选择ActiveMQ, 官方地址: http://activemq.apache.org/. 网上有很多介绍ActiveMQ的文档, 所以在这里就不介绍了.

按照上一讲的这个简图, 

  ConnectionFactory---->Connection--->Session--->Message
  Destination + Session------------------------------------>Producer
  Destination + Session------------------------------------>MessageConsumer

首先需要得到ConnectionFactoy和Destination，这里创建一个一对一的Queue作为Destination。

```java
 ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
  Queue queue = new ActiveMQQueue("testQueue");
```

然后又ConnectionFactory创建一个Connection, 再启动这个Connection: 

```java
  Connection connection = factory.createConnection();
  connection.start();
```

接下来需要由Connection创建一个Session:

```java
  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
```

​    现在暂且不用管参数的含义, 以后会详细讲到.

```java
下面就可以创建Message了,这里创建一个TextMessage。
  Message message = session.createTextMessage("Hello JMS!");

要想把刚才创建的消息发送出去，需要由Session和Destination创建一个消息生产者：
  MessageProducer producer = session.createProducer(queue);

下面就可以发送刚才创建的消息了：
  producer.send(message);

消息发送完成之后，我们需要创建一个消息消费者来接收这个消息：
  MessageConsumer comsumer = session.createConsumer(queue);
  Message recvMessage = comsumer.receive();

消息消费者接收到这个消息之后，就可以得到它的内容：
  System.out.println(((TextMessage)recvMessage).getText());
```

至此，一个简单的JMS例子就完成了。下面是全部源码 ：

```java
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class MessageSendAndReceive {

public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
    
        Connection connection = factory.createConnection();
        connection.start();
        
        Queue queue = new ActiveMQQueue("testQueue");
        
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Message message = session.createTextMessage("Hello JMS!");
        
        MessageProducer producer = session.createProducer(queue);
        producer.send(message);
    
        System.out.println("Send Message Completed!");
        
        MessageConsumer comsumer = session.createConsumer(queue);
        Message recvMessage = comsumer.receive();
        System.out.println(((TextMessage)recvMessage).getText());
    }
}
```

# 3. 深入掌握JMS（三）：MessageListener

  消息的消费者接收消息可以采用两种方式：

  1、consumer.receive() 或 consumer.receive(int timeout)；
  2、注册一个MessageListener。

  采用第一种方式，消息的接收者会一直等待下去，直到有消息到达，或者超时。后一种方式会注册一个监听器，当有消息到达的时候，会回调它的onMessage()方法。下面举例说明：

```java
MessageConsumer comsumer = session.createConsumer(queue);
 comsumer.setMessageListener(new MessageListener(){
            @Override
            public void onMessage(Message m) {
                TextMessage textMsg = (TextMessage) m;
                try {
                    System.out.println(textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
```

# 4. 深入掌握JMS（四）：实战Queue

 Queue实现的是点到点模型，在下面的例子中，启动2个消费者共同监听一个Queue，然后循环给这个Queue中发送多个消息，我们依然采用ActiveMQ。

```java
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class QueueTest {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
    
        Connection connection = factory.createConnection();
        connection.start();
        

        //创建一个Queue
        Queue queue = new ActiveMQQueue("testQueue");

        //创建一个Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        

        //注册消费者1
        MessageConsumer comsumer1 = session.createConsumer(queue);
        comsumer1.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer1 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        

        //注册消费者2
        MessageConsumer comsumer2 = session.createConsumer(queue);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer2 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            
        });
        

        //创建一个生产者，然后发送多个消息。
        MessageProducer producer = session.createProducer(queue);
        for(int i=0; i<10; i++){
            producer.send(session.createTextMessage("Message:" + i));
        }
    }

}
```



  运行这个例子会得到下面的输出结果：

Consumer1 get Message:0

Consumer2 get Message:1

Consumer1 get Message:2

Consumer2 get Message:3

Consumer1 get Message:4

Consumer2 get Message:5

Consumer1 get Message:6

Consumer2 get Message:7

Consumer1 get Message:8

Consumer2 get Message:9

  可以看出每个消息直被消费了一次，但是如果有多个消费者同时监听一个Queue的话，无法确定一个消息最终会被哪一个消费者消费。

# 5. 深入掌握JMS（五）：实战Topic

 与Queue不同的是，Topic实现的是发布/订阅模型，在下面的例子中，启动2个消费者共同监听一个Topic，然后循环给这个Topic中发送多个消息。

```java
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;


public class TopicTest {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
    
        Connection connection = factory.createConnection();
        connection.start();
        
        //创建一个Topic
        Topic topic= new ActiveMQTopic("testTopic");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         
        //注册消费者1
        MessageConsumer comsumer1 = session.createConsumer(topic);
        comsumer1.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer1 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        
        //注册消费者2
        MessageConsumer comsumer2 = session.createConsumer(topic);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer2 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            
        });
        
        //创建一个生产者，然后发送多个消息。
        MessageProducer producer = session.createProducer(topic);
        for(int i=0; i<10; i++){
            producer.send(session.createTextMessage("Message:" + i));
        }
    }

}
```

运行后得到下面的输出结果：

Consumer1 get Message:0
Consumer2 get Message:0
Consumer1 get Message:1
Consumer2 get Message:1
Consumer1 get Message:2
Consumer2 get Message:2
Consumer1 get Message:3
Consumer2 get Message:3
Consumer1 get Message:4
Consumer2 get Message:4
Consumer1 get Message:5
Consumer2 get Message:5
Consumer1 get Message:6
Consumer2 get Message:6
Consumer1 get Message:7
Consumer2 get Message:7
Consumer1 get Message:8
Consumer2 get Message:8
Consumer1 get Message:9
Consumer2 get Message:9

说明每一个消息都会被所有的消费者消费。

# 6. 深入掌握JMS（六）：消息头

 一个消息对象分为三部分：消息头(Headers)，属性（Properties）和消息体（Payload）。对于StreamMessage和MapMessage，消息本身就有特定的结构，而对于TextMessage，ObjectMessage和BytesMessage是无结构的。一个消息可以包含一些重要的数据或者仅仅是一个事件的通知。

​    消息的Headers部分通常包含一些消息的描述信息，它们都是标准的描述信息。包含下面一些值：

　　**JMSDestination**
       消息的目的地，Topic或者是Queue。

　　**JMSDeliveryMode**
        消息的发送模式：persistent或nonpersistent。前者表示消息在被消费之前，如果JMS提供者DOWN了，重新启动后消息仍然存在。后者在这种情况下表示消息会被丢失。可以通过下面的方式设置：
       Producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

​       **JMSTimestamp**
​       当调用send()方法的时候，JMSTimestamp会被自动设置为当前事件。可以通过下面方式得到这个值：
​       long timestamp = message.getJMSTimestamp();

　　

JMSExpiration 

​       表示一个消息的有效期。只有在这个有效期内，消息消费者才可以消费这个消息。默认值为0，表示消息永不过期。可以通过下面的方式设置：

​       producer.setTimeToLive(3600000); //有效期1小时 （1000毫秒 * 60秒 * 60分）

　　

JMSPriority 

​       消息的优先级。0-4为正常的优先级，5-9为高优先级。可以通过下面方式设置：

​       producer.setPriority(9);

　　

JMSMessageID 

​       一个字符串用来唯一标示一个消息。

　　

JMSReplyTo 

​       有时消息生产者希望消费者回复一个消息，JMSReplyTo为一个Destination，表示需要回复的目的地。当然消费者可以不理会它。

　　

JMSCorrelationID 

​       通常用来关联多个Message。例如需要回复一个消息，可以把JMSCorrelationID设置为所收到的消息的JMSMessageID。

　　

JMSType 

​       表示消息体的结构，和JMS提供者有关。

　　

JMSRedelivered 

​       如果这个值为true，表示消息是被重新发送了。因为有时消费者没有确认他已经收到消息或者JMS提供者不确定消费者是否已经收到。

​    除了Header，消息发送者可以添加一些属性(Properties)。这些属性可以是应用自定义的属性，JMS定义的属性和JMS提供者定义的属性。我们通常只适用自定义的属性。

​    后面会讲到这些Header和属性的用法。

# 7. 深入掌握JMS（七）：DeliveryMode例子

在下面的例子中，分别发送一个Persistent和nonpersistent的消息，然后关闭退出JMS。 

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class DeliveryModeSendTest {

​    public static void main(String[] args) throws Exception {
​        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
​    
​        Connection connection = factory.createConnection();
​        connection.start();
​        
​        Queue queue = new ActiveMQQueue("testQueue");
​        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
​                
​        MessageProducer producer = session.createProducer(queue);
​        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
​        producer.send(session.createTextMessage("A persistent Message"));
​        
​        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
​        producer.send(session.createTextMessage("A non persistent Message"));
​        
​        System.out.println("Send messages sucessfully!");
​    }
}

​    运行上面的程序，当输出“Send messages sucessfully!”时，说明两个消息都已经发送成功，然后我们结束它，来停止JMS Provider。

​    接下来我们重新启动JMS Provicer，然后添加一个消费者：

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class DeliveryModeReceiveTest {

​    public static void main(String[] args) throws Exception {
​        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
​    
​        Connection connection = factory.createConnection();
​        connection.start();
​        
​        Queue queue = new ActiveMQQueue("testQueue");
​        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
​        
​        MessageConsumer comsumer = session.createConsumer(queue);
​        comsumer.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println("Consumer get " + ((TextMessage)m).getText());
​                } catch (JMSException e) {
​                    e.printStackTrace();
​                }
​            }
​        });
​    }
}

运行上面的程序，可以得到下面的输出结果：

Consumer get A persistent Message

可以看出消息消费者只接收到一个消息，它是一个Persistent的消息。而刚才发送的non persistent消息已经丢失了。

另外, 如果发送一个non persistent消息, 而刚好这个时候没有消费者在监听, 这个消息也会丢失.

# 8. 深入掌握JMS（八）：JMSReplyTo

 在下面的例子中，首先创建两个Queue，发送者给一个Queue发送，接收者接收到消息之后给另一个Queue回复一个Message，然后再创建一个消费者来接受所回复的消息。

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class MessageSendReceiveAndReply {

​    public static void main(String[] args) throws Exception {
​        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
​    
​        Connection connection = factory.createConnection();
​        connection.start();
​        
​        //消息发送到这个Queue
​        Queue queue = new ActiveMQQueue("testQueue");

​        //消息回复到这个Queue
​        Queue replyQueue = new ActiveMQQueue("replyQueue");
​        
​        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

​        //创建一个消息，并设置它的JMSReplyTo为replyQueue。
​        Message message = session.createTextMessage("Andy");
​        message.setJMSReplyTo(replyQueue);
​        
​        MessageProducer producer = session.createProducer(queue);
​        producer.send(message);
​            
​        //消息的接收者
​        MessageConsumer comsumer = session.createConsumer(queue);
​        comsumer.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    //创建一个新的MessageProducer来发送一个回复消息。
​                    MessageProducer producer = session.createProducer(m.getJMSReplyTo());
​                    producer.send(session.createTextMessage("Hello " + ((TextMessage) m).getText()));
​                } catch (JMSException e1) {
​                    e1.printStackTrace();
​                }
​            }
​            
​        });
​        
​        //这个接收者用来接收回复的消息
​        MessageConsumer comsumer2 = session.createConsumer(replyQueue);
​        comsumer2.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println(((TextMessage) m).getText());
​                } catch (JMSException e) {
​                    e.printStackTrace();
​                }
​            }
​        });
​    }

}

​    首先消息生产者发送一个消息，内容为“Andy”， 然后消费者收到这个消息之后根据消息的JMSReplyTo，回复一个消息，内容为“Hello Andy‘。 最后在回复的Queue上创建一个接收回复消息的消费者，它输出所回复的内容。

​    运行上面的程序，可以得到下面的输出结果：

Hello Andy

# 9. 深入掌握JMS（九）：Selector
前面的例子中创建一个消息消费者使用的是:
          sesssion.createConsumer(destination)
  另外，还提供了另一种方式：
    sesssion.createConsumer(destination, selector)
  这里selector是一个字符串，用来过滤消息。也就是说，这种方式可以创建一个可以只接收特定消息的一个消费者。Selector的格式是类似于SQL-92的一种语法。可以用来比较消息头信息和属性。

  下面的例子中，创建两个消费者，共同监听同一个Queue，但是它们的Selector不同，然后创建一个消息生产者，来发送多个消息。

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JMSSelectorTest {

​    public static void main(String[] args) throws Exception {
​        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

​        Connection connection = factory.createConnection();
​        connection.start();
​       
​        Queue queue = new ActiveMQQueue("testQueue");
​       
​        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
​       
​        MessageConsumer comsumerA = session.createConsumer(queue, "receiver = 'A'");
​        comsumerA.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println("ConsumerA get " + ((TextMessage) m).getText());
​                } catch (JMSException e1) { }
​            }
​        });
​       
​        MessageConsumer comsumerB = session.createConsumer(queue, "receiver = 'B'");
​        comsumerB.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println("ConsumerB get " + ((TextMessage) m).getText());
​                } catch (JMSException e) { }
​            }
​        });
​       
​        MessageProducer producer = session.createProducer(queue);
​        for(int i=0; i<10; i++) {
​            String receiver = (i%3 == 0 ? "A" : "B");
​            TextMessage message = session.createTextMessage("Message" + i + ", receiver:" + receiver);
​            message.setStringProperty("receiver", receiver);
​            producer.send(message );
​        }
​    }
}

结果如下：
ConsumerA get Message0, receiver:A
ConsumerB get Message1, receiver:B
ConsumerB get Message2, receiver:B
ConsumerA get Message3, receiver:A
ConsumerB get Message4, receiver:B
ConsumerB get Message5, receiver:B
ConsumerA get Message6, receiver:A
ConsumerB get Message7, receiver:B
ConsumerB get Message8, receiver:B
ConsumerA get Message9, receiver:A

可以看出，消息消费者只会取走它自己感兴趣的消息。

# 10 .[深入掌握JMS（十）:JMSCorrelationID&Selector](http://%20http//www.360doc.com/content/090712/20/18042_4241252.html)

# 11. [深入掌握JMS（十一）:TemporaryQueue&TemporaryTopic](http://www.360doc.com/content/090712/21/18042_4241975.html)

TemporaryQueue和TemporaryTopic，从字面上就可以看出它们是“临时”的目的地。可以通过Session来创建，例如：
    TemporaryQueue replyQueue = session.createTemporaryQueue();
    
    虽然它们是由Session来创建的，但是它们的生命周期确实整个Connection。如果在一个Connection上创建了两个Session，则 一个Session创建的TemporaryQueue或TemporaryTopic也可以被另一个Session访问。那如果这两个Session是 由不同的Connection创建，则一个Session创建的TemporaryQueue不可以被另一个Session访问。
    另外，它们的主要作用就是用来指定回复目的地， 即作为JMSReplyTo。
    在下面的例子中，先创建一个Connection，然后创建两个Session，其中一个Session创建了一个TemporaryQueue，另一个Session在这个TemporaryQueue上读取消息。
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class TemporaryQueueTest {

​    public static void main(String[] args) throws Exception {
​        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
​        Connection connection = factory.createConnection();
​        connection.start();

​        Queue queue = new ActiveMQQueue("testQueue2");
​        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
​        //使用session创建一个TemporaryQueue。
​        TemporaryQueue replyQueue = session.createTemporaryQueue();
​       
​        //接收消息，并回复到指定的Queue中（即replyQueue）
​        MessageConsumer comsumer = session.createConsumer(queue);
​        comsumer.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println("Get Message: " + ((TextMessage)m).getText());
​                    MessageProducer producer = session.createProducer(m.getJMSReplyTo());
​                    producer.send(session.createTextMessage("ReplyMessage"));
​                } catch (JMSException e) { }
​            }
​        });
​       
​        //使用同一个Connection创建另一个Session，来读取replyQueue上的消息。
​        Session session2 = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
​        MessageConsumer replyComsumer = session2.createConsumer(replyQueue);
​        replyComsumer.setMessageListener(new MessageListener(){
​            public void onMessage(Message m) {
​                try {
​                    System.out.println("Get reply: " + ((TextMessage)m).getText());
​                } catch (JMSException e) { }
​            }
​        });
​       
​        MessageProducer producer = session.createProducer(queue);
​        TextMessage message = session.createTextMessage("SimpleMessage");
​        message.setJMSReplyTo(replyQueue);
​        producer.send(message);
​    }
}

运行结果为：
Get Message: SimpleMessage
Get reply: ReplyMessage

如果将：
 Session session2 = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
更改为：
 Connection connection2 = factory.createConnection();
 Session session2 = connection2.createSession(true, Session.AUTO_ACKNOWLEDGE);
就会得到类似于下面的异常：
Exception in thread "main" javax.jms.InvalidDestinationException: Cannot use a Temporary destination from another Connection。

# 12. [深入掌握JMS（十二）:MDB](http://www.360doc.com/content/090712/21/18042_4242116.html)

在EJB3中，一个MDB（消息驱动Bean）就是一个实现了MessageListener接口的POJO。下面就是一个简单的MDB。
@MessageDriven(activationConfig={
        @ActivationConfigProperty(propertyName="destinationType",
                propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination",
                propertyValue="queue/testQueue")})
public class SimpleMDB implements MessageListener {

    public void onMessage(Message message) {
        try {
            System.out.println("Receive Message : " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

它要求必须标注为@MessageDriven。它所监听Destination通过标注属性来注入。

下面是一个发送消息的StatelessBean：
@Remote
public interface IMessageSender {
    public void sendMessage(String content) throws Exception;
}


@Stateless
@Remote
public class MessageSender implements IMessageSender {
    @Resource(mappedName="ConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName="queue/testQueue")
    private Queue queue;

   

    public void sendMessage(String content) throws Exception {
        Connection cn = factory.createConnection();
       
        Session session = cn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage(content));
    }
}
这个EJB只有一个方法SendMessage。ConnectionFactory和Queue通过标注注入。

接下来是客户端：
public class MessageSenderClient {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        props.setProperty(Context.PROVIDER_URL, "localhost:2099");
        Context context = new InitialContext(props);
        IMessageSender messageSender = (IMessageSender) context.lookup("MessageSender/remote");
        messageSender.sendMessage("Hello");
    }
}
它通过JNDI查找到上面的EJB，然后调用sengMessage.

































