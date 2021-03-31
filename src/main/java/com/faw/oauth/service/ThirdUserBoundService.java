package com.faw.oauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.faw.oauth.dao.oauth.ThirdUserBoundDao;
import com.faw.oauth.domian.entity.ThirdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: ThirdUserBoundImpl
 * @Package com.faw.oauth.service
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/3/12/0012 17:31
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/3/12/0012     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Service
public class ThirdUserBoundService {
    @Autowired
    private ThirdUserBoundDao thirdUserBoundDao;

    public void userBound(Long user_id, String hw_user_id, String client_id) {
        ThirdUser thirdUser = new ThirdUser();
        thirdUser.setClientId(client_id);
        thirdUser.setUserId(user_id);
        thirdUser.setGranteeId(hw_user_id);

        thirdUserBoundDao.insert(thirdUser);
    }

    public void userRemove(Long user_id, String hw_user_id, String client_id) {
        ThirdUser thirdUser = new ThirdUser();
        thirdUser.setClientId(client_id);
        thirdUser.setUserId(user_id);
        thirdUser.setGranteeId(hw_user_id);

        int delete = thirdUserBoundDao.delete(new UpdateWrapper(thirdUser));
    }

    public ThirdUser query(Long user_id, String client_id) {
        ThirdUser thirdUser = new ThirdUser();
        thirdUser.setClientId(client_id);
        thirdUser.setUserId(user_id);
        return thirdUserBoundDao.selectOne(new QueryWrapper<>(thirdUser));
    }
}
