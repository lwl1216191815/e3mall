package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbContentCategory;

/**
 * 商品内容分类
 * @author Dragon
 *
 */
public interface ContentCategoryService {
	/**
	 * 根据父级ID获取商品内容
	 * @param parentId 父级ID
	 * @return
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	/**
	 * 添加内容分类
	 * @param parentId 父级ID
	 * @param name 分类名称
	 * @return
	 */
	E3Result addContentCategory(long parentId,String name);
	/**
	 * 更新数据
	 * @param entity 存储更新的实体
	 * @param updateAll true的时候为全量更新，false为增量更新
	 * @return
	 */
	E3Result update(TbContentCategory entity,boolean updateAll);
	/**
	 * 根据ID删除数据，如果是父节点就不允许删除
	 * @param id
	 * @return
	 */
	E3Result delete(long id);
}
