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

	UploadedFile ufile;

	public RestController() {
		System.out.println("init RestController");
		ufile = new UploadedFile();
	}

	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	public void get( HttpServletResponse response, @PathVariable String value) {
		try {
			
			response.setContentType(ufile.type);
			response.setContentLength(ufile.length);
			FileCopyUtils.copy(ufile.bytes, response.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;
	
	@RequestMapping(value = "/file/{value:.+}", method = RequestMethod.GET)
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("value") String value) {
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
	        
	        /*
	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        */
	        FileCopyUtils.copy(IOUtils.toByteArray(inputStream), response.getOutputStream());
	        
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

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) {

		// 0. notice, we have used MultipartHttpServletRequest

		// 1. get the files from the request object
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");
		//System.out.println(request.getParameterMap().get("fileName")[0]);

		try {
			// just temporary save file info into ufile
			ufile.length = mpf.getBytes().length;
			ufile.bytes = mpf.getBytes();
			ufile.type = mpf.getContentType();
			ufile.name = mpf.getOriginalFilename();
			String destination =  request.getSession().getServletContext().getRealPath("/") + ufile.name;
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
