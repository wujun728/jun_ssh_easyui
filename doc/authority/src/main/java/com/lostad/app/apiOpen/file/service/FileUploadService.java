package com.lostad.app.apiOpen.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.lostad.app.apiOpen.file.entity.HisFileData;
import com.lostad.app.apiOpen.file.entity.HisFileReport;

/**
 * @author DELL
 *
 */
public interface FileUploadService{
	/**
	 * 保存数据文件相关信息
	 * @param doctorId    可为空
	 * @param hisFlowRecord 
	 * @param value 身份证号
	 * @param fileLen 文件大小
	 * @param uploadfilepath 文件路径
	 * @param fileName 文件名
	 * @param sizeInBytes 
	 * @return
	 */
	public HisFileData saveDataInfo(String patientId, String doctorId, MultipartFile file,String typeCode);
	/**
	 * 报告文件
	 * @param dataId 
	 * @param 
	 * @return HisFileData
	 */
	HisFileReport saveReportInfo(String patientId, String doctorId,Integer dataId, MultipartFile file,String typeCode);

	
}