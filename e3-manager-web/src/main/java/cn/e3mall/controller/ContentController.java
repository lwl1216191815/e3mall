package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@RestController
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	public EasyUIDataGridResult getContentList(long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	@RequestMapping("/content/save")
	public E3Result saveContent(TbContent content) {
		E3Result result = contentService.addContent(content);
		return result;
	}
	@RequestMapping("/content/edit")
	public E3Result updateData(TbContent content) {
		E3Result result = contentService.updateData(content, false);
		return result;
	}
	@RequestMapping("/content/delete")
	public E3Result deleteData(String ids) {
		E3Result result = contentService.deleteDataByIds(ids, ",");
		return result;
	}
}
