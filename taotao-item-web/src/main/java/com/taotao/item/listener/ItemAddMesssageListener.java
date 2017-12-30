package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/12/29.
 */
public class ItemAddMesssageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ItemAddMesssageListener.class);
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //从订阅消息中取出商品id
            TextMessage textMessage = (TextMessage) message;
            String strId = textMessage.getText();
            Long itemId = Long.parseLong(strId);
            logger.info("ItemaddMessageListener.onMessage.itemAddTopic.new item id:{}",strId);
            //等待事务提交
            Thread.sleep(1000);
            //根据id查询商品及商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //使用FreeMark生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //准备模板所需数据
            Map data = new HashMap<String, Object>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            //指定输出的文件目录和文件名
            Writer writer = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
            //生成静态页面
            template.process(data, writer);
        } catch (Exception e) {
            logger.error("ItemAddMessageListener.onMessage.ERROR:{}",e);
        }
    }
}
