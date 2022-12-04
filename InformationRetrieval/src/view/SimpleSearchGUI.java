package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.lucene.document.Document;

import modelResults.DisplayResults;
import searchengines.SimpleSearch;

public class SimpleSearchGUI {

	private JPanel panel;
	private JFrame frame;
	private String INDEX_DIR= "luceneindex";
	private JTextField userInput;
	private JButton historyBtn;
	private JButton advancedSearchBtn;
	private JButton searchBtn;
	private JLabel label;
	
	public SimpleSearchGUI() {
		this.panel = new JPanel(new BorderLayout());
		this.frame = new JFrame("Movie Search with Lucene");
		this.userInput = new JTextField(20);		
		this.label = new JLabel("Simple search" );
		this.searchBtn = new JButton("Search");
		this.advancedSearchBtn = new JButton("Advanced Search");
		this.historyBtn = new JButton("History");

		this.label.setSize(new Dimension(40,45));
		this.label.setBounds(150, 150, 400, 37);
		this.label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		
		this.historyBtn.setBorder(new LineBorder(Color.BLACK));
		this.historyBtn.setBounds(400, 400, 400, 37);
		this.historyBtn.setSize(new Dimension(150,40));
		this.historyBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  historyButtonPressed();
			  } 
			} );
		
		this.advancedSearchBtn.setBorder(new LineBorder(Color.BLACK));
		this.advancedSearchBtn.setBounds(125, 400, 400, 37);
		this.advancedSearchBtn.setSize(new Dimension(150,40));
		this.advancedSearchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  advancedSearchButtonPressed();
			  } 
			} );
		
		this.searchBtn.setBorder(new LineBorder(Color.BLACK));
		this.searchBtn.setBounds(500, 250, 400, 37);
		this.searchBtn.setSize(new Dimension(100,40));
		this.searchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  simpleSearchButtonPressed();
			  } 
			} );
		
		this.userInput.setBounds(50, 250, 400, 37);
		this.userInput.setSize(new Dimension(400,40));
		this.userInput.setBackground(new Color(255,255,204));
		this.userInput.setText("");
			
		this.frame.add(historyBtn);
		this.frame.add(advancedSearchBtn);
		this.frame.add(searchBtn);
		this.frame.add(label);
		this.frame.add(this.userInput);
		this.frame.add(panel);
		
		this.frame.pack();
		
		this.frame.setSize(720, 576);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.show();
	}//end of constructor
	
	 public void historyButtonPressed() {
		 
		 HistoryLogGUI displayHistory = new HistoryLogGUI();
		 displayHistory.showHistoryLog(); 
	 }//end of method
	 
	 public void advancedSearchButtonPressed() {
		 AdvancedSearchGUI gui = new AdvancedSearchGUI();
	 }//end of methods

	 public void simpleSearchButtonPressed() {
		 if(!this.userInput.getText().equals("")) {
			 
			 ArrayList<Document> searchByTitleRes;
			 ArrayList<Document> searchByOverviewRes;
			 
			 SimpleSearch simpleSearch = new SimpleSearch(this.INDEX_DIR);
			 
			 searchByTitleRes = simpleSearch.searchByTitle(this.userInput.getText());
			 searchByOverviewRes = simpleSearch.searchByOverview(this.userInput.getText());
			 
			 simpleSearch.addToHistory();
			 			 
			 DisplayResults displayResults = new DisplayResults(); 
			 displayResults.showSimpleResults(searchByTitleRes, searchByOverviewRes , this.userInput.getText());			 
		 }
	 }//end of method
}//end of class

