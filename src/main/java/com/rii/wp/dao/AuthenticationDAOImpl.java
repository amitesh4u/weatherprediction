/**
 * 
 */
package com.rii.wp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rii.wp.exception.WPDataException;
import com.rii.wp.model.User;

/**
 * @author I
 *
 */
public class AuthenticationDAOImpl implements AuthenticationDAO {
	
	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationDAOImpl.class);

	private static final int STATUS_SUCCESS = 0;

	private static final int ERR_USER_EXISTS = 1;

	private static final int ERR_DATA_INSERTION_ERROR = 2;
	
	@Autowired  
	DataSource dataSource; 
	
	@Override  
	 public User getUserDetails(final String emailId, final String password) throws WPDataException {  
		String sql = "select user_id, f_name, l_name, email_Id from users where email_Id=? and usr_pwd = md5(?) and is_active='Y'";
		List<User> users = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			users = jdbcTemplate.query(
				sql,
			    new Object[] {emailId, password},
			    new RowMapper<User>() {
			        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	User u = new User();
			        	u.setUserId(rs.getString(1));
			            u.setFirstName(rs.getString(2));
			            u.setLastName(rs.getString(3));
			            u.setEmailId(rs.getString(4));
			            return u;
			        }
			    });
		}catch(Exception e){
			throw new WPDataException("Error fetching User details.", e);
		}
		User user = null;
		if(users != null && users.size() == 1){
			user = users.get(0);
		}
		return user;
	}

	/**
	 * INSERT INTO users(
            email_id, f_name, l_name, usr_pwd, cntry_a2, ph_cntry_cd, 
            ph_no, usr_cat_cd, hnt_ques_cd, hnt_ans, reg_date, is_active)
    VALUES ( ?, ?, ?, md5(?), ?, ?, 
            ?, ?, ?, ?, ?, ?);
            
	 * @param user
	 * @throws WPDataException 
	 */
	@Override  
	 public int setUserDetails(final User user) throws WPDataException {  
		String sql = "INSERT INTO users(email_id, f_name, l_name, usr_pwd, is_active)" +
					" VALUES (?,?,?,md5(?),?); ";
		int status = STATUS_SUCCESS;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			int rowCount = jdbcTemplate.update(sql,
			    new Object[] {user.getEmailId(), user.getFirstName(), user.getLastName(), user.getPassword(), "Y"});
			if(rowCount <= 0 ){
				status = 1; // Error in Data insertion
			}
		}catch(DuplicateKeyException e){
			logger.error("Duplicate entry. ", e);
			status = ERR_USER_EXISTS;
		}
		catch(Exception e){
			e.printStackTrace();
			//throw new WPDataException("Error Inserting records in User table.", e);
			status = ERR_DATA_INSERTION_ERROR;
		}
		return status;
	}
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}  
	
}
