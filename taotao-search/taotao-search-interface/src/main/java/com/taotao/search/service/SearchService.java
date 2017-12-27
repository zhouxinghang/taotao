package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * Created by admin on 2017/12/27.
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;
}
