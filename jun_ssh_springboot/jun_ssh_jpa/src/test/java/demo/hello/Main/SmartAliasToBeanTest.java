package demo.hello.Main;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;

import basetest.BaseTest;
import demo.jpa.pojo.CopyOfUser;
import klg.common.utils.MyPrinter2;
import klg.query.SmartAliasToBean;

public class SmartAliasToBeanTest extends BaseTest{
	@PersistenceContext
	EntityManager em;
	
	
	@Test
	public void  result2bean(){	
		//String sql="select userId,userName,account from user";
		String sql="select * from user";
		Query query=em.createNativeQuery(sql);
		query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(new SmartAliasToBean(CopyOfUser.class));
		MyPrinter2.print(query.getResultList());
	}
	
}
