package edu.neumont.dbt230.anewkirk.cassandra.gui.gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AllBlogsTab extends JPanel
{
	private JTextArea infoBox = new JTextArea();
	private JScrollPane blogListingBox = new JScrollPane(infoBox);
	private JLabel dateLabel, authorLabel, titleLabel;
	private JTextField searchBox = new JTextField();
	
	public AllBlogsTab()
	{
		infoBox.setBounds(400, 400, 200, 200);
		infoBox.setVisible(true);
		infoBox.setText("Hohoho\tstuff");
		
		blogListingBox.setPreferredSize(new Dimension(125, 180));
		blogListingBox.setAlignmentY(BOTTOM_ALIGNMENT);
		blogListingBox.setVisible(true);
		add(blogListingBox);
		addLabels();
		setVisible(true);
	}
	
	public void addLabels()
	{
		dateLabel = new JLabel("Date");
		dateLabel.setPreferredSize(new Dimension(125, 60));
		add(dateLabel);
		
		authorLabel = new JLabel("Author");
		authorLabel.setPreferredSize(new Dimension(125, 60));
		add(authorLabel);
		
		titleLabel = new JLabel("Title");
		titleLabel.setPreferredSize(new Dimension(125, 60));
		add(titleLabel);
	}
}
