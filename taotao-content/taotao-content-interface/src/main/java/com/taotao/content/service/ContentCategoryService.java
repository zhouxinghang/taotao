package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by admin on 2017/12/24.
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    TaotaoResult addContentCategory(Long parentId, String name);
}
