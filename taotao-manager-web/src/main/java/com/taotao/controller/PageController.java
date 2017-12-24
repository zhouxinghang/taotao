package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by admin on 2017/12/23.
 */

@Controller
public class PageController {
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
