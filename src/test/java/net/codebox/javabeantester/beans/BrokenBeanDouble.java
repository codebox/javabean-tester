package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanDouble extends Bean {
    @Override
    public void setDoubleValue(double doubleValue) {
        // broken
    }
}
