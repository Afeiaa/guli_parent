package com.atguigu.msm.service;

import java.util.Map;

public interface MsmService {
    boolean sendCode(String phone, String SMS_228015620, Map<String, Object> param);

    boolean sendCode1(String phone);
}
