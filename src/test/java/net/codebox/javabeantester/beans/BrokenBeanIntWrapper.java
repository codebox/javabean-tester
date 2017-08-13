package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanIntWrapper extends Bean {
    @Override
    public void setIntWrapperValue(Integer intValue) {
        // broken
    }
}
