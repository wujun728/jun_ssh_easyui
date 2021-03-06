package com.erp.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.erp.dto.GridModel;
import com.erp.model.Bug;
import com.erp.service.BugService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.util.ResourceUtil;

import lombok.extern.slf4j.Slf4j;

//@Namespace("/bug")
//@Action(value = "bugAction")
@Controller
@RequestMapping("/bug")
@Slf4j
public class BugAction extends BaseAction {
	private static final long serialVersionUID = -3055754336964775139L;
	@Autowired
	private BugService bugService;
	
	private File filedata;
	private String filedataFileName;
	private String filedataContentType;

	@ModelAttribute
	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	@ModelAttribute
	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	@ModelAttribute
	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}


	/**
	 * ?????????????????? Administrator??????????????? 2013-6-22???????????? ???????????? @Title:
	 * findBugList @Description: TODO:????????????bug @param @return @param @throws
	 * Exception ???????????? @return String ???????????? @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/findBugList")
	public String findBugList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != searchValue && !"".equals(searchValue)) {
			map.put(getSearchName(), Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel = new GridModel();
		gridModel.setRows(bugService.findBugList(map, pageUtil));
		gridModel.setTotal(bugService.getCount(map, pageUtil));
		OutputJson(gridModel);
		return null;
	}

	/**
	 * ?????????????????? Administrator??????????????? 2013-6-22???????????? ???????????? @Title: addBug @Description:
	 * TODO:??????bug @param @return @param @throws Exception ???????????? @return String
	 * ???????????? @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/addBug")
	public String addBug(Bug bug) throws Exception {
		OutputJson(getMessage(bugService.addBug(bug)), Constants.TEXT_TYPE_PLAIN);
		return null;
	}

	/**
	 * ?????????????????? Administrator??????????????? 2013-6-22???????????? ???????????? @Title: delBug @Description:
	 * TODO:??????bug @param @return @param @throws Exception ???????????? @return String
	 * ???????????? @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delBug")
	public String delBug(Bug bug) throws Exception {
		OutputJson(getMessage(bugService.delBug(bug.getBugId())));
		return null;
	}

	/**
	 * ?????????????????? Administrator??????????????? 2013-6-22???????????? ???????????? @Title: upload @Description:
	 * TODO:BUG???????????? @param @return @param @throws Exception ???????????? @return String
	 * ???????????? @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		String savePath = session.getServletContext().getRealPath(File.separator) + ResourceUtil.getUploadDirectory()
				+ "/";// ????????????????????????
		String saveUrl = contextPath + "/" + ResourceUtil.getUploadDirectory() + "/";// ????????????xhEditor?????????????????????URL
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dateDf = new SimpleDateFormat("dd");
		Date date = new Date();
		String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
		savePath += ymd;
		saveUrl += ymd;

		System.out.println("request.getContextPath()==>" + request.getContextPath());
		File uploadDir = new File(savePath);// ???????????????????????????????????????
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String contentDisposition = request.getHeader("Content-Disposition");// ?????????HTML5??????????????????????????????????????????
		if (contentDisposition != null) {// HTML5??????????????????
			Long fileSize = Long.valueOf(request.getHeader("Content-Length"));// ?????????????????????
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));// ????????????
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));
			fileName = URLDecoder.decode(fileName, "utf-8");
			ServletInputStream inputStream = null;
			try {
				inputStream = request.getInputStream();
			} catch (IOException e) {
				map.put("err", "?????????????????????");
			}

			if (inputStream == null) {
				map.put("err", "??????????????????????????????");
			}

			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
				map.put("err", "?????????????????????????????????");
				map.put("msg", fileName);
			} else {
				// ?????????????????????
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// ??????????????????
				File uploadedFile = new File(savePath, newFileName);

				try {
					FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
				} catch (FileNotFoundException e) {
					map.put("err", "?????????????????????");
				} catch (IOException e) {
					map.put("err", "?????????????????????");
				}
				map.put("err", "");
				System.out.println("----" + saveUrl);
				Map<String, Object> nm = new HashMap<String, Object>();
				nm.put("url", saveUrl + newFileName);
				nm.put("localfile", fileName);
				nm.put("id", 0);
				map.put("msg", nm);
			}
		} else {// ??????HTML5????????????,????????????
			if (ServletFileUpload.isMultipartContent(request)) {// ????????????????????????enctype="multipart/form-data"
				Long fileSize = Long.valueOf(request.getHeader("Content-Length"));
				if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
					map.put("err", "?????????????????????????????????");
					map.put("msg", filedataFileName);
				} else {
					String fileExt = filedataFileName.substring(filedataFileName.lastIndexOf(".") + 1).toLowerCase();
					String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
					Constants.copy(filedata, savePath + newFileName);
					Map<String, Object> nm = new HashMap<String, Object>();
					map.put("err", "");
					nm.put("url", saveUrl + newFileName);
					nm.put("localfile", filedataFileName);
					nm.put("id", 0);
					map.put("msg", nm);
				}
			} else {
				// ??????multipart/form-data??????
				map.put("err", "?????????????????????");
			}
		}
		System.out.println(request.getHeader("Content-Length"));
		System.out.println("filedataContentType=>>" + filedataContentType);
		System.out.println("filedata=>>" + filedata);
		System.out.println("filedataFileName==>>" + filedataFileName);
		System.out.println("savePath==>>" + savePath);
		System.out.println("saveUrl==>>" + saveUrl);
		OutputJson(map, Constants.TEXT_TYPE_PLAIN);
		return null;

	}

}
