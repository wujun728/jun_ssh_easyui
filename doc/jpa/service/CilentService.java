package com.erp.jpa.service;

import com.erp.jpa.entity.Oauth2Client;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 12:34
 */
public interface CilentService {
    Oauth2Client findOauth2UsersByName(String name);
    void  saveClient(Oauth2Client oc) ;
}
