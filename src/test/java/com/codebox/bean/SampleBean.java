package com.codebox.bean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import lombok.Data;

import org.slf4j.Logger;

@Data
public class SampleBean {

    private Logger                        logger;

    private EmptyBean                     emptyBean;
    private SampleDepthBean               sampleDepthBean;

    private List<String>                  list;
    private Map<String, String>           map;
    private ConcurrentMap<String, String> concurrentMap;
    private String                        string;
    private String[]                      stringArray;
    private Boolean                       booleanWrapper;
    private Integer                       intWrapper;
    private Long                          longWrapper;
    private Double                        doubleWrapper;
    private Float                         floatWrapper;
    private Character                     characterWrapper;
    private Byte                          byteWrapper;
    private Byte[]                        byteArray;
    private boolean                       booleanPrimitive;
    private int                           intPrimitive;
    private long                          longPrimitive;
    private double                        doublePrimitive;
    private float                         floatPrimitive;
    private char                          charPrimitive;
    private byte                          bytePrimitive;

}
