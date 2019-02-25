package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品分类service
 */
public interface ItemCatService {
    /**
     * 查询商品分类树形结构
     * @param parentId 当前节点父节点ID
     * @return
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
