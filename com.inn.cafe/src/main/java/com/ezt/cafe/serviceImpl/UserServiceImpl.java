package com.ezt.cafe.serviceImpl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezt.cafe.POJO.User;
import com.ezt.cafe.dao.UserDao;
import com.ezt.cafe.service.UserService;
import com.ezt.cafe.utils.CafeUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	/**
	 * (Postman) POST url: localhost:8082/user/signup Body|raw:
	 * {"name":"Tze","contactNumber":"02086633539","email":"tze@gmail.com",
	 * "password":"OpenSesame888"}
	 * 
	 */
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		CafeUtils.log("Inside signup", requestMap);
		
		if (requestMap.isEmpty()) {
			return CafeUtils.getResponseEntity("No data provided", HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (validateSignUpMap(requestMap)) {
				// check name
				User user = userDao.findByEmail(requestMap.get("email"));
				if (!Objects.isNull(user)) {
					return CafeUtils.getResponseEntity(
							String.format("User {%1$s} already exists", requestMap.get("name")),
							HttpStatus.BAD_REQUEST);
				}
				// check email
				user = userDao.findByEmail(requestMap.get("email"));
				if (!Objects.isNull(user)) {
					return CafeUtils.getResponseEntity(
							String.format("Email {%1$s} is already taken", requestMap.get("email")),
							HttpStatus.BAD_REQUEST);
				}
				
				// OK to proceed
				userDao.save(getUserFromMap(requestMap));
				return CafeUtils.getResponseEntity(
						String.format("Successfully registered user %1$s", requestMap.get("name")), HttpStatus.OK);

			} else {
				return CafeUtils.getResponseEntity("Oops, invalid user", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity("Problem with user service", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @param requestMap
	 * @return
	 */
	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (!requestMap.containsKey("name")) {
			CafeUtils.log("missing \'name\'");
			return false;
		}
		if (!requestMap.containsKey("contactNumber")) {
			CafeUtils.log("missing \'contactNumber\'");
			return false;
		}
		if (!requestMap.containsKey("email")) {
			CafeUtils.log("missing \'email\'");
			return false;
		}
		if (!requestMap.containsKey("password")) {
			CafeUtils.log("missing \'password\'");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param requestMap
	 * @return
	 */
	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus(requestMap.get("status"));
		user.setRole(requestMap.get("role"));
		return user;
	}
}
