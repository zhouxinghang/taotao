package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by admin on 2017/12/24.
 */
public interface ContentService {
    TaotaoResult addContent(TbContent content);

    List<TbContent> getContentByCid(long cid);
}
