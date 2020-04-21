package cn.e3mall.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件操作工具类
 * @author Dragon
 *
 */
public class FileUtils {
	/**
	 *  获取文件类型
	 * @param fileName 源文件名
	 * @param containPoint 是否包含点
	 * @return
	 */
	public static String getFileType(String fileName,boolean containPoint) {
		if(StringUtils.isNotBlank(fileName)) {
			int pointIndex = fileName.lastIndexOf(".");
			if(!containPoint) {
				pointIndex++;
			}
			return fileName.substring(pointIndex);
		}
		throw new NullPointerException("文件名不能为空");
	}
	/**
	 * 获取文件保存名称
	 * @return
	 */
	public static String getSaveName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}

	/**
	 * 获取文件保存路径
	 * @param configPath
	 * @return
	 */
	public static String getSavePath(String configPath) {
		StringBuilder result = new StringBuilder(configPath);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String todayStr = df.format(new Date());
		result.append(todayStr);
		return result.toString();
	}
	
//	public static void main(String[] args) {
//		File file = new File("D:/BaiduNetdiskDownload/self.docx");
//		System.out.println(file.getAbsolutePath());
//	}
}
