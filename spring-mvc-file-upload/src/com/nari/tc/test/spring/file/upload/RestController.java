package com.nari.tc.test.spring.file.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/cont")
public class RestController {

	/**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;
	
	/**
	 * 已上传的文件对象
	 */
	UploadedFile ufile;

	public RestController() {
		System.out.println("init RestController");
		ufile = new UploadedFile();
	}
	/**
	 * 获取刚刚上传的图片
	 * @param response
	 * @param value
	 */
	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	public void get( HttpServletResponse response, @PathVariable String value) {
		try {
			//ufile在upload方法中已经赋值，即为最近上传的文件的相关属性
			response.setContentType(ufile.type);
			response.setContentLength(ufile.length);
			FileCopyUtils.copy(ufile.bytes, response.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 下载文件接口
	 * @param request
	 * @param response
	 * @param value 注意RequestMapping中的value注解形式，其保证了value可以带文件后缀名
	 */
	@RequestMapping(value = "/file/{value:.+}", method = RequestMethod.GET)
	public void getFile(HttpServletRequest request, 
			HttpServletResponse response, @PathVariable("value") String value) {
		try {
			// get absolute path of the application
	        ServletContext context = request.getServletContext();
	        String appPath = context.getRealPath("");
	        System.out.println("appPath = " + appPath);
	        
	        // construct the complete absolute path of the file
	        String fullPath = appPath + value;      
	        File downloadFile = new File(fullPath);
	        FileInputStream inputStream = new FileInputStream(downloadFile);
	        
	        // get MIME type of the file
	        String mimeType = context.getMimeType(fullPath);
	        if (mimeType == null) {
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);
	        
	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	        //此处采用get方法中的FileCopyUtiles类将图片传到客户端
	        FileCopyUtils.copy(IOUtils.toByteArray(inputStream), response.getOutputStream());
	        
	        /* 注释部分为另一个文件下载参考示例原来的代码
	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        */	             
	        /*
	        // get output stream of the response
	        OutputStream outStream = response.getOutputStream();
	 
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
	        // write bytes read from the input stream into the output stream
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        */
	        
	 
	        inputStream.close();
	        //outStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传接口，采用MultipartHttpServletRequest实现
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) {

		// 0. notice, we have used MultipartHttpServletRequest

		// 1. get the files from the request object
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");
		//request.getParameterMap()方法用来获得传入的其他参数
		//System.out.println(request.getParameterMap().get("fileName")[0]);

		try {
			// just temporary save file info into ufile
			ufile.length = mpf.getBytes().length;
			ufile.bytes = mpf.getBytes();
			ufile.type = mpf.getContentType();
			ufile.name = mpf.getOriginalFilename();
			String destination =  request.getSession().getServletContext().getRealPath("/") + "upload/" +ufile.name;
			java.io.File file1 = new java.io.File(destination);
			mpf.transferTo(file1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2. send it back to the client as <img> that calls get method
		// we are using getTimeInMillis to avoid server cached image

		return "<img src='http://localhost:8080/spring-mvc-file-upload/rest/cont/get/"
				+ Calendar.getInstance().getTimeInMillis() + "' />";

	}
}
