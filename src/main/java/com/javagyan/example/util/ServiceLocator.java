package com.javagyan.example.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * The Class ServiceLocator.
 */
public final class ServiceLocator {

    /**
     * Gets the single instance of ServiceLocator.
     * @return single instance of ServiceLocator.
     */
    public static ServiceLocator getInstance() {
        return INSTANCE;
    }

    /** The initial context for looking up objects from container environment. */
    private Context initialContext;

    /**
     * Cache of jndi objects mapped by jndiName. Key of this cache is the fully qualified name of the class and value is
     * the jndi object instance. Before going for JNDI lookup this cache is checked first. If key value pair is found in
     * cache then jndi look up is not performed. This improves performance and avoids redundant instance creations.
     */
    private Map<String, Object> cache;

    /** The singleton instance of service locator. */
    private static final ServiceLocator INSTANCE = new ServiceLocator();

    /**
     * Private Constructor. Instantiates a new service locator.
     */
    private ServiceLocator() {
        try {
            this.initialContext = new InitialContext();
            this.cache = new ConcurrentHashMap<String, Object>();
        } catch (final NamingException ex) {
            throw new RuntimeException("NamingException while creating InitialContext", ex);
        }
    }

    public Map<String, Object> getCache() {
        return this.cache;
    }

    /**
     * Locates objects from local cache and if not found then from context using JNDI lookup.
     * @param jndiName jndiName of the object to be looked up in initialContext.
     * @return Object jndiObject from initialContext.
     */
    public Object locate(final String jndiName) {
        Object object = null;
        try {
            if (this.cache.containsKey(jndiName)) {
                object = this.cache.get(jndiName);
            } else {
                object = this.initialContext.lookup(jndiName);
                this.cache.put(jndiName, object);
            }
        } catch (final NamingException ex) {
            throw new RuntimeException(String.format("NamingException raised while looking up jndi  %s", jndiName), ex);
        }

        return object;
    }
}
