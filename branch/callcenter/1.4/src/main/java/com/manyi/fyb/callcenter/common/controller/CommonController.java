package com.manyi.fyb.callcenter.common.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.manyi.fyb.callcenter.utils.CommonUtils;
import com.manyi.fyb.callcenter.utils.OSSObjectUtil;
import com.manyi.fyb.callcenter.utils.ThumbnailUtil;

@Controller
@RequestMapping("/common")
public class CommonController {

	@RequestMapping("/uploadCommon")
	@ResponseBody
	public String uploadCommon (MultipartHttpServletRequest request , HttpServletResponse response)  {
		
		boolean tag = false;
		JSONObject json = new JSONObject();
		String fileName = "";
		String returnFullPath = "";
		String fileInputName = request.getParameter("fileInputName");//取得input框的名称
		if (StringUtils.isBlank(fileInputName)) {
			//CommonFunction.responseCommon(response, "text/html", "<script>parent.callbackUploadedPicture('error')</script>");
			//return null;
			fileInputName ="upload";//没有默认为"upload"
		}
		String folderPath = "D:/test/testupload/test/";
		String uploadType = request.getParameter("uploadType");
		if(StringUtils.isBlank(uploadType)) {
			uploadType = "0";
		}
		String serialCode = request.getParameter("serialCode");
		if (StringUtils.isBlank(serialCode)) {
			serialCode = "error";
			
		}
		serialCode = OSSObjectUtil.getAliyunPath(serialCode);
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			id="0";
		}
		serialCode = serialCode + uploadType + "/" + id;
		String savedPath = uploadType + "/";
		/* 以 年月 或 年月日 包一层文件夹，如 /deco/specialTopic/2013/04/11/xxx.pic */
		SimpleDateFormat dfOfFolder = new SimpleDateFormat("yyyy/MM/dd");//yyyyMMdd
		savedPath =  dfOfFolder.format(new Date()) + "/";
		folderPath += savedPath;
		
		MultipartFile file = request.getFile(fileInputName);
		String returnParentFunction = request.getParameter("returnParentFunction");
		if (StringUtils.isBlank(returnParentFunction)) {
			returnParentFunction = "callbackUploadedPicture";
		}
		System.out.println("11111111");
		System.out.println(file.getOriginalFilename());
		if(file.getSize() > 5 * 1024 * 1024)  {
			json.put("returnStatus", "error");
			json.put("returnDescription", "the size is larger than 5M");
			try {
				CommonUtils.responseCommon(response, "<script>parent." + returnParentFunction + "(" + json + ")</script>");
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("33333");
		if (file != null && !file.isEmpty()) {
			try {
				System.out.println("222222");
				int ran = (int)(Math.random()*900) + 100;
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String suffix = (file.getOriginalFilename().lastIndexOf(".") == -1 ? "" : file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase());
				String pre = df.format(new Date()) + "_" + ran ;
				fileName =  pre + suffix;
				serialCode = serialCode + "/" + pre ;
				CommonUtils.copyFile(file.getInputStream(),  folderPath + fileName);
				ThumbnailUtil.getThumbnail(new File(folderPath + fileName),new File(folderPath + pre +".thumbnail" + suffix ));
				//..start aliyun
				returnFullPath = OSSObjectUtil.upload(serialCode,folderPath + fileName);
				System.out.println(returnFullPath);
				returnFullPath = OSSObjectUtil.upload(serialCode +".thumbnail",folderPath + pre +".thumbnail" + suffix );
				System.out.println(returnFullPath);
				//end aliyun
				tag = true;
			} catch (FileNotFoundException fnfe) {
				tag = false;
				fnfe.printStackTrace();
			} catch (IOException ie) {
				tag = false;
				ie.printStackTrace();
			} catch (Exception e) {
				tag = false;
				e.printStackTrace();
			}
		}
		
		if (tag == true) {
			json.put("returnStatus", "success");
			json.put("returnKey",serialCode);
			System.out.println(serialCode);
			json.put("returnLongPath", returnFullPath);
			json.put("domId", request.getParameter("domId"));
			json.put("uploadType", uploadType);
			
			
		} else {
			json.put("returnStatus", "error");
			json.put("returnDescription", "systemError");
		}
		System.out.println("<script>parent." + returnParentFunction + "(" + json + ")</script>");
		try {
			CommonUtils.responseCommon(response, "<script>parent." + returnParentFunction + "(" + json + ")</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		return null;
		
	}
}
