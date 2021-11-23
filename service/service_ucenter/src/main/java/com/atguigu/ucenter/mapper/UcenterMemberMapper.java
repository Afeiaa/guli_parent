package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entiy.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer registerCount(@Param("day")String day);
}
