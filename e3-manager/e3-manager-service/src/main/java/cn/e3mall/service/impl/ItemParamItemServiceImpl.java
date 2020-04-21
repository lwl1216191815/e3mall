package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbItemParamItemMapper;
import cn.e3mall.pojo.TbItemParamItem;
import cn.e3mall.pojo.TbItemParamItemExample;
import cn.e3mall.service.ItemParamItemService;
/**
 * 
 * @author Dragon
 *
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Override
	public E3Result getItemParamItemByItemId(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		return list.isEmpty() ? E3Result.ok() : E3Result.ok(list.get(0));
	}

}
