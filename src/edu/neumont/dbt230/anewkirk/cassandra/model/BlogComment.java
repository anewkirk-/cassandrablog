package edu.neumont.dbt230.anewkirk.cassandra.model;

public class BlogComment {
	private String commentAuthor;
	private String content;
	private String postID;
	private String timeStamp;
	
	public BlogComment(String author, String content, String postID, String timeStamp) {
		this.commentAuthor = author;
		this.content = content;
		this.postID = postID;
		this.timeStamp = timeStamp;
	}

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
