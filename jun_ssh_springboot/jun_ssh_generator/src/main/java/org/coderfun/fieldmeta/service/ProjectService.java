package org.coderfun.fieldmeta.service;

import org.coderfun.fieldmeta.entity.Project;

import klg.common.dataaccess.BaseService;

public interface ProjectService extends BaseService<Project, Long>{
	
	public Project getDefaultProject();
	

}
