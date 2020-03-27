package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContextIT.xml")
public class MonTestIT {

	@Autowired
	ComptabiliteDaoImpl comptabiliteDaoImpl;

	@Test
	//@Sql({"classpath:create_schema.sql", "classpath:create_tables.sql", "classpath:insert_data.sql"})
	public void test(){

		List<CompteComptable> compteComptableList = comptabiliteDaoImpl.getListCompteComptable();

		System.out.println(">>>>>>>>>>>>><<<<< !!!!!!!!!!!!!!!!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>><");

	}

}
