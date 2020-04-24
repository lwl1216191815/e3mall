package cn.e3mall.item.listeners;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.item.vo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听activemq，当添加一个商品的时候就生成一个html文件
 * 思路：1监听到商品添加，获取消息中的itemId
 * 2根据ItemId查询商品详情
 * 3 将商品详情添加到freemark中
 * 4 写freemark
 * 5 关闭资源
 */
public class HtmlGenerateListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${html.generate.path}")
    private String htmlPath;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String itemIdStr = textMessage.getText();
            //等待事务提交
            // TODO 完全可以在e3-manager-service增加一个AOP用来发送消息，提交事务之后再发消息就不用等事务提交了
            Thread.sleep(1000);
            TbItem tbItem = itemService.getItemById(Long.valueOf(itemIdStr));
            Item item = new Item(tbItem);
            E3Result result = itemDescService.getDescByItemId(Long.valueOf(itemIdStr));
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建一个数据集，把商品数据封装
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", result.getData());
            File floder = new File(htmlPath);
            if(!floder.exists()){
                floder.mkdirs();
            }
            Writer out = new FileWriter(new File(htmlPath + itemIdStr + ".html"));
            template.process(data,out);
            out.close();
        } catch (JMSException | ItemDescNotFoundException | InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
