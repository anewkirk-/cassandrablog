package edu.neumont.dbt230.anewkirk.cassandra.model;

public class BlogPost 
{
	private String postTitle;
	private String postAuthor;
	private String postContent;
	private String postID;
	private String timeStamp;
	
	public BlogPost() {}
	
	public BlogPost(String postID, String postTitle, String postAuthor, String postContent, String timeStamp) {
		this.postID = postID;
		this.postTitle = postTitle;
		this.postAuthor = postAuthor;
		this.postContent = postContent;
		this.timeStamp = timeStamp;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostAuthor() {
		return postAuthor;
	}
	public void setPostAuthor(String postAuthor) {
		this.postAuthor = postAuthor;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
