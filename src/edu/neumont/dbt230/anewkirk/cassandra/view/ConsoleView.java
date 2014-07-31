package edu.neumont.dbt230.anewkirk.cassandra.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.datastax.driver.core.Session;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import edu.neumont.dbt230.anewkirk.cassandra.controller.BlogClient;
import edu.neumont.dbt230.anewkirk.cassandra.model.BlogComment;
import edu.neumont.dbt230.anewkirk.cassandra.model.BlogPost;
import edu.neumont.dbt230.anewkirk.cassandra.model.User;


public class ConsoleView {
	
	private static Scanner sc = new Scanner(System.in);
	private static User user = new User("testuser", "Test", "User");
	private static BlogClient client = new BlogClient();
	private static Random gen = new Random();
	private static final String thinSeparator = "--------------------------------------";
	private static final String thickSeparator = "======================================";
	
	public static void main(String[] args) {
		client.connect("137.135.56.72");
		
		//login
		
		//menustuffhere
		loginCheck();
		mainMenu();
		client.close();
	}
	
	public static void mainMenu() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Quit");
		options.add("New Post");
		options.add("List all posts");
		int choice = -1;
		while(choice != 0){
			choice = getMenuSelection("Select an option:",options);
			switch(choice) {
			case 0:
				System.exit(0);
				break;
			case 1:
				postNew();
				break;
			case 2: 
				viewAll();
				break;
			}
		}
	}
	
	public static void postNew() {
		String title = getStringFromUser("Enter title for new post...");
		String content = getStringFromUser("Enter blog post...");
		System.out.println(title);
		System.out.println(thinSeparator);
		System.out.println(content);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Post!");
		options.add("Try again");
		options.add("Cancel");
		switch(getMenuSelection("Submit post?", options)) {
		case 0:
			client.insertNewPost(new BlogPost(buildUUID(), title, user.getUsername(), content, buildTimestamp()));
			break;
		case 1:
			postNew();
			break;
		case 2:
			break;
		}
	}
	public static void loginCheck()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Login");
		options.add("Create New User");
		switch( getMenuSelection("::Hello::", options))
		{
			case 0:
				String uName = getStringFromUser("::Whats your UserName?::");
				if(isValidUser(uName))
				{
					user = client.getUser(uName);
					System.out.println(String.format("Welcome : %s", uName));
				}
				else
				{
					System.out.println(String.format("%s is not a valid UserName", uName));
					loginCheck();
				}
				break;
			case 1:
				User newUser = createNewUser(askForUserCreds());
				client.insertNewUser(newUser);
				user = newUser;
				break;
			default:
				System.out.println("There was an internal indexing error in loginCheck()");
				break;
					
		}
		
	}
	
	public static boolean isValidUser(String userName)
	{
		return client.userExists(userName);
	}
	
	public static List<String> askForUserCreds()
	{
		List<String> creds = new ArrayList<String>();
		String userName = getStringFromUser("::What will be your new UserName?:: ");
		creds.add(userName);
		String fName = getStringFromUser("::Please enter your First Name: ");
		creds.add(fName);
		String lName = getStringFromUser("::Please enter your Last Name: ");
		creds.add(lName);
		return creds;
	}
	
	private static User createNewUser(List<String> creds)
	{
		return new User(creds.get(0), creds.get(1), creds.get(2));
	}
	
	private static String buildTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static void viewAll() {
		ArrayList<BlogPost> allPosts = client.queryBlogPosts();
		ArrayList<String> options = new ArrayList<String>();
		options.add("Back");
		String postString;
		for(BlogPost post : allPosts) {
			postString = post.getPostTitle() + "\t" + post.getPostAuthor() + "\t";// + post.getTimeStamp();
			options.add(postString);
		}
		int selection = getMenuSelection("Select a post:", options);
		switch(selection) {
		case 0:
			break;
		default:
			BlogPost chosen = allPosts.get(selection - 1);
			viewPost(chosen);
			break;
		}
	}
	
	public static void viewPost(BlogPost post){
		System.out.println(thickSeparator);
		System.out.println(post.getPostTitle() + " | " + post.getPostAuthor() + " | " + post.getTimeStamp());
		System.out.println(thickSeparator);
		System.out.println(post.getPostContent());
		ArrayList<BlogComment> comments = client.queryComments(post.getPostID());
		if(comments.size() > 0) {
			System.out.println("\nComments:");
			System.out.println(thinSeparator);
			for(BlogComment comment : comments) {
				System.out.println(comment.getAuthor() + " | " + comment.getTimeStamp() + "\n");
				System.out.println(comment.getContent());
				System.out.println(thinSeparator);
			}
		} else {
			System.out.println("\nNo comments found.");
		}
		ArrayList<String> options = new ArrayList<String>();
		options.add("Back");
		options.add("Leave a comment");
		switch(getMenuSelection("Choose an option:", options)) {
		case 0:
			break;
		case 1:
			leaveComment(post.getPostID());
		}
	}
	
	public static void leaveComment(String postid) {
		String commentText = getStringFromUser("Enter your comment:");
		BlogComment comment = new BlogComment(user.getUsername(), commentText, postid, buildTimestamp());
		ArrayList<String> options = new ArrayList<String>();
		options.add("Cancel");
		options.add("Confirm");
		switch(getMenuSelection("Submit comment?",options)) {
		case 0:
			break;
		case 1:
			client.insertNewComment(comment);
		}
	}
	
	public static String getStringFromUser(String prompt) {
		String result;
		try {
			sc.nextLine();
			System.out.println(prompt);
			System.out.print(">");
			result = sc.nextLine();
			if(result == null || result.isEmpty()) {
				throw new Exception();
			}
		} catch(Exception e) {
			System.out.println();
			return getStringFromUser(prompt);
		}
		return result;
	}
	
	public static int getMenuSelection(String prompt, ArrayList<String> options) {
		int count = 0;
		System.out.println("\n" + prompt);
		System.out.println(thickSeparator);
		for(String option : options) {
			System.out.println("[" + count + "] " + option);
			count++;
		}
		System.out.println(">");
		try {
			int result = sc.nextInt();
			if(result < 0 || result > options.size()) {
				throw new IllegalArgumentException();
			}
			return result;
		} catch(Exception e) {
			return getMenuSelection(prompt, options);
		}
	}
	
	public static String buildUUID() {
		String base = "bcbc411e-175c-11e4-9d72-b2227cce";
		for(int i = 0; i < 4; i++) {
			base += gen.nextInt(10);
		}
		return base;
	}

}
