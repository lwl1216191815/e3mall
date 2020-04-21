package cn.e3mall.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@RestController
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 根据ID获取商品
	 * 
	 * @param itemId 商品ID
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}

	/**
	 * 分页查询商品
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 * 添加商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/save")
	public E3Result addItem(TbItem item, String desc) {
		E3Result result = itemService.addItem(item, desc);
		return result;
	}

	/**
	 * 修改商品信息
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/update")
	public E3Result updateItem(TbItem item, String desc) {
		E3Result result = itemService.editItem(item, desc);
		return result;
	}
	/**
	 * 根据商品ID删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/item/delete")
	public E3Result deleteItemByIds(String ids) {
		String[] idsArray = ids.split(",");
		List<Long> itemIds = new ArrayList<Long>(idsArray.length);
		for (String itemId : idsArray) {
			itemIds.add(Long.valueOf(itemId));
		}
		E3Result e3Result = itemService.deleteItemByIds(itemIds);
		return e3Result;
	}
	
	@RequestMapping("/item/instock")
	public E3Result instockItems(String ids) {
		String[] idsArray = ids.split(",");
		List<Long> itemIds = new ArrayList<Long>(idsArray.length);
		for (String itemId : idsArray) {
			itemIds.add(Long.valueOf(itemId));
		}
		E3Result result = itemService.instockItem(itemIds);
		return result;
	}
	
	@RequestMapping("/item/reshelf")
	public E3Result reshelfItems(String ids) {
		String[] idsArray = ids.split(",");
		List<Long> itemIds = new ArrayList<Long>(idsArray.length);
		for (String itemId : idsArray) {
			itemIds.add(Long.valueOf(itemId));
		}
		E3Result result = itemService.reshelfItem(itemIds);
		return result;
	}

}
