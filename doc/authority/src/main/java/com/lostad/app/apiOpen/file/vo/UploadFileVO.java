package com.lostad.app.apiOpen.file.vo;
/**
	 * 借口返回值
	 * 
	 */
	//@ApiModel(value="文件信息")
	public class UploadFileVO  {
		//@ApiModelProperty(value="生成的文件id",required=false)   
		public Long id;
		//@ApiModelProperty(value="病人id",required=false)   
		public String patientId;
		//@ApiModelProperty(value="上传医生ID",required=false)  
		public String doctorId;
		//@ApiModelProperty(value="上传医生ID",required=false)  
		public String fileName;
		//@ApiModelProperty(value="文件类型",required=false)  
		public String typeCode;
	//	@ApiModelProperty(value="文件长度",required=false)  
		public String fileLength ;
	    public UploadFileVO(){}
	}