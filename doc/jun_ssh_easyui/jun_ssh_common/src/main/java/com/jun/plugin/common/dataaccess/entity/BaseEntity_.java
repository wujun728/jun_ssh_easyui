package com.jun.plugin.common.dataaccess.entity;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.jun.plugin.common.dataaccess.entity.BaseEntity;

@Generated(value="Dali", date="2018-11-23T14:35:44.473+0800")
@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ {
	public static volatile SingularAttribute<BaseEntity, Serializable> id;
	public static volatile SingularAttribute<BaseEntity, Date> createTime;
	public static volatile SingularAttribute<BaseEntity, Date> modifyTime;
	public static volatile SingularAttribute<BaseEntity, String> remark;
}
