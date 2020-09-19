package cn.e3mall.service.config.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * spring配置activemq
 */
@PropertySource({"classpath:conf/resource.properties"})
@Configuration
public class ActiveMQApplicationContextConfig {
    /**
     * 真正可以产生Connection对象的ConnectionFactory，由对应的JMS服务厂商提供
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL("tcp://47.93.38.3:61616");
        return activeMQConnectionFactory;
    }

    /**
     * spring用于管理真正的ConnectionFactory的ConnectionFactory
     * @return
     */
    @Bean
    public SingleConnectionFactory singleConnectionFactory(){
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        ActiveMQConnectionFactory activeMQConnectionFactory = activeMQConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        return singleConnectionFactory;
    }

    /**
     * Spring提供的JMS工具类，可以用来消息发送，接收
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        SingleConnectionFactory connectionFactory = singleConnectionFactory();
        /**
         * 这里对应的是Spring声明的connectionFactory
         */
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    /**
     * 队列目的地，点对点的
     * @return
     */
    @Bean
    public ActiveMQQueue activeMQQueue(){
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("spring-queue");
        return activeMQQueue;
    }

    /**
     * 标题目的地，一对多的
     * @return
     */
    @Bean
    public ActiveMQTopic activeMQTopic(){
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("item-change-topic");
        return activeMQTopic;
    }
}
