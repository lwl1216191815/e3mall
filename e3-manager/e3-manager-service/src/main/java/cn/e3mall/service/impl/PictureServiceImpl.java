package cn.e3mall.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.util.CommonUtils;
import cn.e3mall.common.util.FileUtils;
import cn.e3mall.mapper.TbFileMapper;
import cn.e3mall.pojo.TbFile;
import cn.e3mall.pojo.TbFileExample;
import cn.e3mall.pojo.TbFileExample.Criteria;
import cn.e3mall.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
	@Value("${file.uploadPath}")
	private String configSaveFilePath;
	@Autowired
	private TbFileMapper fileMapper;

	@Override
	public TbFile saveFile(TbFile tbFile) {
		tbFile.setId(CommonUtils.getId());
		fileMapper.insert(tbFile);
		return tbFile;
	}

	@Override
	public File uploadFile(String originalFilename) {
		String savePath = FileUtils.getSavePath(configSaveFilePath);
		String saveName = FileUtils.getSaveName();
		String fileType = FileUtils.getFileType(originalFilename, true);
		File filePath = new File(savePath, saveName + fileType);
		// 如果文件目录不存在，就创建
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		return filePath;
	}

	@Override
	public TbFile saveFile(File file,String originalFilename) {
		TbFile tbFile = new TbFile();
		tbFile.setFileName(originalFilename);
		tbFile.setFileSize(file.length());
		tbFile.setFileType(FileUtils.getFileType(originalFilename, false));
		tbFile.setSaveName(file.getName());
		tbFile.setSavePath(file.getAbsolutePath());
		tbFile.setUploadTime(new Date());
		return saveFile(tbFile);
	}

	@Override
	public File getFileBySaveName(String saveName) {
		if(StringUtils.isNoneBlank(saveName)) {
			TbFileExample example = new TbFileExample();
			Criteria criteria = example.createCriteria();
			criteria.andSaveNameEqualTo(saveName);
			List<TbFile> fileList = fileMapper.selectByExample(example);
			if(!fileList.isEmpty() && fileList.size() == 1) {
				TbFile tbFile = fileList.get(0);
				File file = new File(tbFile.getSavePath());
				return file;
			}
		}
		throw new NullPointerException("saveName不能为空");
	}

}
