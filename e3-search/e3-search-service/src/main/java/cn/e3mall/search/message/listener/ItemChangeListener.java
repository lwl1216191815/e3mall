package cn.e3mall.search.message.listener;

import cn.e3mall.search.service.SearchService;
import cn.e3mall.search.service.impl.SearchItemServiceImpl;
import cn.e3mall.search.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemChangeListener implements MessageListener {
    @Autowired
    private SearchItemServiceImpl searchItemServiceImpl;
    @Override
    public void onMessage(Message message) {
        try{
            TextMessage textMessage = null;
            Long itemId = null;
            if(message instanceof TextMessage){
                textMessage = (TextMessage) message;
                itemId = Long.valueOf(textMessage.getText());
            }
            searchItemServiceImpl.addDocument(itemId);
        }  catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
