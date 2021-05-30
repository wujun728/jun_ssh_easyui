package com.erp.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.jpa.dao.UserDao;
import com.erp.jpa.entity.Oauth2User;
import com.erp.jpa.service.UserService;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 10:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Oauth2User findOauth2UsersByName(String username) {
        return userDao.findOauth2UsersByName(username);
    }

    @Override
    public void saveUser(Oauth2User u ) {
        userDao.save(u);
    }
}
