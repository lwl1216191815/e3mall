package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.service.ItemParamItemService;

@RestController
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	public E3Result getParamDataByItemId(@PathVariable long itemId) {
		E3Result result = itemParamItemService.getItemParamItemByItemId(itemId);
		return result;
	}
}
