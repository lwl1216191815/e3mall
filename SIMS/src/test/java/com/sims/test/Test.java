package com.sims.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 测试spring容器是否创建成功
 * @author 龙帅
 *
 */
public class Test {

	private static ApplicationContext applicationContext;
	static {
		applicationContext= new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
	}
    @org.junit.Test
	public void testSpringIoc() {
		DataSource bean = (DruidDataSource) applicationContext.getBean("dataSource");
		try {
			System.out.println(bean.getConnection());
		}catch (SQLException e){
			e.printStackTrace();
		}

	}

}
