package com.taotao.common.util;

import java.util.Random;

/**
 * Created by admin on 2017/12/24.
 * 各种id生成策略
 */
public class IDUtils {

    /**
     * 图片名生成
     * @return
     */
    public static String genImageName() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        //加上3位随机数
        int end3 = random.nextInt(999);
        //不足3位前补0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 商品ID生成
     * @return
     */
    public static long getItemId() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end2 = random.nextInt(99);
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }

}
