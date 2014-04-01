package com.codebox.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.mockito.cglib.beans.ImmutableBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This helper class can be used to unit test the get/set/equals/canEqual/toString/hashCode methods of JavaBean-style
 * Value Objects.
 * 
 * @author rob.dawson
 * @author jeremy.landis
 */
public class JavaBeanTester {

    public final static boolean PERFORM_CAN_EQUALS   = true;
    public final static boolean LOAD_UNDERLYING_DATA = true;

    /**
     * Tests the equals/hashCode/toString methods of the specified class.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param <E>
     *            the type parameter associated with the extension class under test
     * @param clazz
     *            the class under test
     * @param extension
     *            extension of class under test
     * @throws IntrospectionException
     *             thrown if the JavaBeanTester.load method throws this exception for the class under test
     * @throws InstantiationException
     *             thrown if the clazz.newInstance() method throws this exception for the class under test
     * @throws IllegalAccessException
     *             thrown if the clazz.newIntances() method throws this exception for the class under test
     */
    public static <T, E> void equalsHashCodeToStringSymmetricTest(final Class<T> clazz, final Class<E> extension,
            final boolean loadUnderlyingData) throws IntrospectionException, InstantiationException,
            IllegalAccessException {
        // Create Instances
        final T x = clazz.newInstance();
        final T y = clazz.newInstance();
        E ext = null;
        if (extension != null) {
            ext = extension.newInstance();
        }

        // Test Empty Equals, HashCode, and ToString
        Assert.assertEquals(x, y);
        Assert.assertEquals(x.hashCode(), y.hashCode());
        Assert.assertEquals(x.toString(), y.toString());

        // Test Empty Equals, HashCode, and ToString
        if (ext != null) {
            Assert.assertNotEquals(ext, y);
            Assert.assertNotEquals(ext.hashCode(), y.hashCode());
        }

        // Test Empty One Sided Tests
        Assert.assertNotEquals(x, null);
        Assert.assertEquals(x, x);

        // Test Empty One Sided Tests
        if (ext != null) {
            Assert.assertNotEquals(ext, null);
            Assert.assertEquals(ext, ext);
        }

        // Populate Side X
        JavaBeanTester.load(clazz, x, loadUnderlyingData);

        // Populate Side E
        if (ext != null) {
            JavaBeanTester.load(extension, ext, loadUnderlyingData);
        }

        // ReTest Equals (flip)
        Assert.assertNotEquals(y, x);

        // ReTest Equals (flip)
        if (ext != null) {
            Assert.assertNotEquals(y, ext);
        }

        // Populate Size Y
        JavaBeanTester.load(clazz, y, loadUnderlyingData);

        // ReTest Equals and HashCode
        if (loadUnderlyingData) {
            Assert.assertEquals(x, y);
            Assert.assertEquals(x.hashCode(), y.hashCode());
        } else {
            Assert.assertNotEquals(x, y);
            Assert.assertNotEquals(x.hashCode(), y.hashCode());
        }

        // ReTest Equals and HashCode
        if (ext != null) {
            Assert.assertNotEquals(ext, y);
            Assert.assertNotEquals(ext.hashCode(), y.hashCode());
            Assert.assertNotEquals(ext.toString(), y.toString());
        }

        // Create Immutable Instance
        try {
            @SuppressWarnings("unchecked")
            final T e = (T) ImmutableBean.create(x);
            Assert.assertEquals(e, x);

            @SuppressWarnings("unchecked")
            final T e2 = (T) ImmutableBean.create(ext);
            Assert.assertEquals(e2, ext);
        } catch (final Exception e) {
            // Do nothing class is not mutable
        }
    }

    /**
     * Equals Tests will traverse one object changing values until all have been tested against another object. This is
     * done to effectively test all paths through equals.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param instance
     *            the class instance under test
     * @param expected
     *            the instance expected for tests
     * @param loadUnderlyingData
     *            load underlying data with values
     * @throws IntrospectionException
     *             thrown if the Introspector.getBeanInfo() method throws this exception for the class under test
     */
    public static <T> void equalsTests(final T instance, final T expected, final boolean loadUnderlyingData)
            throws IntrospectionException {

        // Perform hashCode test dependent on data coming in
        // Assert.assertEquals(expected.hashCode(), instance.hashCode());
        if (expected.hashCode() == instance.hashCode()) {
            Assert.assertEquals(expected.hashCode(), instance.hashCode());
        } else {
            Assert.assertNotEquals(expected.hashCode(), instance.hashCode());
        }

        final PropertyDescriptor[] props = Introspector.getBeanInfo(instance.getClass()).getPropertyDescriptors();
        for (final PropertyDescriptor prop : props) {
            final Method getter = prop.getReadMethod();
            final Method setter = prop.getWriteMethod();

            if (getter != null && setter != null) {
                // We have both a get and set method for this property
                final Class<?> returnType = getter.getReturnType();
                final Class<?>[] params = setter.getParameterTypes();

                if (params.length == 1 && params[0] == returnType) {
                    // The set method has 1 argument, which is of the same type as the return type of the get method, so
                    // we can test this property
                    try {
                        // Save original value
                        final Object original = getter.invoke(instance);

                        // Build a value of the correct type to be passed to the set method using alternate test
                        Object value = JavaBeanTester.buildValue(returnType, loadUnderlyingData, 1);

                        // Call the set method, then check the same value comes back out of the get method
                        setter.invoke(instance, value);

                        // Check equals depending on data
                        if (instance.equals(expected)) {
                            Assert.assertEquals(expected, instance);
                        } else {
                            Assert.assertNotEquals(expected, instance);
                        }

                        // Build a value of the correct type to be passed to the set method using null test
                        value = JavaBeanTester.buildValue(returnType, loadUnderlyingData, 2);

                        // Call the set method, then check the same value comes back out of the get method
                        setter.invoke(instance, value);

                        // Check equals depending on data
                        if (instance.equals(expected)) {
                            Assert.assertEquals(expected, instance);
                        } else {
                            Assert.assertNotEquals(expected, instance);
                        }

                        // Reset to original value
                        setter.invoke(instance, original);

                    } catch (final IllegalAccessException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final IllegalArgumentException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final InstantiationException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final InvocationTargetException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final SecurityException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    }
                }
            }
        }
    }

    /**
     * Tests the load methods of the specified class.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @param instance
     *            the instance of class under test
     * @param loadUnderlyingData
     *            load recursively all underlying data objects
     * @param skipThese
     *            the names of any properties that should not be tested
     * @throws IntrospectionException
     *             thrown if the JavaBeanTester.getterSetterTests method throws this exception for the class under test
     */
    public static <T> void load(final Class<T> clazz, final T instance, final boolean loadUnderlyingData,
            final String... skipThese) throws IntrospectionException {
        JavaBeanTester.getterSetterTests(clazz, instance, loadUnderlyingData, skipThese);
    }

    /**
     * Tests the get/set/equals/hashCode/toString methods of the specified class.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param <E>
     *            the type parameter associated with the extension class under test
     * @param clazz
     *            the class under test
     * @param checkEquals
     *            should equals be checked (use true unless good reason not to)
     * @param loadUnderlyingData
     *            load recursively all underlying data objects
     * @param skipThese
     *            the names of any properties that should not be tested
     * @throws IntrospectionException
     *             thrown if the JavaBeanTester.getterSetterTests or JavaBeanTester.equalsHashCodeToSTringSymmetricTest
     *             method throws this exception for the class under test
     * @throws IllegalAccessException
     *             thrown if the JavaBeanTester.getterSetterTests or clazz.newInstance() method throws this exception
     *             for the class under test
     * @throws InstantiationException
     *             thrown if the JavaBeanTester.getterSetterTests or JavaBeanTester.equalsHashCodeToSTringSymmetricTest
     *             method throws this exception for the class under test
     */
    public static <T, E> void test(final Class<T> clazz, final Class<E> extension, final boolean checkEquals,
            final boolean loadUnderlyingData, final String... skipThese) throws IntrospectionException,
            InstantiationException, IllegalAccessException {
        JavaBeanTester.getterSetterTests(clazz, clazz.newInstance(), loadUnderlyingData, skipThese);
        if (checkEquals) {
            JavaBeanTester.equalsHashCodeToStringSymmetricTest(clazz, extension, loadUnderlyingData);
        }
    }

    /**
     * Build Mock Value Tests.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @return Object this method is not currently implemented therefore null is returned
     */
    private static <T> Object buildMockValue(final Class<T> clazz) {
        if (!Modifier.isFinal(clazz.getModifiers())) {
            // Insert a call to your favorite mocking framework here
            return null;
        }
        return null;
    }

    /**
     * Build Value Tests. Will loop through recursively all objects.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @param loadUnderlyingData
     *            load recursively all underlying data objects
     * @param loadType
     *            0 = standard data load 1 = alternate data load 2 = null data load
     * @return Object value built from method for clazz
     * @throws InstantiationException
     *             thrown if the constructor.newIntances or JavaBeanTester.test method throws this exception for the
     *             class under test
     * @throws IllegalAccessException
     *             thrown if the constructor.newIntances or JavaBeanTester.test method throws this exception for the
     *             class under test
     * @throws IllegalArgumentException
     *             thrown if the constructor.newIntances method throws this exception for the class under test
     * @throws SecurityException
     *             thrown if the clazz.getConstructors() method throws this exception for the class under test
     * @throws InvocationTargetException
     *             thrown if the constructor.newIntances method throws this exception for the class under test
     */
    private static <T> Object buildValue(final Class<T> clazz, final boolean loadUnderlyingData, final int loadType)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException,
            InvocationTargetException {
        // If we are using a Mocking framework try that first...
        final Object mockedObject = JavaBeanTester.buildMockValue(clazz);
        if (mockedObject != null) {
            return mockedObject;
        }

        // Next check for a no-arg constructor
        final Constructor<?>[] ctrs = clazz.getConstructors();
        for (final Constructor<?> ctr : ctrs) {
            if (ctr.getParameterTypes().length == 0 && clazz != String.class) {
                if (loadUnderlyingData) {
                    // Load Underlying Data
                    try {
                        JavaBeanTester.getterSetterTests(clazz, clazz.newInstance(), loadUnderlyingData);
                    } catch (final IntrospectionException e) {
                        Assert.fail(String.format("An exception was thrown while testing the clazz %s: %s",
                                clazz.getName(), e.toString()));
                    }
                    return null;
                }
                // The class has a no-arg constructor, so just call it
                return ctr.newInstance();
            }
        }

        // Specific rules for common classes
        Object returnObject = null;
        switch (loadType) {
            case 1:
                returnObject = JavaBeanTester.setAlternateValues(clazz);
                break;
            case 2:
                returnObject = JavaBeanTester.setNullValues(clazz);
                break;
            default:
                returnObject = JavaBeanTester.setStandardValues(clazz);
                break;
        }
        if (returnObject != null || loadType == 2) {
            return returnObject;

        } else if (clazz.isAssignableFrom(List.class)) {
            return new ArrayList<Object>();

        } else if (clazz == Logger.class) {
            return LoggerFactory.getLogger(clazz);

            // XXX Add additional rules here

        } else {
            Assert.fail(String.format(
                    "Unable to build an instance of class %s, please add some code to the %s class to do this.",
                    clazz.getName(), JavaBeanTester.class.getName()));
            return null;
        }
    }

    /**
     * Getter Setter Tests.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @param instance
     *            the instance of class under test
     * @param loadUnderlyingData
     *            load underlying data
     * @param skipThese
     *            the names of any properties that should not be tested
     * @throws IntrospectionException
     *             thrown if the Introspector.getBeanInfo() method throws this exception for the class under test
     */
    private static <T> void getterSetterTests(final Class<T> clazz, final T instance, final boolean loadUnderlyingData,
            final String... skipThese) throws IntrospectionException {
        final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        nextProp: for (final PropertyDescriptor prop : props) {
            // Check the list of properties that we don't want to test
            for (final String skipThis : skipThese) {
                if (skipThis.equals(prop.getName())) {
                    continue nextProp;
                }
            }
            final Method getter = prop.getReadMethod();
            final Method setter = prop.getWriteMethod();

            if (getter != null && setter != null) {
                // We have both a get and set method for this property
                final Class<?> returnType = getter.getReturnType();
                final Class<?>[] params = setter.getParameterTypes();

                if (params.length == 1 && params[0] == returnType) {
                    // The set method has 1 argument, which is of the same type as the return type of the get method, so
                    // we can test this property
                    try {
                        // Build a value of the correct type to be passed to the set method
                        final Object value = JavaBeanTester.buildValue(returnType, loadUnderlyingData, 0);

                        // Call the set method, then check the same value comes back out of the get method
                        setter.invoke(instance, value);

                        final Object expectedValue = value;
                        final Object actualValue = getter.invoke(instance);

                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);

                    } catch (final IllegalAccessException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final IllegalArgumentException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final InstantiationException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final InvocationTargetException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    } catch (final SecurityException e) {
                        Assert.fail(String.format("An exception was thrown while testing the property %s: %s",
                                prop.getName(), e.toString()));
                    }
                }
            }
        }
    }

    /**
     * Set using alternate test values.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @return Object the Object to use for test
     */
    private static <T> Object setAlternateValues(final Class<T> clazz) {
        return JavaBeanTester.setValues(clazz, "ALT_VALUE", 1, Boolean.FALSE, Integer.valueOf(2), Long.valueOf(2),
                Double.valueOf(2.0), Float.valueOf(2.0F), Character.valueOf('N'));
    }

    /**
     * Set using null test values.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @return Object the Object to use for test
     */
    private static <T> Object setNullValues(final Class<T> clazz) {
        return JavaBeanTester.setValues(clazz, null, 0, null, null, null, null, null, null);
    }

    /**
     * Set using standard test values.
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class under test
     * @return Object the Object to use for test
     */
    private static <T> Object setStandardValues(final Class<T> clazz) {
        return JavaBeanTester.setValues(clazz, "TEST_VALUE", 1, Boolean.TRUE, Integer.valueOf(1), Long.valueOf(1),
                Double.valueOf(1.0), Float.valueOf(1.0F), Character.valueOf('Y'));
    }

    /**
     * Set Values for object
     * 
     * @param <T>
     *            the type parameter associated with the class under test
     * @param clazz
     *            the class instance under test
     * @param string
     *            value of string object
     * @param arrayLength
     *            amount of array objects to create
     * @param booleanValue
     *            value of boolean object
     * @param integerValue
     *            value of integer object
     * @param longValue
     *            value of long object
     * @param doubleValue
     *            value of double object
     * @param floatValue
     *            value of float object
     * @param characterValue
     *            value of character object
     * @return Object value determined by input class. If not found, returns null.
     */
    private static <T> Object setValues(final Class<T> clazz, final String string, final int arrayLength,
            final Boolean booleanValue, final Integer integerValue, final Long longValue, final Double doubleValue,
            final Float floatValue, final Character characterValue) {
        if (clazz == String.class) {
            return string;
        } else if (clazz.isArray()) {
            return Array.newInstance(clazz.getComponentType(), arrayLength);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            if (clazz == boolean.class && booleanValue == null) {
                return Boolean.FALSE;
            }
            return booleanValue;
        } else if (clazz == int.class || clazz == Integer.class) {
            if (clazz == int.class && integerValue == null) {
                return Integer.valueOf(-1);
            }
            return integerValue;
        } else if (clazz == long.class || clazz == Long.class) {
            if (clazz == long.class && longValue == null) {
                return Long.valueOf(-1);
            }
            return longValue;
        } else if (clazz == double.class || clazz == Double.class) {
            if (clazz == double.class && doubleValue == null) {
                return Double.valueOf(-1.0);
            }
            return doubleValue;
        } else if (clazz == float.class || clazz == Float.class) {
            if (clazz == float.class && floatValue == null) {
                return Float.valueOf(-1.0F);
            }
            return floatValue;
        } else if (clazz == char.class || clazz == Character.class) {
            if (clazz == char.class && characterValue == null) {
                return Character.valueOf('\u0000');
            }
            return characterValue;
        }
        return null;
    }

}
