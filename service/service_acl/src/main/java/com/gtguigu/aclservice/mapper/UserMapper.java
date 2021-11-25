package com.gtguigu.aclservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gtguigu.aclservice.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
