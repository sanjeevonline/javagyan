package com.javagyan.example.quartz;

import java.text.ParseException;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Uses Quartz for scheduling jobs.
 * @author Sanjeev Kumar
 */
public class QuartzSchedular {
    /**
     * Initializes the quartz with handle to the EJB that provides the actual business logic for the batch module.
     */
    public void scheduleEJBJob() {
        final JobDetail jobDetail = new JobDetail("Batch Job", "Batch Job", EJBInvokerJob.class);
        jobDetail.getJobDataMap().put(EJBInvokerJob.EJB_JNDI_NAME_KEY, "ejb/BatchServiceBean");
        jobDetail.getJobDataMap().put(EJBInvokerJob.EJB_METHOD_KEY, "executeBatch");
        final CronTrigger cronTrigger = new CronTrigger("Batch Trigger", "Batch Trigger");
        try {
            final String cronExpression = "0 0/20 * * * ?"; // schedule recursive job for every 20 minutes
            cronTrigger.setCronExpression(cronExpression);
            final Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            sched.scheduleJob(jobDetail, cronTrigger);
        } catch (final SchedulerException e) {
            throw new RuntimeException("SchedulerException while scheduling Quartz job ..", e);
        } catch (final ParseException e) {
            throw new RuntimeException("ParseException while scheduling Quartz job ..", e);
        }
    }

    /**
     * Initializes the EJB and calls the method that starts the Timer.
     * @param ServletConfig
     */
    public void scheduleSimpleJob() {
        final SchedulerFactory sf = new StdSchedulerFactory();
        try {
            final Scheduler sched = sf.getScheduler();
        } catch (final SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final RunMeTask task = new RunMeTask();
        // specify your scheduler task details
        final JobDetail job = new JobDetail();
        job.setName("runMeJob");
        job.setJobClass(RunMeJob.class);
        final Map dataMap = job.getJobDataMap();
        dataMap.put("runMeTask", task);
    }
}
