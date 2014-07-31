package edu.neumont.dbt230.anewkirk.cassandra.controller;

import java.util.ArrayList;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import edu.neumont.dbt230.anewkirk.cassandra.model.BlogComment;
import edu.neumont.dbt230.anewkirk.cassandra.model.BlogPost;
import edu.neumont.dbt230.anewkirk.cassandra.model.User;

public class BlogClient {

	private Cluster cluster;
	private Session session;

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out
				.println("Connected to cluster: " + metadata.getClusterName());
		session = cluster.connect();
	}

	public void createSchema() {

		System.out.println("[+] Creating keyspace....");
		session.execute("CREATE KEYSPACE \"blog\" WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':3}");

		System.out.println("[+] Creating columnfamily blog.users...");
		session.execute("CREATE COLUMNFAMILY blog.users (" + "username text,"
				+ "firstname text," + "lastname text,"
				+ "PRIMARY KEY (username)" + ");");

		System.out.println("[+] Creating columnfamily blog.posts...");
		session.execute("CREATE COLUMNFAMILY blog.posts (" + "id uuid,"
				+ "username text," + "title text," + "content text,"
				+ "timestamp text," + "PRIMARY KEY (id, username)" + ");");

		System.out.println("[+] Creating columnfamily blog.comments...");
		session.execute("CREATE COLUMNFAMILY blog.comments(" + "username text,"
				+ "content text," + "postid uuid," + "timestamp text,"
				+ " PRIMARY KEY(postid, timestamp));");

	}
	
	public User getUser(String userName)
	{
		Row user = session.execute("SELECT * FROM blog.users WHERE username = '" + userName + "'" ).one();
		return new User(userName, user.getString("firstname"), user.getString("lastname"));		
	}

	public void deleteSchema() {
		session.execute("DROP KEYSPACE blog;");
	}

	public void createTestPost() {
		System.out.println("Inserting test blog post by testuser");
		session.execute("INSERT INTO blog.posts (id, username, title, content) "
				+ "VALUES (7c2766e0-1696-11e4-8c21-0800200c9a66, 'testuser', 'This is a title', 'some blog content');");

	}

	public void createTestUser() {
		System.out.println("Inserting user \"testuser\"");
		session.execute("INSERT INTO blog.users (username, firstname, lastname) "
				+ "VALUES (" + "'testuser'," + "'Test'," + "'User'" + ");");
	}

	public void queryBlogUsers() {
		ResultSet results = session.execute("SELECT * FROM blog.users;");
		System.out.println(String.format("%-30s", "username"));
		for (Row row : results) {
			System.out.println(String.format("%-30s\n%s",
					"-------------------------------|",
					row.getString("username")));
		}
		System.out.println();
	}

	public BlogPost loadPost(String username, String title) {
		ResultSet results = session
				.execute("SELECT * FROM blog.posts WHERE username = '"
						+ username + "' AND title = '" + title + "';");
		String content = null, id = null, timestamp = null;
		for (Row row : results) {
			content = row.getString("content");
			id = row.getUUID("id").toString();
			timestamp = row.getString("timestamp");
		}
		return new BlogPost(id, username, title, content, timestamp);
	}

	public ArrayList<BlogPost> queryBlogPosts() {
		ArrayList<BlogPost> posts = new ArrayList<BlogPost>();
		ResultSet results = session.execute("SELECT * FROM blog.posts;");
		for (Row row : results) {
			posts.add(new BlogPost(row.getUUID("id").toString(), row
					.getString("username"), row.getString("title"), row
					.getString("content"), row.getString("timestamp")));
		}
		return posts;
	}

	public ArrayList<BlogComment> queryComments(String postid) {
		ArrayList<BlogComment> comments = new ArrayList<BlogComment>();
		ResultSet results = session
				.execute("SELECT * FROM blog.comments WHERE " + "postid = "
						+ postid + ";");
		for (Row row : results) {
			comments.add(new BlogComment(row.getString("username"), row
					.getString("content"), row.getUUID("postid").toString(),
					row.getString("timestamp")));
		}
		return comments;
	}

	public void insertNewUser(User user) {
		session.execute("INSERT INTO blog.users(username, firstname, lastname) VALUES ('"
				+ user.getUsername()
				+ "','"
				+ user.getFirstname()
				+ "','"
				+ user.getLastname() + "');");
	}

	public void insertNewPost(BlogPost post) {
		session.execute("INSERT INTO blog.posts(id, username, title, content, timestamp) VALUES ("
				+ post.getPostID()
				+ ", '"
				+ post.getPostAuthor()
				+ "', '"
				+ post.getPostTitle()
				+ "', '"
				+ post.getPostContent()
				+ "', '"
				+ post.getTimeStamp() + "');");
	}

	public void insertNewComment(BlogComment comment) {
		session.execute("INSERT INTO blog.comments(username, content, postid, timestamp) VALUES ('"
				+ comment.getAuthor()
				+ "', '"
				+ comment.getContent()
				+ "', "
				+ comment.getPostID() + ", '" + comment.getTimeStamp() + "');");
	}

	public boolean userExists(String username) {
		ResultSet results = session
				.execute("SELECT * FROM blog.users WHERE username = '"
						+ username + "';");
		return results.all().size() > 0;
	}

	public void close() {
		cluster.close();
	}

	public static void main(String[] args) {
		try {
			BlogClient client = new BlogClient();

			// Azure box public IP
			client.connect("137.135.56.72");

//			client.createSchema();
//			client.createTestPost();
//			client.createTestUser();
//			client.queryBlogPosts();
//			client.queryBlogUsers();
//			client.deleteSchema();
			
			client.close();
			System.out.println("[!] Done.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}