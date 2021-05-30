package com.erp.jpa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.erp.jpa.dao.ClientDao;
import com.erp.jpa.entity.Oauth2Client;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 11:31
 */
@Repository
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveClient(Oauth2Client oc) {
        this.entityManager.merge(oc);
    }

    @Override
    public Oauth2Client findOauth2UsersByName(String name) {
        String hql =" select c from  Oauth2Client c where c.clientName=:clientName";
         Query query =   this.entityManager.createQuery(hql)
        .setParameter("clientName",name);
        return (Oauth2Client) query.getSingleResult();
    }
}
