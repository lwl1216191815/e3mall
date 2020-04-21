package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.pojo.TbContentCategory;

@RestController
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/content/category/create")
	public E3Result addContentCategory(long parentId,String name) {
		E3Result result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/content/category/update")
	public E3Result rename(long id, String newName) {
		TbContentCategory entity = new TbContentCategory();
		entity.setId(id);
		entity.setName(newName);
		return contentCategoryService.update(entity, false);
	}
	
	@RequestMapping("/content/category/delete")
	public E3Result delete(long id) {
		E3Result result = contentCategoryService.delete(id);
		return result;
	}
}
