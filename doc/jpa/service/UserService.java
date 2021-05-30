package com.erp.jpa.service;

import com.erp.jpa.entity.Oauth2User;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 10:18
 */
public interface UserService {

    Oauth2User findOauth2UsersByName(String username);
    void  saveUser(Oauth2User u) throws Exception;



}
