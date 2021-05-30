package com.lostad.app.apiOpen.file.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lostad.app.apiOpen.file.entity.HisFileData;
import com.lostad.app.apiOpen.file.entity.HisFileReport;
import com.lostad.app.common.service.CacheService;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.FileGenerater;
import com.lostad.app.common.util.Validator;
import com.lostad.app.conf.Const;
import com.lostad.app.system.entity.User;

/**
 * @author DELL
 * 
 */
@Service("fileDateUploadService")
public class FileUploadServiceImpl  implements FileUploadService {

	@Autowired
	private CommonService commonService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private FileGenerater fileGenerater;
	/**
	 * 上传原始数据 ，允许医生doctorId为空
	 * @param 
	 * @return
	 */
	@Transactional
	public HisFileData saveDataInfo(String patientId,String doctorId, MultipartFile file,String typeCode) {
		//病人对应的文件信息
		HisFileData hisFileData = null;
		try {
			String path = fileGenerater.getPathUpload(Const.PATH_DATA);
			//获取医生实体
			String userName = "";
			if(Validator.isNotEmpty(doctorId)){//有上传医生
				User hisDoctor = cacheService.findUser(doctorId);
				if(null != hisDoctor){
					userName = hisDoctor.getUsername();
				}
			}
			
			String extensionName = fileGenerater.getExtensionName(file.getOriginalFilename());
			String fileName = fileGenerater.createFileName(patientId, userName, "4", typeCode, extensionName );
			String filePath = path+File.separator + fileName;
			try {
				file.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long sizeInBytes = file.getSize();
			//病人信息
			Date date = new Date();
			hisFileData = new HisFileData();
			hisFileData.setPatientId(patientId);
			hisFileData.setFileName(fileName);//机器上长传，不需要存储原文件名
			hisFileData.setFilePath(filePath);
			hisFileData.setTypeCode(typeCode);
			hisFileData.setCreateDate(date);
			hisFileData.setFileLength(sizeInBytes+"");
			commonService.save(hisFileData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return hisFileData;
	}
	/**
	 *方法的说明
	 * @param 
	 * @return
	 */
	@Override
	@Transactional
	public HisFileReport saveReportInfo(String patientId,String doctorId,Integer dataId, MultipartFile file,String typeCode) {
		    //病人信息
			String path = fileGenerater.getPathUpload(Const.PATH_DATA);
			// 获取医生实体
			String userName = "";
			if(Validator.isNotEmpty(doctorId)){
				User hisDoctor = cacheService.findUser(doctorId);
				if(null != hisDoctor){
					userName = hisDoctor.getUsername();
				}
			}
			String extensionName = fileGenerater.getExtensionName(file.getOriginalFilename());
			String fileNameNew = fileGenerater.createFileName(patientId, userName, "4", typeCode, extensionName);
			long sizeInBytes = file.getSize();
			
			String fileName = file.getOriginalFilename();
			String filePath1 = path+File.separator+fileNameNew;
			try {
				file.transferTo(new File(filePath1));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			//数据库中deleted字段去除不能为空
			Date date = new Date();
			//病人对应的报告信息
			HisFileReport report = new HisFileReport();
			report.setPatientId(patientId);
			report.setFileName(fileName);
			report.setFilePath(filePath1);
			report.setTypeCode(typeCode);
			//新增以下
			report.setCreateDate(date);
			report.setFileLength(sizeInBytes+"");
			commonService.save(report);
			//修改数据文件
			if(dataId!=null){
				HisFileData data = commonService.find(HisFileData.class,dataId);
				data.setReport(1);//已经出报告
				commonService.merge(data);
			}
		return report;
	}

}
