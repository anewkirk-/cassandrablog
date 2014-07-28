package edu.neumont.dbt230.anewkirk.cassandra.model;

import java.util.GregorianCalendar;

public class BlogPost 
{
	private String postTitle;
	private String postAuthor;
	private String postContent;
	private int postID;
	private GregorianCalendar timeStamp;
	
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
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public GregorianCalendar getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(GregorianCalendar timeStamp) {
		this.timeStamp = timeStamp;
	}
}
