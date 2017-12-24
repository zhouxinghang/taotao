package com.taotao.service;

import java.io.InputStream;

/**
 * Created by admin on 2017/12/24.
 */
public interface PicService {

    String uploadFile(InputStream inputStream, String fileName);
}
