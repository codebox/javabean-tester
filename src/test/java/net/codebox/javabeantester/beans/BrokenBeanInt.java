package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanInt extends Bean {
    @Override
    public void setIntValue(int intValue) {
        // broken
    }
}
