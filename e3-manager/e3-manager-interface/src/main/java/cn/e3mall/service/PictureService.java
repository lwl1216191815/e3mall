package cn.e3mall.service;

import java.io.File;

import cn.e3mall.pojo.TbFile;

/**
 * 图片service
 * @author Dragon
 *
 */
public interface PictureService {
	/**
	 *  新增文件记录
	 * @param tbFile 文件记录对象
	 * @return
	 */
	TbFile saveFile(TbFile tbFile);

	/**
	 * 上传文件
	 * @param originalFilename 源文件名
	 * @return
	 */
	File uploadFile(String originalFilename);
	/**
	 * 保存文件
	 * @param file
	 * @return
	 */
	TbFile saveFile(File file, String originalFilename);
	/**
	 * 根据保存文件名获取文件
	 * @param saveName
	 * @return
	 */
	File getFileBySaveName(String saveName);
}
