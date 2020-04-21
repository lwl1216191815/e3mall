package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.search.service.SearchItemService;

@RestController
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	
	
	@RequestMapping("/index/item/import")
	public E3Result importItems() {
		E3Result result = searchItemService.importItems();
		return result;
	}
}
