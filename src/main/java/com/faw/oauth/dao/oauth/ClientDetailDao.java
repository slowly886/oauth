package com.faw.oauth.dao.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.faw.oauth.domian.entity.ClientDetail;
import com.faw.oauth.domian.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: ClientDetailDao
 * @Package com.faw.oauth.dao.oauth
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/25/0025 1:09
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/25/0025     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Mapper
public interface ClientDetailDao extends BaseMapper<ClientDetail> {
    ClientDetail loadClientByClientId(@Param("clientId") String clientId);
}
