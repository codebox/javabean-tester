package net.codebox.javabeantester.beans;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanString extends Bean {
    @Override
    public void setStringValue(String stringValue) {
        // broken
    }
}
