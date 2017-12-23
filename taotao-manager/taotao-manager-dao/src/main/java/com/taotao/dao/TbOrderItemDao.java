package com.taotao.dao;

import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderItemQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbOrderItemDao {
    int countByExample(TbOrderItemQuery example);

    int deleteByExample(TbOrderItemQuery example);

    int deleteByPrimaryKey(String id);

    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    List<TbOrderItem> selectByExample(TbOrderItemQuery example);

    TbOrderItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemQuery example);

    int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemQuery example);

    int updateByPrimaryKeySelective(TbOrderItem record);

    int updateByPrimaryKey(TbOrderItem record);
}