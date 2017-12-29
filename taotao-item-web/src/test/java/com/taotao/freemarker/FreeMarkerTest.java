package com.taotao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by admin on 2017/12/29.
 */
public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        //1创建一个模板文件
        //2创建Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3设置模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("F:\\workspace\\taotao-parent\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4设置字符集
        configuration.setDefaultEncoding("utf-8");
        //5使用Configuration对象加载一个模板文件
        Template template = configuration.getTemplate("student.ftl");
        //6创建一个数据集，可以是pojo，也可以是map，推荐map
        Map data = new HashMap<>();
        data.put("hello", "hello FreeMarker");
        Student student = new Student(1, "小米", 11, "北京昌平回龙观");
        data.put("student", student);
        List<Student> stuList = new ArrayList<>();
        stuList.add(new Student(1, "小米", 11, "北京昌平回龙观"));
        stuList.add(new Student(2, "小米2", 12, "北京昌平回龙观"));
        stuList.add(new Student(3, "小米3", 13, "北京昌平回龙观"));
        stuList.add(new Student(4, "小米4", 14, "北京昌平回龙观"));
        stuList.add(new Student(5, "小米5", 15, "北京昌平回龙观"));
        stuList.add(new Student(6, "小米6", 16, "北京昌平回龙观"));
        stuList.add(new Student(7, "小米7", 17, "北京昌平回龙观"));
        data.put("stuList", stuList);
        //日期类型的处理
        data.put("date", new Date());
        data.put("val","123456");
        //7创建Writer对象，制定输出文件
        Writer out= new FileWriter(new File("F:/freemarker.html"));
        //8使用模板对象的process方法输出文件
        template.process(data, out);
        //9关闭流
        out.close();
    }
}
