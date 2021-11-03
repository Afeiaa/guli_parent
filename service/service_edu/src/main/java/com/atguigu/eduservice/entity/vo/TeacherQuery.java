package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    private String name;
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2021-01-01 01:01:01")
    private String begin;
    private String end;

}
