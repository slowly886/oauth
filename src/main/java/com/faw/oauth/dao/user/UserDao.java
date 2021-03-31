package com.faw.oauth.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.faw.oauth.domian.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User> {
    User selecUserByUserName(@Param("username") String username);
}
