package com.opencode.car.entity.spider.brand;

import lombok.Data;

import java.util.List;

@Data
public class BrandResult {

    private String returncode;
    private String message;
    private BrandList result;

}

