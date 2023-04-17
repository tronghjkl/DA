package com.globits.da.service.impl;

import com.globits.da.service.MyFirstApi;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class MyFirstApiServiceImpl implements MyFirstApi {
    @Override
    public String randomString() {
        return RandomStringUtils.randomAlphanumeric(10).toUpperCase();
    }

}
