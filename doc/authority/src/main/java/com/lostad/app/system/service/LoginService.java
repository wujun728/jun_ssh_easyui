package com.lostad.app.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lostad.app.common.exception.ServiceException;
import com.lostad.app.security.util.MD5Utils;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.User;

@Service
@Transactional
public class LoginService {
    @Autowired
	private UserDao userDao ;
	public User login(String userName,String password) {
		User user = userDao.findByUsername(userName);
		if(user==null){
			throw new ServiceException("用户不存在！");
		}
		String pwd = MD5Utils.md5(user.getPassword());
		if(!user.getPassword().equals(pwd)){
			throw new ServiceException("密码错误！");
		}
		return user;
	}
}
