package web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import domain.Beauty;
import exception.BasicException;
import service.interfaces.IBeautyService;
import web.message.ResponseMsg;

@Controller
public class BeautyController {
	Log log=LogFactory.getLog(BeautyController.class);
	@Resource
	private IBeautyService beautyService;
	
	@RequestMapping(value="/beauty/index")
	public String index(){
		return "/beauty/index";
	}
	
	@ResponseBody
	@RequestMapping(value="/beauty/getPic",produces = {"text/plain;charset=utf-8"})
	public String getPic(){
		try {
			List<Beauty> list=beautyService.getPic();
			return new ResponseMsg(1, "", list).toString();
		} catch (BasicException e) {
			log.info(e.hint());
			return new ResponseMsg(0, "出异常了", "").toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="/beauty/getTxt",produces = {"text/plain;charset=utf-8"})
	public String getTxt(){
		try {
			List<Beauty> list=beautyService.getTxt();
			return new ResponseMsg(1, "", list).toString();
		} catch (BasicException e) {
			log.info(e.hint());
			return new ResponseMsg(0, "出异常了", "").toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="/beauty/uploadPic",produces = {"text/plain;charset=utf-8"})
	public String uploadPic(HttpServletRequest request,String link,String comment){
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
//		upload.setSizeMax(sizeMax);
		try {
			FileItemIterator fii=upload.getItemIterator(request);
			FileItemStream fis=fii.next();
			log.info(fis.getFieldName());
			log.info(fis.getName());
			String fileName=fis.getFieldName();
			String uploadPath="upload";
			String filePath=uploadPath + "//" + fileName;
			BufferedInputStream in = new BufferedInputStream(fis.openStream());   
            BufferedOutputStream out = new BufferedOutputStream(  
                     new FileOutputStream(new File(uploadPath + "//" + fileName)));  
            Streams.copy(in, out, true); // 开始把文件写到你指定的上传文件夹  
            beautyService.uploadPic(filePath, link, comment);
            return new ResponseMsg(1, "", "").toString();
//			while(fii.hasNext()){
//				
//				 if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域  
//					 String fileName = fis.getName().substring(  
//	                         fis.getName().lastIndexOf("."));// 获得上传文件的文件名  
////	                 fileName = sdf.format(new Date())+"-"+index+fileName;  
//	                
////	                 index++;  
//	             } 
//			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
//			System.err
//			log.error(e);
			log.info("no file exists");
			return new ResponseMsg(0, "没有上传文件", "").toString();
		} catch (IOException e) {
			log.error(e);
			return new ResponseMsg(0, "出异常了", "").toString();
		} catch (BasicException e) {
			log.error(e.hint());
			return new ResponseMsg(0, "出异常了", "").toString();
		}
		
//		try {
//			
//			
//			return new ResponseMsg(1, "", list).toString();
//		} catch (BasicException e) {
//			log.info(e.hint());
//			return new ResponseMsg(0, "出异常了", "").toString();
//		}
	}
	
}
