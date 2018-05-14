package com.jeesite.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeesite.modules.counter.dao.TbCounterDao;
import com.jeesite.modules.counter.entity.TbCounter;

public class Test {

	public static void main(String[] args) {
		
	}
	
	@Autowired
	TbCounterDao tbCounterDao;
	
	@org.junit.Test
	public void test(){
		TbCounter counter=new TbCounter();
		counter.setCode("contact_code");
		counter=tbCounterDao.findList(counter).get(0);
		System.err.println(counter.getValue());
	}
}
