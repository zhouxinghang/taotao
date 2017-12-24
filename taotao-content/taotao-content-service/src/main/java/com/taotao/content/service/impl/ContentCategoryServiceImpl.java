package com.taotao.content.service.impl;

import com.sun.security.auth.NTSidUserPrincipal;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.content.service.ContentCategoryService
        ;
import com.taotao.dao.TbContentCategoryDao;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/12/24.
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        //设置查询条件
        TbContentCategoryQuery query = new TbContentCategoryQuery();
        TbContentCategoryQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> contentCategories = tbContentCategoryDao.selectByExample(query);
        //处理查询结果
        List<EasyUITreeNode> treeNodes = new ArrayList<>();
        for(TbContentCategory contentCategory : contentCategories) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent()? "closed" : "open");
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        //contentCategory.setId(IDUtils.getItemId());
        contentCategory.setName(name);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setIsParent(false);
        //排序。默认为1
        contentCategory.setSortOrder(1);
        //状态，1 正常  2 删除
        contentCategory.setStatus(1);
        tbContentCategoryDao.insert(contentCategory);

        //更新父节点状态
        TbContentCategory parent = tbContentCategoryDao.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()) {
            parent.setIsParent(true);
            tbContentCategoryDao.updateByPrimaryKey(parent);
        }

        return TaotaoResult.ok(contentCategory);
    }
}
