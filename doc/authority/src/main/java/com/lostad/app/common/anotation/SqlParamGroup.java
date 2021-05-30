package com.lostad.app.common.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于特殊类型查询条件处理，比如 时间区间查询，数字区间查询时的字段注解
 * 关于formatTo 是通过标准的String的format方法的格式把字符串转化为目标格式，
 * 如：比如日期类型 2005-01-01 自动为 2005-01-01 00:00:00 
 * 可使用 "%s 00:00:00"
 * 比如给此值加 mysql函数 "TRIM('%s') "
 * @author：sszvip@qq.com
 * @since ：2016年11月1日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlParamGroup {
    public String colName() default "";
    public String operation() ;//必填
    public String formatTo() default "";//转化为目标格式，
}