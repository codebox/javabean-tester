package com.codebox.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SampleExtensionBean extends SampleBean {
    private String extension;
}
