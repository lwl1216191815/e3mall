package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	/**
	 * 获取商品类目树形结构
	 * @return
	 */
	List<EasyUITreeNode> getCatList(long parentId);
}
