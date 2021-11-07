package com.atguigu.eduservice.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneSubject {

    private String id;
    private String title;

    List<TwoSubject> children = new ArrayList<>();

}
