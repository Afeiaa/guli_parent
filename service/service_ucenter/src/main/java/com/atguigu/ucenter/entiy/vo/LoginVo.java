package com.atguigu.ucenter.entiy.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    private String mobile;
    private String password;
    private String nickName;
}
