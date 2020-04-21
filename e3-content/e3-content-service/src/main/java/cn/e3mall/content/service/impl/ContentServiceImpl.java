package cn.e3mall.content.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.mapper.TbFileMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbFile;
import cn.e3mall.pojo.TbFileExample;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private TbFileMapper fileMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_KEY;

	@Override
	public EasyUIDataGridResult getContentList(long categoryId, Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		List<TbContent> list = getContentList(categoryId);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult(pageInfo.getTotal(),list);
		return result;
	}

	@Override
	public E3Result addContent(TbContent entity) {
		entity.setUpdated(new Date());
		entity.setCreated(entity.getUpdated());
		contentMapper.insert(entity);
		return E3Result.ok();
	}

	@Override
	public E3Result updateData(TbContent entity, boolean updateAll) {
		if(updateAll) {
			return updataData(entity);
		}
		if(entity != null && entity.getId() != null) {
			contentMapper.updateByPrimaryKeySelective(entity);
			return E3Result.ok();
		}
		throw new NullPointerException("content的id不能为空");
	}
	
	private E3Result updataData(TbContent entity) {
		if(entity != null && entity.getId() != null) {
			contentMapper.updateByPrimaryKey(entity);
			return E3Result.ok();
		}
		throw new NullPointerException("content的id不能为空");
	}
	@Override
	public E3Result deleteDataByIds(List<Long> contentIds) {
		if(contentIds != null && !contentIds.isEmpty()) {
			TbContentExample example = new TbContentExample();
			example.createCriteria().andIdIn(contentIds);
			List<TbContent> contentList = contentMapper.selectByExample(example);
			contentMapper.deleteByExample(example);
			//要删除文件了
			List<String> saveNames = new ArrayList<String>(contentList.size() * 2);
			for(TbContent content : contentList) {
				if(StringUtils.isNotBlank(content.getPic())) {
					String[] picArr = content.getPic().split("=");
					if(picArr.length > 1) {
						saveNames.add(picArr[1]);
					}
				}
				if(StringUtils.isNotBlank(content.getPic2())) {
					String[] pic2Arr = content.getPic2().split("=");
					if(pic2Arr.length > 1) {
						saveNames.add(pic2Arr[1]);
					}
				}
			}
			if(!saveNames.isEmpty()) {
				TbFileExample fileExample = new TbFileExample();
				fileExample.createCriteria().andSaveNameIn(saveNames);
				List<TbFile> fileList = fileMapper.selectByExample(fileExample);
				fileMapper.deleteByExample(fileExample);
				for(TbFile img : fileList) {
					File file = new File(img.getSavePath());
					if(file.exists()) {
						file.delete();
					}
				}
			}
			return E3Result.ok();
		}
		throw new NullPointerException("content的id不能为空");
	}
	@Override
	public E3Result deleteDataByIds(String ids,String splitStr) {
		if(StringUtils.isBlank(splitStr)) {
			splitStr = ",";
		}
		if(StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(splitStr);
			List<Long> contentIds = new ArrayList<Long>(idArray.length);
			for(String id : idArray) {
				contentIds.add(Long.valueOf(id));
			}
			return deleteDataByIds(contentIds);
		}
		throw new NullPointerException("ids不能为空");
	}

	@Override
	public List<TbContent> getContentList(long categoryId) {
		String json = jedisClient.hget(CONTENT_KEY, String.valueOf(categoryId));
		if(StringUtils.isNotBlank(json)) {
			List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
			return list;
		}
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		jedisClient.hset(CONTENT_KEY, String.valueOf(categoryId), JsonUtils.objectToJson(list));
		return list;
	}
}
