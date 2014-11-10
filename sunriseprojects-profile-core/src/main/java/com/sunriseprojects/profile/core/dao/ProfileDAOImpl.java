package com.sunriseprojects.profile.core.dao;

import javax.sql.DataSource; 
import org.springframework.jdbc.core.JdbcTemplate;

//http://www.journaldev.com/2603/spring-transaction-management-example-with-jdbc

public class ProfileDAOImpl implements ProfileDAO {

    private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
	@Override
	public void insertMember(String firstName, String lastName, String email,
			String password) {
		
        String queryAuth = "insert into member_auth (email, passwd, member_number) values (?,?,?)";
        String queryProfile = "insert into member_profile (email, firstname, lastname) values (?,?,?)";
 
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
        jdbcTemplate.update(queryAuth, new Object[] { email,
        		password, password });
        System.out.println("Inserted into Table Successfully");
        
        jdbcTemplate.update(queryProfile, new Object[] { email,
        		firstName,
        		lastName });
        System.out.println("Inserted into queryProfile Table Successfully");
        
		
	}

}
