package com.javagyan.example.quartzwithejb;

import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p>
 * A <code>Job</code> that invokes a method on an EJB3 EJB. Based on the EJBInvokerJob.
 * </p>
 * <p>
 * Expects the properties corresponding to the following keys to be in the <code>JobDataMap</code> when it executes:
 * <ul>
 * <li><code>EJB_JNDI_NAME_KEY</code>- the JNDI name (location) of the EJB</li>
 * <li><code>EJB_METHOD_KEY</code>- the name of the method to invoke on the EJB.</li>
 * <li><code>EJB_ARGS_KEY</code>- an Object[] of the args to pass to the method (optional, if left out, there are no
 * arguments).</li>
 * <li><code>EJB_ARG_TYPES_KEY</code>- a Class[] of the types of the args to pass to the method (optional, if left out,
 * the types will be derived by calling getClass() on each of the arguments).</li>
 * </ul>
 * <br/>
 * </p>
 * <p>
 * The result of the EJB method invocation will be available to <code>Job/TriggerListener</code>s via
 * <code>{@link org.quartz.JobExecutionContext#getResult()}</code>.
 * </p>
 * @author Sanjeev Kumar
 */
public class EjbInvokerJob implements Job {

    /** Variable for EJB JNDI NAME KEY. */
    public static final String EJB_JNDI_NAME_KEY = "ejb";

    /** Variable that holds the name of ejb method to be invoked. */
    public static final String EJB_METHOD_KEY = "method";

    /** Variable that holds key name for EJB ARGUMENT TYPE . */
    public static final String EJB_ARG_TYPES_KEY = "argTypes";

    /** Variable that holds the key name for EJB ARGUMENT. */
    public static final String EJB_ARGS_KEY = "args";

    /** Default Constructor. */
    public EjbInvokerJob() {
        // do nothing
    }

    /** Execute method that invoke EJB Method and the set the results in JobExecutionContext. */
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        final JobDataMap dataMap = context.getMergedJobDataMap();
        final String ejbJNDIName = dataMap.getString(EJB_JNDI_NAME_KEY);
        final String methodName = dataMap.getString(EJB_METHOD_KEY);
        Object[] arguments = (Object[]) dataMap.get(EJB_ARGS_KEY);

        if ((null == ejbJNDIName) || (ejbJNDIName.length() == 0)) {
            throw new JobExecutionException("must specify ejb JNDI name");
        }

        if (arguments == null) {
            arguments = new Object[0];
        }

        Class[] argTypes = (Class[]) dataMap.get(EJB_ARG_TYPES_KEY);
        if (argTypes == null) {
            argTypes = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                argTypes[i] = arguments[i].getClass();
            }
        }

        final Object ejb = locate(ejbJNDIName);
        try {
            final Method methodToExecute = ejb.getClass().getDeclaredMethod(methodName, argTypes);
            final Object returnObj = methodToExecute.invoke(ejb, arguments);
            context.setResult(returnObj);
        } catch (final Exception e) {
            throw new JobExecutionException(e);
        }
    }

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
}
