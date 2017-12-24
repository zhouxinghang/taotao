package com.taotao.controller;

import com.taotao.common.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by admin on 2017/12/24.
 */

@Controller
public class PicController {

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile) {
        return JsonUtils.objectToJson("ok");
    }

}
