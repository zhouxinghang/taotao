package com.taotao.service.impl;

import com.taotao.common.util.FtpUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by admin on 2017/12/24.
 */

@Service
public class PicServiceImpl implements PicService {
    @Value("${manage.host}")
    private String host;
    @Value("${manage.username}")
    private String username;
    @Value("${manage.password}")
    private String password;
    @Value("${manage.port}")
    private int port;
    @Value("${manage.basePath}")
    private String basePath;
    @Value("${manage.filePath}")
    private String filePath;
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        String name = IDUtils.genImageName() + fileName.substring(fileName.lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, name, inputStream);
        if(result) {
            return "http://" + host + "/" + name;
        } else {
            return null;
        }
    }
}
