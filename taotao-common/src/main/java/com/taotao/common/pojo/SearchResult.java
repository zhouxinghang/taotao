package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/12/26.
 */
public class SearchResult implements Serializable{

    private long totalPages;
    private long recordCount;
    private List<SearchItem> itemList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
