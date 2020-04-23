package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HtmlGenerateController {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    public String generateHtml() throws IOException, TemplateException {
        // 1、从spring容器中获得FreeMarkerConfigurer对象。
        // 2、从FreeMarkerConfigurer对象中获得Configuration对象。
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // 3、使用Configuration对象获得Template对象。
        Template template = configuration.getTemplate("hello.ftl");
        // 4、创建数据集
        Map dataModel = new HashMap<>();
        dataModel.put("hello", "1000");
        // 5、创建输出文件的Writer对象。
        Writer out = new FileWriter(new File("D:/BaiduNetdiskDownload/it黑马网课/80项目二：宜立方商城（80-93天）/temp/spring-freemarker.html"));
        // 6、调用模板对象的process方法，生成文件。
        template.process(dataModel, out);
        // 7、关闭流。
        out.close();
        return "OK";

    }
}
