package cn.e3mall.portal.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.service.PictureService;

@RestController
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
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
