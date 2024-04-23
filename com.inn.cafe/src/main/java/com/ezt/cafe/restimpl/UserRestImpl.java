package com.ezt.cafe.restimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ezt.cafe.rest.UserRest;
import com.ezt.cafe.service.UserService;
import com.ezt.cafe.utils.CafeUtils;

@RestController
public class UserRestImpl implements UserRest {

	@Autowired
	UserService userService;
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try {
			
			return userService.signUp(requestMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity( "Problem with user REST", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
