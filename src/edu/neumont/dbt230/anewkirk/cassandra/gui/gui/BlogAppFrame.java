package g7.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class BlogAppFrame extends JFrame
{
	private JMenuBar mainToolBar = new JMenuBar();
	
	private JTabbedPane tabsPane = new JTabbedPane();
	private JPanel loginPanel = new LoginPane();
	private AllBlogsTab abt = new AllBlogsTab();
	private UserPanel up = new UserPanel();
	private DetailBlogTab dbt = new DetailBlogTab();
	private NewPostTab npt = new NewPostTab();
	
	public BlogAppFrame()
	{
		
		tabsPane.add("Login",loginPanel);
		this.add(tabsPane);
		addTabs();
		addAndFillToolBar();
		setPreferredSize(new Dimension(900, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	public void addAndFillToolBar()
	{
		JMenu fileMenu = new JMenu("File");
		JMenuItem loginItem = new JMenuItem("Login");
		fileMenu.add(loginItem);
		mainToolBar.add(fileMenu);
		setJMenuBar(mainToolBar);
	}
	
	public void addTabs()
	{
		tabsPane.add("All Blogs", abt);
		tabsPane.add("userPanel", up);
		tabsPane.add("New Post", npt);
	}
	
	public static void main(String[] args) {
		BlogAppFrame ba = new BlogAppFrame();
		ba.setVisible(true);
	}
}
