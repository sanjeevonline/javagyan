package com.javagyan.example.ejbtimer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Initializes the EJB Timer.
 * @author Sanjeev Kumar
 */
public class EJBTimerSchedular {
    /**
     * Locates objects from context using JNDI lookup.
     * @param ejbJNDIName jndiName of the object to be looked up in initialContext.
     * @return Object jndiObject from initialContext.
     */
    public Object locate(final String ejbJNDIName) {
        Object object = null;
        try {
            final Context initialContext = new InitialContext();
            object = initialContext.lookup(ejbJNDIName);
        } catch (final NamingException ex) {
            throw new RuntimeException(String.format("NamingException raised while looking up jndi  %s", ejbJNDIName),
                    ex);
        }

        return object;
    }

    /**
     * Initializes the EJB and calls the method that starts the Timer.
     * @param ServletConfig
     */
    public void scheduleTimerService() {
        final EJBTimerService timerService = (EJBTimerService) locate("ejb/EJBTimer");
        timerService.scheduleTimer();
    }
}
