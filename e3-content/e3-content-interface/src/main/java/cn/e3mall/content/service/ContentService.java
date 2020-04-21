package cn.e3mall.content.service;
/**
 * 内容service
 * @author Dragon
 *
 */

import java.util.List;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
	/**
	 * 根据分类ID分页查询内容列表
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getContentList(long categoryId,Integer page, Integer rows);
	/**
	 * 根据分类ID查询内容列表
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentList(long categoryId);
	/**
	 * 添加内容
	 * @param entity
	 * @return
	 */
	E3Result addContent(TbContent entity);
	/**
	 * 更新数据
	 * @param entity 需要更新的实体
	 * @param updateAll 是否是全量更新
	 * @return
	 */
	E3Result updateData(TbContent entity,boolean updateAll);
	/**
	 * 根据ID删除数据
	 * @param contentIds
	 * @return
	 */
	E3Result deleteDataByIds(String ids,String splitStr);
	/**
	 * 根据ID删除数据
	 * @param contentIds
	 * @return
	 */
	E3Result deleteDataByIds(List<Long> contentIds);
}
