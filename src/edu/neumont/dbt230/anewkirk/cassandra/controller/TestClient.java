package edu.neumont.dbt230.anewkirk.cassandra.controller;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import edu.neumont.dbt230.anewkirk.cassandra.model.BlogPost;

public class TestClient {

	private Cluster cluster;
	private Session session;

	private void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out
				.println("Connected to cluster: " + metadata.getClusterName());
		session = cluster.connect();
	}
	
	public void createSchema() {
		
//		System.out.println("[+] Creating keyspace....");
//		session.execute("CREATE KEYSPACE \"blog\" WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':3}");
		
//		System.out.println("[+] Creating columnfamily blog.users...");
//		session.execute("CREATE COLUMNFAMILY blog.users (" +  "username text," +  "firstname text," + "lastname text," + "PRIMARY KEY (username)" + ");");
		
//		System.out.println("[+] Creating columnfamily blog.posts...");
//		session.execute("CREATE COLUMNFAMILY blog.posts (" + "id uuid," +
//		"username text," + "title text," + "content text," + "PRIMARY KEY (id, username)" + ");");
//		
//		System.out.println("[+] Creating columnfamily blog.comments...");
//		session.execute("CREATE COLUMNFAMILY blog.comments(" + "id timeuuid PRIMARY KEY," +
//		"username text," + "content text," + "postid text" + ");");
		
	}
	
	public void createTestPost() {
		System.out.println("Inserting test blog post by testuser");
		session.execute("INSERT INTO blog.posts (id, username, title, content) " +  "VALUES (7c2766e0-1696-11e4-8c21-0800200c9a66, 'testuser', 'This is a title', 'some blog content');");
		
	}
	
	public void createTestUser() {
		System.out.println("Inserting user \"testuser\"");
		session.execute("INSERT INTO blog.users (username, firstname, lastname) " + "VALUES (" + "'testuser'," + "'Test'," + "'User'" + ");");
	}
	
	public void queryBlogUsers() {
		ResultSet results = session.execute("SELECT * FROM blog.users;");
		System.out.println(String.format("%-30s", "username"));
			for (Row row : results) {
			    System.out.println(String.format("%-30s\n%s", "-------------------------------|", row.getString("username")));
			}
			System.out.println();
	}
	
	public void queryBlogPosts() {
		ResultSet results = session.execute("SELECT * FROM blog.posts;");
		System.out.println(String.format(""));
		for (Row row : results) {
			System.out.println(String.format("%s\t%s\t%s\t\t%s", row.getUUID("id"), row.getString("username"), row.getString("title"), row.getString("content")));
		}
	}
	
	public void insertNewUser(String username) {
		//TODO
	}
	
	public void insertNewPost(BlogPost post) {
		//TODO
	}
	
	public boolean userExists(String username) {
		//TODO
		return false;
	}

	private void close() {
		cluster.close();
	}
	
	public static void main(String[] args) {
		try {
			TestClient client = new TestClient();
			
			//Azure box public IP
			client.connect("137.135.56.72");
			
//			client.createSchema();
			
//			client.createTestPost();
//			client.createTestUser();
			
			client.queryBlogPosts();
//			client.queryBlogUsers();
			
			
			client.close();
			System.out.println("[!] Done.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}