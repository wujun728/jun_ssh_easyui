package com.erp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.BackupScheduleConfig;
import com.erp.model.Log;
import com.erp.service.DbBackUpService;
import com.erp.shiro.ShiroUser;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.util.XMLFactory;
@SuppressWarnings("unchecked")
@Service("dbBackUpService")
public class DbBackUpServiceImpl implements DbBackUpService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private JobDetail backupTask=JobBuilder.newJob(BackupScheduleServiceImpl.class).withIdentity("task","taskGroup").build();;
//	private JobDetail backupTask=new JobDetail("task","taskGroup",BackupScheduleServiceImpl.class);
	private XMLFactory xmlFactory=new XMLFactory(BackupScheduleConfig.class);
	private static String basePath =System.getProperty("erp");
	private static String xmlPath=basePath+"configXml"+File.separator+"dbBackUpInit.xml";
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public void onApplicationEvent(ContextRefreshedEvent event )
	{
		if (event instanceof ContextRefreshedEvent) {
            System.out.println("Spring?????????????????????, ?????????????????????????????????????????????????????????");
            BackupScheduleConfig config = getBackupScheduleConfig();
            if (config != null && "Y".equals(config.getScheduleEnabled())) {
               schedule(config.getScheduleHour(),config.getScheduleMinute(),config.getScheduleEnabled());
               System.out.println("?????????????????????????????????");
            }else{
            	System.out.println("????????????????????????????????????");
            }
        }
	}
    /* (??? Javadoc) 
    * <p>Title: getBackupScheduleConfig</p> 
    * <p>Description:????????????????????????????????? </p> 
    * @return 
    * @see com.erp.service.DbBackUpService#getBackupScheduleConfig() 
    */
    public BackupScheduleConfig getBackupScheduleConfig(){  
    		try
			{
    			return xmlFactory.unmarshal(new FileInputStream(new File(xmlPath)));
			} catch (FileNotFoundException e)
			{
				System.out.println("xml???????????????");
			}
    		return null;
    }
    
    /* (??? Javadoc) 
    * <p>Title: unSchedule</p> 
    * <p>Description:?????????????????? ????????????????????? </p> 
    * @return 
    * @see com.erp.service.DbBackUpService#unSchedule() 
    */
    public String unSchedule(){        
        try {
            BackupScheduleConfig config=getBackupScheduleConfig();
            if(config!=null){
                config.setScheduleEnabled("N");
                //String xmlString = xmlFactory.marshal(config);
                //xmlFactory.stringXMLToFile(xmlPath, xmlString);
                System.out.println("??????????????????????????????");
            }else{
                String tip="???????????????????????????????????????";
                System.out.println(tip);
                return tip;
            }
            Scheduler sched = sf.getScheduler();
            sched.deleteJob(backupTask.getKey());
            sched.shutdown();
            String tip="????????????????????????????????????????????????" + backupTask.getDescription() + ",?????????: " + backupTask.getDescription();
            System.out.println(tip);
            return tip;
        } catch (SchedulerException ex) {
            String tip="????????????????????????????????????????????????"+ex.getMessage();
            System.out.println(tip);
            return tip;
        }
    }
    
    /* (??? Javadoc) 
    * <p>Title: schedule</p> 
    * <p>Description: ????????????????????????24????????????</p> 
    * @param hour ??????
    * @param minute ??????
    * @param scheduleEnabled
    * @return 
    * @see com.erp.service.DbBackUpService#schedule(int, int, java.lang.String) 
    */
    public String schedule(int hour, int minute,String scheduleEnabled) {
        BackupScheduleConfig scheduleConfig = getBackupScheduleConfig();
        if (scheduleConfig == null) {
            //??????????????????
            BackupScheduleConfig config = new BackupScheduleConfig();
            config.setScheduleHour(hour);
            config.setScheduleMinute(minute);
            config.setScheduleEnabled("Y");
            String xmlString = xmlFactory.marshal(config);
            xmlFactory.stringXMLToFile(xmlPath, xmlString);
        } else {
            //??????????????????
            scheduleConfig.setScheduleHour(hour);
            scheduleConfig.setScheduleMinute(minute);
            scheduleConfig.setScheduleEnabled(scheduleEnabled);
            String xmlString = xmlFactory.marshal(scheduleConfig);
            xmlFactory.stringXMLToFile(xmlPath, xmlString);
        }

        String expression = "0 " + minute + " " + hour + " * * ?";
       // String expression = "5/15 * * * * ?";
       try {
    	   Date date = new Date();
    	   Date endTime = new Date();
           LocalDate localDate = LocalDate.now();
           LocalDateTime localDateTime = LocalDateTime.now();
           System.out.println("localDate2Date:"+localDate2Date(localDate));
           
    	   Trigger trigger = TriggerBuilder.newTrigger()
                   .withIdentity("trigger1"+"???????????????,????????????" + hour + ":" + minute, "group1")
                   .startAt(localDate2Date(localDate)).endAt(endTime)
                   .withSchedule(CronScheduleBuilder.cronSchedule(expression))         //"0 0 12 * * ? "
                   .build();
            Scheduler sched = sf.getScheduler();
            sched.deleteJob(backupTask.getKey());
            sched.scheduleJob(backupTask, trigger);
            sched.start();
            String tip = "??????????????????????????????????????????" + backupTask.getDescription() + ",?????????: " + backupTask.getDescription();
            System.out.println(tip);
            String taskState = "?????????????????????????????????????????????????????????24????????????" + hour + ":" + minute;
            System.out.println(taskState);
            return taskState;
        } catch (Exception  e) {
            String tip = "??????????????????????????????????????????" + e.getMessage();
            System.out.println(tip);
            return tip;
        }
    }
    
    public static Date localDate2Date(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
    
	/* (??? Javadoc) 
	* <p>Title: handSchedule</p> 
	* <p>Description:????????????</p> 
	* @return 
	* @see com.erp.service.DbBackUpService#handSchedule() 
	*/
	public boolean handSchedule()
	{
		String filename = Constants.dbBackUp();
		String sqlName = Constants.BASE_PATH +"attachment" +File.separator+ "dbBackUp" + File.separator + filename;
		return addLog(sqlName,filename,false);
	}
	
	/* (??? Javadoc) 
	* <p>Title: findLogsAllList</p> 
	* <p>Description: ???????????????????????????</p> 
	* @param map
	* @param pageUtil
	* @return 
	* @see com.erp.service.DbBackUpService#findLogsAllList(java.util.Map, com.erp.util.PageUtil) 
	*/
	public List<Log> findLogsAllList(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="from Log t where t.type=1 ";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql+=" order by t.logId desc";
		return publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}
	
	/* (??? Javadoc) 
	* <p>Title: getCount</p> 
	* <p>Description: ????????????????????????</p> 
	* @param map
	* @param pageUtil
	* @return 
	* @see com.erp.service.DbBackUpService#getCount(java.util.Map, com.erp.util.PageUtil) 
	*/
	public Long getCount(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="select count(*) from Log t where t.type=1 ";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql+=" order by t.logId desc";
		//dbBackUp();
		return  publicDao.count(hql, map);
	}
	
	/* (??? Javadoc) 
	* <p>Title: addLog</p> 
	* <p>Description:??????????????????????????? </p> 
	* @param path
	* @param fileName
	* @param isSystem
	* @return 
	* @see com.erp.service.DbBackUpService#addLog(java.lang.String, java.lang.String, boolean) 
	*/
	public boolean addLog(String path,String fileName,boolean isSystem)
	{
		Log log = new Log();
		log.setLogDate(new Date());
		log.setType(1);
		if (isSystem)
		{
			log.setName("system");
			log.setMac("**************");
			log.setIp("**************");
		}else {
			ShiroUser user = Constants.getCurrendUser();
			log.setUserId(user.getUserId());
			log.setName(user.getAccount());
			log.setMac(Constants.getMacAddr());
			log.setIp(Constants.getIpAddrNew());
		}
		log.setEventName("????????????");
		log.setEventRecord(path);
		log.setObjectId(fileName);
		publicDao.save(log);
		return true;
	}
	
	
	public static String getSeparator(){
		  return System.getProperties().getProperty("file.separator");
	}
}
