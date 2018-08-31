package com.sims.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:TestapplicationContext.xml");
		DataSource bean = (DruidDataSource) context.getBean("dataSource");
		try {
			System.out.println(bean.getConnection());
		}catch (SQLException e){
			e.printStackTrace();
		}

	}

}
