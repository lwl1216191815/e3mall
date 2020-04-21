package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;
/**
 * 商品类目controller
 * @author Dragon
 *
 */
@RestController
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	/**
	 * 根据上级id查询类目，并且返回树形节点
	 * @param parentId
	 * @return
	 */
	@RequestMapping("item/cat/list")
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EasyUITreeNode> catList = itemCatService.getCatList(parentId);
		return catList;
	}
}
