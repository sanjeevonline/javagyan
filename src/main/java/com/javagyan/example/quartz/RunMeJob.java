package com.javagyan.example.quartz;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RunMeJob implements Job {
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        final Map dataMap = context.getJobDetail().getJobDataMap();
        final RunMeTask task = (RunMeTask) dataMap.get("runMeTask");
        task.printMe();
    }
}
