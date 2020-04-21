package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>(list.size());
		for(TbContentCategory contentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent() ? "closed":"open");
			result.add(node);
		}
		return result;
	}
	@Override
	public E3Result addContentCategory(long parentId, String name) {
		if(StringUtils.isNotBlank(name)) {
			TbContentCategory entity = new TbContentCategory();
			entity.setParentId(parentId);
			entity.setName(name);
			entity.setIsParent(false);
			entity.setSortOrder(1);
			entity.setCreated(new Date());
			entity.setUpdated(entity.getCreated());
			entity.setStatus(1);//1正常2删除
			contentCategoryMapper.insert(entity);
			TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
			if(!parentNode.getIsParent()) {
				parentNode.setIsParent(true);
				contentCategoryMapper.updateByPrimaryKey(parentNode);
			}
			return E3Result.ok(entity);
		}
		throw new NullPointerException("name不允许为空");
	}
	/**
	 * 更新数据(全量更新)
	 * @param entity 存储更新的实体
	 * @return
	 */
	private E3Result update(TbContentCategory entity) {
		if(entity != null && entity.getId() != null) {
			contentCategoryMapper.updateByPrimaryKey(entity);
			return E3Result.ok();
		}
		throw new NullPointerException("TbContentCategory的id不能为空");
	}
	@Override
	public E3Result update(TbContentCategory entity, boolean updateAll) {
		if(updateAll) {
			return update(entity);
		}
		if(entity != null && entity.getId() != null) {
			contentCategoryMapper.updateByPrimaryKeySelective(entity);
			return E3Result.ok();
		}
		throw new NullPointerException("TbContentCategory的id不能为空");
	}
	@Override
	public E3Result delete(long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		if(contentCategory.getIsParent()) {
			return E3Result.error(250, "父节点不允许删除");
		}
		//执行删除
		contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
		//判断是否还有兄弟节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(contentCategory.getParentId());
		List<TbContentCategory> brotherList = contentCategoryMapper.selectByExample(example);
		//如果没有兄弟节点，删除后将父节点的isParent改为false
		if(brotherList == null || brotherList.isEmpty()) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(parent);
		}
		return E3Result.ok();
	}

}
