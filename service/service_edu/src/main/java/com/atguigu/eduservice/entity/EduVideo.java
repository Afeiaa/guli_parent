package com.atguigu.eduservice.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String courseId;

    private String chapterId;

    private String title;

    private String videoSourceId;

    private String videoOriginalName;

    private Integer sort;

    private Long playCount;

    private Boolean isFree;

    private Float duration;

    private String status;

    private Long size;

    private Long version;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
