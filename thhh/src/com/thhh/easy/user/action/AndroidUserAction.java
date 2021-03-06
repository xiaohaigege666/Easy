package com.thhh.easy.user.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;
import com.thhh.easy.user.service.IUserService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;

public class AndroidUserAction {

	private Users user ;
	private Image image ;
	private IUserService userService ;
	private Posts post ;
	private Collects collect ;
	private int pageIndex; // 当前页数
	private int rowCount; // 每页显示条数
	private String uploadFileName;
	private File upload ;
	
	/**
	 * 用户登录检测
	 */
	public void loginCheck(){
//		user = new Users() ;
//		user.setId(2);
//		user.setName("aaaaaa") ;
//		user.setPwd("123456") ;
		if(user == null || user.getName() == null || "".equals(user.getName())){
			MyUtil.sendString(Constant.STRING_0) ;
			user = null ;
			return ;
		}
		Users u = userService.findUserByName(user.getName()) ;
		if(u != null && user.getPwd().equals(u.getPwd())){
			//用户合法，则向客户端发送用户对象
			MyUtil.sendString(u) ;
		}else{
			//用户不合法
			MyUtil.sendString(Constant.STRING_0) ;
		}
		user = null ;
	}
	/**
	 * 用户注册
	 */
	public void register(){
		user = new Users();
//		user.setRp(0) ;
//		user.setName("cccccc") ;
//		user.setPwd("123456") ;
		if(user.getName() != null && user.getPwd() != null){
			Users u = userService.findUserByName(user.getName()) ;
			if(u != null){
				//此用户名已存在
				MyUtil.sendString(Constant.STRING_0) ;
				return ;
			}else{
				userService.save(user) ;
				MyUtil.sendString(Constant.STRING_1) ;
			}
		}else{
			MyUtil.sendString(Constant.STRING_0) ;
		}
		user = null ;
	}
	/**
	 * 查询用户的个人信息
	 */
	public void userInfor(){
		user = new Users();
//		user.setId(2);
//		user.setName("aaaaaa") ;
//		user.setPwd("123456") ;
		
		//从客户端接收到用户名，在数据库中查询是否存在该用户
		Users u=userService.findUserByName(user.getName());
		if (u!=null) {
			int postCount;
			postCount=userService.findPostCount(u.getId());
			MyUtil.sendString(u) ;
			
		}else {
			MyUtil.sendString(null);
		}
		
	}
	/**
	 * 用户的发帖数
	 */
	public void findPostCount(){

		Users u = userService.findUserByName(user.getName());
//		user = new Users();
//		user.setId(2);
//		user.setName("aaaaaa") ;
//		user.setPwd("123456") ;
//		Users u = userService.findUserByName(user.getName());

		if (getPageIndex() == 0 || getRowCount() == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		if (u != null) {
			int postCount = 0;
			postCount=userService.findPostCount(u.getId());
			List<Posts> listPost = userService.findUserPosts(u.getId());
			List<Map> list = new ArrayList<Map>();
			for (Posts posts : listPost) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("count", postCount);
				map.put("posts", posts) ;
				list.add(map);
			}
			MyUtil.sendString(list) ;
//			MyUtil.sendJSONOArray(list.toArray());
		}
	}
	/**
	 * 用户收藏的帖子数
	 */
	public void findCollectCount(){
		user = new Users();
		user.setId(2);
		user.setName("aaaaaa") ;
		user.setPwd("123456") ;
		Users u = userService.findUserByName(user.getName());
		if (getPageIndex() == 0 || getRowCount() == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		if (u != null) {
			int collectCount = 0;
			collectCount=userService.findCollectCount(u.getId());
			List<Collects> listCollect = userService.findUserCollects(u.getId());
			List<Map> list = new ArrayList<Map>();
			for (Collects collect : listCollect) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("collects", collectCount);
				map.put("collect",collect);
				list.add(map) ;
			}
			MyUtil.sendString(list) ;
//			MyUtil.sendJSONOArray(list.toArray());
		}
	}
	
	/**
	 * 修改用户的个人信息
	 */
	public void update(){
		user = new Users();
//		user.setId(7);
//		user.setName("jjjjjj") ;
//		user.setPwd("111111") ;
		user.setName("aaaaaa") ;
		user.setPwd("1234567");
		user.setNickname("小小");
		Users u=userService.findUserByName(user.getName());
		if (u != null) {
//			user.setName("aaaaaa") ;
//			user.setPwd("1234567");
//			user.setNickname("小小");
			Image image = new Image();
			image.setId(1);
			user.setImage(image);
//			user.setRp(0);
			userService.updateUsers(user);
			Users user=userService.findUserByName("aaaaaa");
			System.out.println("修改后的用户信息："+user.getName()+","+user.getPwd()+","+user.getNickname());
			MyUtil.sendString(user);
		}else {
			MyUtil.sendString(null);
		}
	}
	/**
	 * 查看我的图片
	 */
	public void myImage(){
		user = new Users();
		user.setId(3);
		user.setName("hahaha") ;
		user.setPwd("123456") ;
		Users u = userService.findUserByName(user.getName());
		if (u != null) {
			List<Image> listImage=userService.findUserImage(user.getId());
			for (Image image : listImage) {
				listImage.add(image);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("listImage", listImage);
			MyUtil.sendString(map);
		}else {
			MyUtil.sendString(null);
		}
	}
	/**
	 * 图片的上传
	 */
	public void upload(){
		user = new Users();
		user.setId(2);
		user.setName("aaaaaa") ;
		user.setPwd("123456") ;
		Users u = userService.findUserByName(user.getName());
		if (u != null) {
			upload = new File("D:\\tupian\\gg.jpg");
			if(upload!=null){
				//获得文件上传的磁盘相对路径
				 String realPath = ServletActionContext.getServletContext().getRealPath("/image/imagePerson/");
				 //创建一个文件
				 File diskFile = new File(realPath+"//"+uploadFileName);
				 
				 //文件上传
				 try {
					FileUtils.copyFile(upload, diskFile);//upload是源文件
				} catch (IOException e) {
					e.printStackTrace();
				}
			image.setUrls("imagePerson/"+uploadFileName);//setUrls中存放：路径加文件名
			userService.save(u);
			MyUtil.sendString(u);
			}		
		}
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	public Posts getPost() {
		return post;
	}
	public void setPost(Posts post) {
		this.post = post;
	}
	public Collects getCollect() {
		return collect;
	}
	public void setCollect(Collects collect) {
		this.collect = collect;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
