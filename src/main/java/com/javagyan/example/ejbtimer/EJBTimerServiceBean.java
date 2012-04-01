package com.javagyan.example.ejbtimer;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.commons.collections.CollectionUtils;

/**
 * EJB Timer Bean
 * @author Sanjeev Kumar
 */
@Stateless
public class EJBTimerServiceBean implements EJBTimerService {

    @Resource
    TimerService timerService;

    /**
     * This function will be called after the <code>intervalDuration</code> is expired.
     * @param timer holds information about the timer objects that was previously created by enterprise beans.
     */
    @Timeout
    public void executeBatch(final Timer timer) {
        // business logic that needs to be executed at timed intervals
        System.out.println("Executed at time : " + Calendar.getInstance());
    }

    /**
     * @see EJBTimerService.tsystems.dm.receptionmonitoring.core.service.ReceptionBatchService#executeBatch(de.tsystems.dm.receptionmonitoring
     *      .core.vo.ReceptionMonitoringConfigVO)
     */
    public void scheduleTimer() {
        if (!(CollectionUtils.isEmpty(timerService.getTimers()))) {
            for (final Object obj : timerService.getTimers()) {
                final Timer timer = (Timer) obj;
                if (timer.getInfo().equals(this.getClass().getName())) {
                    timer.cancel(); // cancel existing timers, if any, for this bean.
                }
            }
        }
        timerService.createTimer(Calendar.getInstance().getTime(), 12000 /* 2 minutes */, this.getClass().getName());
    }
}
