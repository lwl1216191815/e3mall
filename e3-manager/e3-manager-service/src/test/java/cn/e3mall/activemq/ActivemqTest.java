package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * activemq测试
 */
public class ActivemqTest {

//    第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
//    第二步：使用ConnectionFactory对象创建一个Connection对象。
//    第三步：开启连接，调用Connection对象的start方法。
//    第四步：使用Connection对象创建一个Session对象。
//    第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
//    第六步：使用Session对象创建一个Producer对象。
//    第七步：创建一个Message对象，创建一个TextMessage对象。
//    第八步：使用Producer对象发送消息。
//    第九步：关闭资源。

    /**
     * 点到点形式，发送消息
     * @throws Exception
     */
    @Test
    public void testQueueProducer() throws Exception{
        String url = "tcp://47.93.38.3:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        connection.start();
        //第一个参数的意思是是否开启事务（消息没发出去就重新发），一般不开启事务。如果开启（true），第二个参数没有意义
        //第二个参数的意思是应答模式（手动或者自动），一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue testQueue = session.createQueue("testQueue");
        MessageProducer producer = session.createProducer(testQueue);
//        TextMessage message = new ActiveMQTextMessage();
//        message.setText("activeMQ queue测试消息");
        TextMessage textMessage = session.createTextMessage("activeMQ queue测试消息");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

//    消费者：接收消息。
//    第一步：创建一个ConnectionFactory对象。
//    第二步：从ConnectionFactory对象中获得一个Connection对象。
//    第三步：开启连接。调用Connection对象的start方法。
//    第四步：使用Connection对象创建一个Session对象。
//    第五步：使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
//    第六步：使用Session对象创建一个Consumer对象。
//    第七步：接收消息。
//    第八步：打印消息。
//    第九步：关闭资源

    @Test
    public void testQueueCustomer() throws Exception{
        String url = "tcp://47.93.38.3:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue testQueue = session.createQueue("testQueue");
        MessageConsumer consumer = session.createConsumer(testQueue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待接收消息
        System.in.read();
        consumer.close();
        session.close();
        consumer.close();
    }
}
