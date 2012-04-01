package com.javagyan.example.ejbtimer;

import javax.ejb.Local;

/**
 * Local interface for batch service. Batch job is responsible for finding and notifying defaulted plants for a given
 * time interval based on the received reception data during that interval.
 * @author Sanjeev Kumar
 */
@Local
public interface EJBTimerService {
    /**
     * Starting point for the execution of batch job for finding and notifying defaulted plants for a given time
     * interval.
     * @param timeInerval interval after which ejb timer expires.
     */
    void scheduleTimer();
}
