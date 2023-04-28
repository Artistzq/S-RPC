package com.kerbalogy.srpc.api.services;

import java.util.Date;

/**
 * @Author : Artis Yao
 */
public class TimeServiceImpl implements TimeService {
    @Override
    public String getCurrentTime() {
        return "Current time is: " + new Date().toString();
    }
}
