package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 2017/12/27.
 * 搜索服务Controller
 */

@Controller
public class SearchController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;
    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        LOG.info("SearchController.search.info:[query:{}, page:{}]", queryString, page);
        //调用服务执行查询
        //把查询条件进行转码，解决get乱码问题
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        SearchResult result = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
        //将查询结果放入model
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);
        //返回逻辑视图
        return "search";
    }

}


