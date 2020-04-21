package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.service.ItemDescService;

@RestController
public class ItemDescController {
	@Autowired
	private ItemDescService itemDescService;
	/**
	 * 根据商品ID获取商品详情
	 * @param itemId
	 * @return
	 * @throws ItemDescNotFoundException
	 */
	@RequestMapping(value="rest/item/query/item/desc/{itemId}",method=RequestMethod.GET)
	public E3Result getDescByItemId(@PathVariable long itemId) throws ItemDescNotFoundException {
		E3Result result = itemDescService.getDescByItemId(itemId);
		return result;
	}
}
