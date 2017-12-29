package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by admin on 2017/12/26.
 */
public interface SearchItemMapper {
    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);
}
