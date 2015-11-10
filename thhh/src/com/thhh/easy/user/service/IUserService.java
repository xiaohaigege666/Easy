package com.thhh.easy.user.service;

import java.util.List;

import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;

public interface IUserService {

	Users findUserById(int id);

	Users findUserByName(String name);
	
	Users findAll();

	void save(Users user);

	Users updateUsers(Users u);
	
	int findPostCount(Integer id);
	
	int findCollectCount(Integer id);
	
	List<Posts> findUserPosts(Integer userId);
	
	List<Image> findByProperty(String propertyName, Object value);
	
	List<Collects> findUserCollects(Integer userId);

	List<Image> findUserImage(Integer id);
}