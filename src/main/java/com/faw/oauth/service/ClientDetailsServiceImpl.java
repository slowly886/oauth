package com.faw.oauth.service;

import com.faw.oauth.dao.oauth.ClientDetailDao;
import com.faw.oauth.domian.entity.ClientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: ClientDetailsServiceImpl
 * @Package com.faw.oauth.service
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/24/0024 23:53
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/24/0024     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    ClientDetailDao clientDetailDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetail clientDetail = clientDetailDao.loadClientByClientId(clientId);
        if (clientDetail == null) {
            throw new ClientRegistrationException("没有找到client");
        }
        return clientDetail;
    }

    public void save(ClientDetail clientDetail) {
        clientDetailDao.insert(clientDetail);
    }

}
