package com.codebox.bean;

import java.beans.IntrospectionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JavaBeanTesterTest {

    private SampleBean sampleBean;
    private SampleBean expectedBean;

    @Before
    public void init() {
        this.sampleBean = new SampleBean();
        this.expectedBean = new SampleBean();
    }

    @Test
    public void load_fullBean() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
        Assert.assertNotNull(this.sampleBean.getDoubleWrapper());
    }

    @Test
    public void load_fullBeanEquals() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleBean.class, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);

        this.sampleBean.setSampleDepthBean(new SampleDepthBean());
        this.expectedBean.setSampleDepthBean(new SampleDepthBean());
        this.sampleBean.setEmptyBean(new EmptyBean());
        this.expectedBean.setEmptyBean(new EmptyBean());
        JavaBeanTester.load(SampleDepthBean.class, this.sampleBean.getSampleDepthBean(),
                JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleDepthBean.class, this.expectedBean.getSampleDepthBean(),
                JavaBeanTester.LOAD_UNDERLYING_DATA);

        JavaBeanTester.equalsTests(this.sampleBean, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void load_fullBeanEqualsShort() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleBean.class, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.equalsTests(this.sampleBean, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void load_fullBeanEqualsSkipUnderlying() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, !JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleBean.class, this.expectedBean, !JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.equalsTests(this.sampleBean, this.expectedBean, !JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void load_fullBeanSkipUnderlyingData() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, !JavaBeanTester.LOAD_UNDERLYING_DATA);
        Assert.assertNotNull(this.sampleBean.getDoubleWrapper());
    }

    @Test
    public void load_partialBeanEquals() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleBean.class, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);

        this.sampleBean.setSampleDepthBean(new SampleDepthBean());
        this.expectedBean.setSampleDepthBean(new SampleDepthBean());
        JavaBeanTester.load(SampleDepthBean.class, this.sampleBean.getSampleDepthBean(),
                JavaBeanTester.LOAD_UNDERLYING_DATA);
        JavaBeanTester.load(SampleDepthBean.class, this.expectedBean.getSampleDepthBean(),
                JavaBeanTester.LOAD_UNDERLYING_DATA);

        JavaBeanTester.equalsTests(this.sampleBean, this.expectedBean, JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void load_skipBeanProperties() throws IntrospectionException {
        JavaBeanTester.load(SampleBean.class, this.sampleBean, JavaBeanTester.PERFORM_CAN_EQUALS, "string");
        Assert.assertNotNull(this.sampleBean.getDoubleWrapper());
        Assert.assertNull(this.sampleBean.getString());
    }

    @Test
    public void test_fullBean() throws IntrospectionException, InstantiationException, IllegalAccessException {
        JavaBeanTester.test(SampleBean.class, SampleExtensionBean.class, JavaBeanTester.PERFORM_CAN_EQUALS,
                JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void test_fullBeanNullExt() throws IntrospectionException, InstantiationException, IllegalAccessException {
        JavaBeanTester.test(SampleBean.class, null, JavaBeanTester.PERFORM_CAN_EQUALS,
                JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void test_fullBeanSkipUnderlyingData() throws IntrospectionException, InstantiationException,
            IllegalAccessException {
        JavaBeanTester.test(SampleBean.class, SampleExtensionBean.class, JavaBeanTester.PERFORM_CAN_EQUALS,
                !JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

    @Test
    public void test_skipBeanProperties() throws IntrospectionException, InstantiationException, IllegalAccessException {
        JavaBeanTester.test(SampleBean.class, SampleExtensionBean.class, JavaBeanTester.PERFORM_CAN_EQUALS,
                JavaBeanTester.LOAD_UNDERLYING_DATA, "string");
    }

    @Test
    public void test_skipCanEquals() throws IntrospectionException, InstantiationException, IllegalAccessException {
        JavaBeanTester.test(SampleBean.class, SampleExtensionBean.class, !JavaBeanTester.PERFORM_CAN_EQUALS,
                JavaBeanTester.LOAD_UNDERLYING_DATA);
    }

}
