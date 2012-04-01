package com.javagyan.example.quartzwithejb;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Uses Quartz for scheduling jobs.
 * @author Sanjeev Kumar
 */
public class QuartzEjbSchedular {
    /**
     * Initializes the quartz with handle to the EJB that provides the actual business logic for the batch module.
     */
    public void scheduleEJBJob() {
        final JobDetail jobDetail = new JobDetail("Batch Job", "Batch Job", EjbInvokerJob.class);
        jobDetail.getJobDataMap().put(EjbInvokerJob.EJB_JNDI_NAME_KEY, "ejb/BatchServiceBean");
        jobDetail.getJobDataMap().put(EjbInvokerJob.EJB_METHOD_KEY, "executeBatch");
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
}
