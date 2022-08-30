package com.opencode.car.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AreaDO {

    private String cityName;
    private String cityCode;
    private String provinceCode;
    private String name;
    private String provinceName;

}
