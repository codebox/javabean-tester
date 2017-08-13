package net.codebox.javabeantester.beans;

import java.lang.annotation.RetentionPolicy;

/**
 * Created by rob on 13/08/2017.
 */
public class BrokenBeanEnum extends Bean {
    @Override
    public void setEnumValue(RetentionPolicy enumValue) {
        // broken
    }
}
