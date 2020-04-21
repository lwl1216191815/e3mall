package cn.e3mall.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.service.PictureService;
/**
 * 图片上传controller
 * @author Dragon
 *
 */
@RestController
public class PictureController {
	@Autowired
	private PictureService pictureService;
	/**
	 * 文件上传
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	public Map<String,Object> uploadPic(MultipartFile uploadFile,HttpServletRequest req){
		String fileName = uploadFile.getOriginalFilename();
		File file = pictureService.uploadFile(fileName);
		Map<String,Object> result = new HashMap<>();
		//写入文件
		try {
			uploadFile.transferTo(file);
			pictureService.saveFile(file,fileName);
			result.put("error",0);
			StringBuilder urlStr = new StringBuilder(req.getScheme());
			urlStr.append("://").append(req.getServerName()).append(":");
			urlStr.append(req.getServerPort()).append("/pic/scanFile?saveName="+file.getName());
			result.put("url", urlStr.toString());
			return result;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		result.put("error",1);
		result.put("message", "上传有问题");
		return result;
	}
	/**
	 * 
	 * @param response
	 * @param saveName
	 * @throws IOException
	 */
	@RequestMapping("/pic/scanFile")
	public void scanFile(HttpServletResponse response,String saveName) throws IOException {
		File file = pictureService.getFileBySaveName(saveName);
		if(file.exists()) {
			InputStream is = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			while(is.read(b) != -1) {
				os.write(b);
			}
			is.close();
			os.flush();
			os.close();
		}
	}
}
