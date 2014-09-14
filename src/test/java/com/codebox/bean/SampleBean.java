package com.codebox.bean;

import java.util.List;
import java.util.Map;

import lombok.Data;

import org.slf4j.Logger;

@Data
public class SampleBean {

    private Logger              logger;

    private EmptyBean           emptyBean;
    private SampleDepthBean     sampleDepthBean;

    private List<String>        list;
    private Map<String, String> map;
    private String              string;
    private String[]            stringArray;
    private Boolean             booleanWrapper;
    private Integer             intWrapper;
    private Long                longWrapper;
    private Double              doubleWrapper;
    private Float               floatWrapper;
    private Character           characterWrapper;
    private boolean             booleanPrimitive;
    private int                 intPrimitive;
    private long                longPrimitive;
    private double              doublePrimitive;
    private float               floatPrimitive;
    private char                charPrimitive;

}