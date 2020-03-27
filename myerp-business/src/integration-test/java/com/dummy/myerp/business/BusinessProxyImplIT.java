package com.dummy.myerp.business;

import com.dummy.myerp.business.contrat.BusinessProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations= "/com/dummy/myerp/business/applicationContext.xml")
public class BusinessProxyImplIT {

	@Autowired
	BusinessProxy businessProxy;

	@Before
	public void setUp(){

	}

	@Test
	public void testCheckEcriture(){

		businessProxy.getComptabiliteManager().getListCompteComptable();

	}


}
