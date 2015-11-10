package com.thhh.easy.posts.action;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.thhh.easy.entity.Comments;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.posts.service.ICommentsService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;

public class AndroidCommentsAction {

	private ICommentsService commentsService ;
	
	private int pageIndex; // 当前页数
	private int rowCount; // 每页显示条数
	
	private Posts posts ;	//贴子对象
	private Comments comments ;	//评论对象
	
	
	/**
	 * 查看评论
	 */
	public void seeComment(){
		if(posts == null || posts.getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(1);
			setRowCount(6);
		}
		List<Comments> listComments = commentsService.findCommentByPostsId(pageIndex,rowCount ,posts.getId()) ;
		MyUtil.sendString(new Gson().toJson(listComments)) ;
	}
	
	/**
	 * 添加评论
	 */
	public void addComments(){
		if(comments == null || comments.getUsers() == null || comments.getPosts() == null
				|| comments.getUsers().getId() == null || comments.getPosts().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		
		comments.setUsers(commentsService.findUserById(comments.getUsers().getId())) ;
		comments.setPosts(commentsService.findPostsById(comments.getPosts().getId())) ;
		comments.setDates(new Date()) ;
		comments.setId(null);
		commentsService.saveComments(comments) ;
		MyUtil.sendString(Constant.STRING_1) ;
		comments = null ;
	}


	public ICommentsService getCommentsService() {
		return commentsService;
	}


	public void setCommentsService(ICommentsService commentsService) {
		this.commentsService = commentsService;
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


	public Posts getPosts() {
		return posts;
	}


	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}
}
