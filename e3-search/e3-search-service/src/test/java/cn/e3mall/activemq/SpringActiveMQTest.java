package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ����activemq����Ϣ
 */
public class SpringActiveMQTest {

    @Test
    public void testQueueConsumer() throws Exception{
        //1 ��ʼ��Spring����
        String classPath = "classpath:applicationContext-activemq.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(classPath);
        System.in.read();
    }
}
