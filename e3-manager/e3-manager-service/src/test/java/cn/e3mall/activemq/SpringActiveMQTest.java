package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * 测试Spring的ActiveMQ
 */
public class SpringActiveMQTest {

    @Test
    public void testSpringActiveMq() throws Exception {
        //1 初始化Spring容器
        String classPath = "classpath:spring/applicationContext-activemq.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(classPath);
        // 从容器中获取JMSTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 从容器中获取一个Destination对象
        Queue queue = (Queue)applicationContext.getBean("queueDestination");
        // 使用JMSTemplate发送消息，需要知道Destination
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("spring activemq");
                return message;
            }
        });
    }
}
