package net.codebox.javabeantester.beans;

import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by rob on 13/08/2017.
 */
public class Bean {
    private String stringValue;
    private Object[] arrayValue;
    private boolean booleanValue;
    private Boolean booleanWrapperValue;
    private int intValue;
    private Integer intWrapperValue;
    private long longValue;
    private Long longWrapperValue;
    private double doubleValue;
    private Double doubleWrapperValue;
    private float floatValue;
    private Float floatWrapperValue;
    private char charValue;
    private Character charWrapperValue;
    private RetentionPolicy enumValue;
    private Date noArgConstructorObjectValue;

    private byte byteValue;
    private Byte byteWrapperValue;
    private List<Object> listValue;
    private Set<Object> setValue;
    private Map<Object,Object> mapValue;
    
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Object[] getArrayValue() {
        return arrayValue;
    }

    public void setArrayValue(Object[] arrayValue) {
        this.arrayValue = arrayValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getBooleanWrapperValue() {
        return booleanWrapperValue;
    }

    public void setBooleanWrapperValue(Boolean booleanWrapperValue) {
        this.booleanWrapperValue = booleanWrapperValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Integer getIntWrapperValue() {
        return intWrapperValue;
    }

    public void setIntWrapperValue(Integer intWrapperValue) {
        this.intWrapperValue = intWrapperValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public Long getLongWrapperValue() {
        return longWrapperValue;
    }

    public void setLongWrapperValue(Long longWrapperValue) {
        this.longWrapperValue = longWrapperValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Double getDoubleWrapperValue() {
        return doubleWrapperValue;
    }

    public void setDoubleWrapperValue(Double doubleWrapperValue) {
        this.doubleWrapperValue = doubleWrapperValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public Float getFloatWrapperValue() {
        return floatWrapperValue;
    }

    public void setFloatWrapperValue(Float floatWrapperValue) {
        this.floatWrapperValue = floatWrapperValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public Character getCharWrapperValue() {
        return charWrapperValue;
    }

    public void setCharWrapperValue(Character charWrapperValue) {
        this.charWrapperValue = charWrapperValue;
    }

    public RetentionPolicy getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(RetentionPolicy enumValue) {
        this.enumValue = enumValue;
    }

    public Date getNoArgConstructorObjectValue() {
        return noArgConstructorObjectValue;
    }

    public void setNoArgConstructorObjectValue(Date noArgConstructorObjectValue) {
        this.noArgConstructorObjectValue = noArgConstructorObjectValue;
    }
    public String getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }
    public String getByteWrapperValue() {
        return byteWrapperValue;
    }

    public void setByteWrapperValue(byte byteWrapperValue) {
        this.byteWrapperValue = byteWrapperValue;
    }
    public List<?> getListValue() {
        return listValue;
    }

    public void setListValue(List<?> listValue) {
        this.listValue = listValue;
    }
    public Set<?> getSetValue() {
        return setValue;
    }

    public void setSetValue(Set<?> setValue) {
        this.setValue = setValue;
    }
    public Map<?,?> getMapValue() {
        return mapValue;
    }

    public void setMapValue(Map<?,?> mapValue) {
        this.mapValue = mapValue;
    }
}
