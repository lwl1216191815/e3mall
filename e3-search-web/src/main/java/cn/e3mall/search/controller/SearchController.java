package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer pageRows;

    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        if(StringUtils.isNotBlank(keyword)){
            keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
        }
        SearchResult result = searchService.search(keyword, page, pageRows);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",result.getTotalPage());
        model.addAttribute("recordCount",result.getRecordCount());
        model.addAttribute("page",page);
        model.addAttribute("itemList",result.getItemList());
        return "search";
    }
}
