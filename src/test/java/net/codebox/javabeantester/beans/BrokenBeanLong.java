package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanLong extends Bean {
    @Override
    public void setLongValue(long longValue) {
        // broken
    }
}
