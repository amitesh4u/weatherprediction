/**
 * 
 */
package com.rii.wp.dao;

import com.rii.wp.exception.WPDataException;
import com.rii.wp.model.User;

/**
 * @author I
 *
 */
public interface AuthenticationDAO extends BaseDAO {
	
	User getUserDetails(String username, String password) throws WPDataException;

	int setUserDetails(User user) throws WPDataException;

}
