package com.faw.oauth.dao.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.faw.oauth.domian.entity.ThirdUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ThirdUserBoundDao extends BaseMapper<ThirdUser> {
}
