package edu.neumont.dbt230.anewkirk.cassandra.model;

import java.util.GregorianCalendar;

public class BlogComment 
{
	private String commentAuthor;
	private String content;
	private String title;
	private int postID;
	private GregorianCalendar timeStamp;
	
	public String getAuthor() {
		return commentAuthor;
	}
	public void setAuthor(String author) {
		this.commentAuthor = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
