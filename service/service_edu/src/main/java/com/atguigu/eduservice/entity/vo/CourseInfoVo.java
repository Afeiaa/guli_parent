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

    // 如果测试接口报这个字段的错误，正常使用不需要传，但是数据库表中有，所以测试接口需要传
//    private String subjectParentId;

    private String title;

    // 0.01  两位小数的数据，用于价格
    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private String description;

}
