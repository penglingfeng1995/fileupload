package com.plf.fileupload.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.plf.fileupload.pojo.FileMsg;

@RestController
public class FileuploadController {

	@RequestMapping("upload")
	//使用MultipartFile 作为入参
	public FileMsg upload(@RequestParam(name="file") MultipartFile file) {
		try {
			//得到文件的文件名
			String filename = file.getOriginalFilename();
			//得到当前时间
			LocalDate date = LocalDate.now();
			//根据时间创建目录
			File targetpath = new File("D:\\tomcat_file_base\\"+date);
			if(!targetpath.exists()) {
				//判断，如果目录不存在就创建目录
				targetpath.mkdirs();
			}
			//储存文件到该目录
			file.transferTo(new File(targetpath, filename));
			//得到tomcat文件服务器的地址
			String filelocation="http://192.168.0.101:8080/plf/"+date+"/"+filename;
			//返回消息
			return new FileMsg("success",filename,filelocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new FileMsg("faild","","");
		
	}
}
