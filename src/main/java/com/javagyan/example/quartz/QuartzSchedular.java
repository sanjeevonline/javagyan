package com.javagyan.example.quartz;

import java.util.Map;

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
