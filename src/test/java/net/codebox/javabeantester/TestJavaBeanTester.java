package net.codebox.javabeantester;

import net.codebox.javabeantester.beans.*;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created by rob on 13/08/2017.
 */
public class TestJavaBeanTester {
    @Test
    public void validBeanPasses() throws IntrospectionException {
        JavaBeanTester.test(Bean.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidStringAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanString.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidIntAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanInt.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidIntWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanInt.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidArrayAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanArray.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidBooleanAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanBoolean.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidBooleanWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanBooleanWrapper.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidLongAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanLong.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidLongWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanLongWrapper.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidDoubleAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanDouble.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidDoubleWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanDoubleWrapper.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidFloatAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanFloat.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidFloatWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanFloatWrapper.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidCharAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanChar.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidCharWrapperAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanCharWrapper.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidEnumAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanEnum.class);
    }

    @Test(expected=AssertionError.class)
    public void invalidNoArgConstructorObjectAccessorsFail() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanNoArgConstructorObject.class);
    }

    @Test
    public void skippingPropertiesWorksCorrectly() throws IntrospectionException {
        JavaBeanTester.test(BrokenBeanString.class, "stringValue");
    }
}
