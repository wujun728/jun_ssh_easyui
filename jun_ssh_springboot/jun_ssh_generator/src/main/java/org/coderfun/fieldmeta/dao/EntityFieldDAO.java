package org.coderfun.fieldmeta.dao;

import org.coderfun.fieldmeta.entity.EntityField;

import klg.common.dataaccess.BaseRepository;

public interface EntityFieldDAO extends BaseRepository<EntityField, Long> {
	public static final String UPDATE_SORT="update_sort";
}
