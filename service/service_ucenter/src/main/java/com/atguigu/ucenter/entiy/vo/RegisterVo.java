package com.atguigu.ucenter.entiy.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVo {

    private String mobile;
    private String password;
    private String nickname;
    private String code;

}
