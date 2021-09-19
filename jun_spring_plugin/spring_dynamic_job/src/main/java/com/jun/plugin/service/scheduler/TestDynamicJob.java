package com.jun.plugin.service.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Just for testing
 *
 * @author Shengzhao Li
 */
public class TestDynamicJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final Object mailGuid = context.getMergedJobDataMap().get("mailGuid");
        System.out.println("[Dynamic-Job]  It is " + new Date() + " now, mailGuid=" + mailGuid);
    }
}