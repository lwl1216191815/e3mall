package cn.e3mall.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.CommonUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbFileMapper;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbFile;
import cn.e3mall.pojo.TbFileExample;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemDescExample.Criteria;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbFileMapper fileMapper;
	
	
	/**
	 * 根据商品编号获取商品信息
	 */
	@Override
	public TbItem getItemById(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		//执行查询
		List<TbItem> itemList = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(itemList);
		result.setTotal(Long.valueOf(pageInfo.getTotal()).intValue());
		return result;
	}
	@Override
	public E3Result addItem(TbItem item, String itemDesc) {
		item.setId(CommonUtils.getId());
		item.setStatus((byte) 1);//1 正常 2 下架 3 删除
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		TbItemDesc desc = new TbItemDesc();
		desc.setItemId(item.getId());
		desc.setCreated(item.getCreated());
		desc.setUpdated(item.getCreated());
		desc.setItemDesc(itemDesc);
		itemDescMapper.insert(desc);
		return E3Result.ok();
	}
	@Override
	public E3Result editItem(TbItem item, String itemDesc) {
		item.setUpdated(new Date());
		item.setStatus((byte) 1);//1 正常 2 下架 3 删除
		itemMapper.updateByPrimaryKeySelective(item);
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(item.getId());
		TbItemDesc record = new TbItemDesc();
		record.setUpdated(item.getUpdated());
		record.setItemDesc(itemDesc);
		itemDescMapper.updateByExampleSelective(record , example );
		return E3Result.ok();
	}
	@Override
	public E3Result deleteItemByIds(List<Long> itemIds) {
		if(itemIds != null && !itemIds.isEmpty()) {
			TbItemExample example = new TbItemExample();
			example.createCriteria().andIdIn(itemIds);
			List<TbItem> delList = itemMapper.selectByExample(example);
			//先删除商品记录
			itemMapper.deleteByExample(example);
			TbFileExample fielExample = new TbFileExample();
			List<String> values = new ArrayList<String>();
			for(TbItem item : delList) {
				String imageStr = item.getImage();
				if(StringUtils.isNotBlank(imageStr)) {
					String[] urls = imageStr.split(",");
					for(String url : urls) {
						String[] temp = url.split("=");
						if(temp.length > 1) {
							values.add(temp[1]);
						}
					}
				}
			}
			//再删除文件记录
			if(!values.isEmpty()) {
				fielExample.createCriteria().andSaveNameIn(values);
				List<TbFile> fileList = fileMapper.selectByExample(fielExample);
				for(TbFile img : fileList) {
					File file = new File(img.getSavePath());
					file.delete();
				}
				fileMapper.deleteByExample(fielExample);
			}
			return E3Result.ok();
		}
		throw new NullPointerException("itemIds不能为空");
	}
	@Override
	public E3Result instockItem(List<Long> itemIds) {
		if(itemIds != null && !itemIds.isEmpty()) {
			TbItemExample example = new TbItemExample();
			example.createCriteria().andIdIn(itemIds);
			TbItem record = new TbItem();
			record.setStatus((byte) 2);
			itemMapper.updateByExampleSelective(record , example );
			return E3Result.ok();
		}
		throw new NullPointerException("itemIds不能为空");
	}
	@Override
	public E3Result reshelfItem(List<Long> itemIds) {
		if(itemIds != null && !itemIds.isEmpty()) {
			TbItemExample example = new TbItemExample();
			example.createCriteria().andIdIn(itemIds);
			TbItem record = new TbItem();
			record.setStatus((byte) 1);
			itemMapper.updateByExampleSelective(record , example );
			return E3Result.ok();
		}
		throw new NullPointerException("itemIds不能为空");
	}

}
