package com.ezt.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ezt.cafe.POJO.User;


public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmail(@Param("email") String email);
}
