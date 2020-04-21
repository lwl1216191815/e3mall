package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemDescExample.Criteria;
import cn.e3mall.service.ItemDescService;
/**
 * 商品详情service
 * @author Dragon
 *
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public E3Result getDescByItemId(long itemId) throws ItemDescNotFoundException {
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
		if(!list.isEmpty() && list.size() == 1 && list.get(0).getItemId() == itemId) {
			return E3Result.ok(list.get(0));
		}
		throw new ItemDescNotFoundException("根据商品ID未找到合法的商品详情记录");
	}

}
