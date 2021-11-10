package com.atguigu.eduservice.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoVo {

    private String id;

    private String teacherId;

    private String subjectId;

    private String subjectParentId;

    private String title;

    // 0.01  两位小数的数据，用于价格
    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private String description;

}
