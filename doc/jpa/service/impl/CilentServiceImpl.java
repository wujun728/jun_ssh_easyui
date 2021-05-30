package com.erp.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.jpa.dao.ClientDao;
import com.erp.jpa.entity.Oauth2Client;
import com.erp.jpa.service.CilentService;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 12:35
 */
@Service
public class CilentServiceImpl implements CilentService {
    @Autowired
    private ClientDao clientDao;


    public Oauth2Client findOauth2UsersByName(String name) {
        return clientDao.findOauth2UsersByName(name);
    }

    @Override
    public void saveClient(Oauth2Client oc){
            clientDao.saveClient(oc);
         //  System.out.println(6/0);

    }
}
