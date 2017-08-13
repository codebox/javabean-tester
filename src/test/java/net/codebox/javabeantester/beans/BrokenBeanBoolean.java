package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanBoolean extends Bean {
    @Override
    public void setBooleanValue(boolean booleanValue) {
        // broken
    }
}
