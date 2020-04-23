package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 接收activemq的消息
 */
public class SpringActiveMQTest {

    @Test
    public void testQueueConsumer() throws Exception{
        //1 初始化Spring容器
        String classPath = "classpath:applicationContext-activemq.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(classPath);
        System.in.read();
    }
}
