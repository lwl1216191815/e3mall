package cn.e3mall;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class framework {
//    第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
//    第二步：设置模板文件所在的路径。
//    第三步：设置模板文件使用的字符集。一般就是utf-8.
//    第四步：加载一个模板，创建一个模板对象。
//    第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
//    第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
//    第七步：调用模板对象的process方法输出文件。
//    第八步：关闭流。
    @Test
    public void generateFile() throws Exception{
        Configuration configuration = new Configuration(Configuration.getVersion());
        File ftlFile = new File("E:/java/2020project/e3-idea/e3-item-web/src/main/webapp/WEB-INF/ftl");
        configuration.setDirectoryForTemplateLoading(ftlFile);
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("hello.ftl");
        Map<String,Object> dataModal = new HashMap<>();
        dataModal.put("hello","前后端都分离数百年了，你到底要闹哪样");
        File tempFile = new File("D:/BaiduNetdiskDownload/it黑马网课/80项目二：宜立方商城（80-93天）/temp/hello.html");
        Writer out = new FileWriter(tempFile);
        template.process(dataModal,out);
        out.close();
    }

}
