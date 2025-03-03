package com.payment.simulator.server.engine;


import java.util.concurrent.ConcurrentHashMap;


public class ChainRequestContext extends ConcurrentHashMap<String, Object> {

    private static final long serialVersionUID = -5792996927212791314L;

    protected static final ThreadLocal<ChainRequestContext> THREAD_LOCAL
            = ThreadLocal.withInitial(ChainRequestContext::new);

    /**
     * Set the current context value
     * @param requestContext
     */
    public static void setRequestContext(ChainRequestContext requestContext) {
        THREAD_LOCAL.set(requestContext);
    }

    /**
     * Get the current ChainRequestContext
     *
     * @return the current ChainRequestContext
     */
    public static ChainRequestContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * unsets the threadLocal context. Done at the end of the request.
     *
     * @return
     */
    public void unset() {
        this.clear();
        THREAD_LOCAL.remove();
    }

    /**
     * Convenience method to return a string value for a given key
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        Object value = get(key);
        return value == null ? null : value.toString();
    }

}
