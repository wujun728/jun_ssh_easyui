package com.lostad.app.apiOpen.file.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lostad.app.common.entity.IdAutoEntity;

/**
 * 数据文件索引
 * 
 * @author songsz
 * @Column(columnDefinition="varchar(32) COMMENT '菜单名称'")
 */
@Entity
@Table(name = "his_file_report")
@JsonIgnoreProperties({"filePath"})
public class HisFileReport extends IdAutoEntity {
    @Column(columnDefinition = "tinyint(1) default 0 COMMENT '是否已下载'")
    private Integer download = 0;
    @Column(columnDefinition = "bigint COMMENT '数据文件ID'")
    private Long dataId = 0L;
    @Column(columnDefinition="varchar(32) COMMENT '所属患者 '")
    private String patientId;
    @Column(columnDefinition="varchar(32) COMMENT '此次检测的操作医生，不一定是当前的责任医生 '")
    private String doctorId;
    @Column(columnDefinition="varchar(64) COMMENT '原文件名 '")
    private String fileName;
   
    @Column(columnDefinition="varchar(255) COMMENT '文件存储路径 '")
    private String filePath;
    @Column(columnDefinition="varchar(255) COMMENT '文件类型编码 ，如心电、血压、尿检 '")
    private String typeCode;
    @Column(columnDefinition="varchar(255) COMMENT '设备标号'")
    private String deviceSn;
    @Column(columnDefinition="varchar(255) COMMENT '文件大小（Byte）'") 
    private String fileLength ;

    public HisFileReport(){}

	/**
	 * @return the download
	 */
	public Integer getDownload() {
		return download;
	}
	/**
	 * @param download the download to set
	 */
	public void setDownload(Integer download) {
		this.download = download;
	}


	/**
	 * @return the dataId
	 */
	public Long getDataId() {
		return dataId;
	}

	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the doctorId
	 */
	public String getDoctorId() {
		return doctorId;
	}

	/**
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return the deviceSn
	 */
	public String getDeviceSn() {
		return deviceSn;
	}

	/**
	 * @param deviceSn the deviceSn to set
	 */
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	/**
	 * @return the fileLength
	 */
	public String getFileLength() {
		return fileLength;
	}

	/**
	 * @param fileLength the fileLength to set
	 */
	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}
    
    

}