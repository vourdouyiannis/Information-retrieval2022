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
import searchengines.AdvancedSearch;

public class AdvancedSearchGUI {
	private JPanel panel;
	private JFrame frame;
	private String INDEX_DIR= "luceneindex";
	private JTextField title;
	private JTextField originalLanguage;
	private JTextField voteCount;
	private JTextField voteAverage;
	private JLabel titleLabel;
	private JLabel originalLanguageLabel;
	private JLabel voteCountLabel;
	private JLabel voteAverageLabel;
	private JButton searchBtn;
	private JButton historyButton;
	
	public AdvancedSearchGUI() {
		this.panel = new JPanel(new BorderLayout());
		this.frame = new JFrame("Advanced Search");
		this.searchBtn = new JButton("SEARCH");
		
		//Initialize label 'title'
		this.titleLabel = new JLabel("Title:");
		this.titleLabel.setSize(new Dimension(10,5));
		this.titleLabel.setBounds(30, 50, 80, 37);
		this.titleLabel.setFont(new Font("Jumble", Font.BOLD, 15));
		
		//Initialize textfield 'title'
		this.title = new JTextField();
		this.title.setSize(new Dimension(400,40));
		this.title.setBounds(180, 50, 200, 40);
		this.title.setBackground(new Color(255,255,255));
			
		//Initialize label 'originalLanguageLabel'
		this.originalLanguageLabel = new JLabel("Original Language:");
		this.originalLanguageLabel.setSize(new Dimension(10,20));
		this.originalLanguageLabel.setBounds(30, 100, 200, 37);
		this.originalLanguageLabel.setFont(new Font("Jumble", Font.BOLD, 15));
		
		//Initialize textfield 'originalLanguage'
		this.originalLanguage = new JTextField();
		this.originalLanguage.setSize(new Dimension(400,40));
		this.originalLanguage.setBounds(180, 100, 200, 40);
		this.originalLanguage.setBackground(new Color(255,255,255));
		
		
		//Initialize label 'voteCountLabel'
		this.voteCountLabel = new JLabel("Votes count:");
		this.voteCountLabel.setSize(new Dimension(10,20));
		this.voteCountLabel.setBounds(30, 150, 200, 37);
		this.voteCountLabel.setFont(new Font("Jumble", Font.BOLD, 15));
		
		//Initialize textfield 'voteCount'
		this.voteCount = new JTextField();
		this.voteCount.setSize(new Dimension(400,40));
		this.voteCount.setBounds(180, 150, 200, 40);
		this.voteCount.setBackground(new Color(255,255,255));
		
		
		//Initialize label 'voteAverageLabel'
		this.voteAverageLabel = new JLabel("Votes Average:");
		this.voteAverageLabel.setSize(new Dimension(10,20));
		this.voteAverageLabel.setBounds(30, 200, 200, 37);
		this.voteAverageLabel.setFont(new Font("Jumble", Font.BOLD, 15));
		
		//Initialize textfield 'voteAverage'
		this.voteAverage = new JTextField();
		this.voteAverage.setSize(new Dimension(400,40));
		this.voteAverage.setBounds(180, 200, 200, 40);
		this.voteAverage.setBackground(new Color(255,255,255));
		
		//Initialize search button
		this.searchBtn.setBorder(new LineBorder(Color.BLACK));
		this.searchBtn.setSize(new Dimension(70,40));
		this.searchBtn.setBounds(450, 300, 100, 37);
		this.searchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  searchButtonPressed();
			  } 
			} );
		
		//Initialize history button
		this.historyButton = new JButton("History");
		this.historyButton.setBorder(new LineBorder(Color.BLACK));
		this.historyButton.setSize(new Dimension(70,40));
		this.historyButton.setBounds(300, 300, 100, 37);
		this.historyButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  historyButtonPressed();
			  } 
			} );
		
		//Add components.
		frame.add(historyButton);
		frame.add(titleLabel);
		frame.add(originalLanguageLabel);
		frame.add(voteCountLabel);
		frame.add(voteAverageLabel);
		frame.add(title);
		frame.add(originalLanguage);
		frame.add(voteCount);
		frame.add(voteAverage);
		frame.add(searchBtn);
		
		frame.add(panel);
		frame.pack();
		
		frame.setSize(720, 576);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
	
	private void searchButtonPressed() {
		String titleQuery = this.title.getText();
		String originalLanguageQuery = this.originalLanguage.getText();
		String voteCountQuery = this.voteCount.getText();
		String voteAverageQuery = this.voteAverage.getText();
		
		AdvancedSearch sr = new AdvancedSearch(this.INDEX_DIR);
		sr.addToHistory(titleQuery, originalLanguageQuery, voteCountQuery, voteAverageQuery);
		
		ArrayList<Document> searchByTitleRes = new  ArrayList<Document>() ;
		ArrayList<Document> searchByOriginalLanguageRes  = new  ArrayList<Document>()  ;
		ArrayList<Document> searchByVoteCountRes  = new  ArrayList<Document>()  ;
		ArrayList<Document> searchByVoteAverageRes  = new  ArrayList<Document>() ;
		ArrayList<Document> myDoc= new  ArrayList<Document>();

		if(!titleQuery.equals("") && !originalLanguageQuery.equals("") && !voteCountQuery.equals("") && !voteAverageQuery.equals("")) {
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);

			for(Document d1 :searchByTitleRes) {
				for(Document d2 :searchByOriginalLanguageRes) {
					for(Document d3 :searchByVoteCountRes) {
						for(Document d4 :searchByVoteAverageRes) {
							if(d1.get("title").equals(d2.get("title")) && d1.get("title").equals(d3.get("title")) && d1.get("title").equals(d4.get("title"))) {
								myDoc.add(d1);
							}
						}
					}
				}
			}
		}

		// Gia ena keno
		else if (!titleQuery.equals("") && originalLanguageQuery.equals("") && voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			myDoc.addAll(searchByTitleRes);
		}
		else if (titleQuery.equals("") && !originalLanguageQuery.equals("") && voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			myDoc.addAll(searchByOriginalLanguageRes);
		}
		else if (titleQuery.equals("") && originalLanguageQuery.equals("") && !voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			myDoc.addAll(searchByVoteCountRes);
		}
		else if (titleQuery.equals("") && originalLanguageQuery.equals("") && voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			myDoc.addAll(searchByVoteAverageRes);
		}

		// Gia duo kena
		else if (!titleQuery.equals("") && !originalLanguageQuery.equals("") && voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			for(Document d1 :searchByTitleRes) {
				for (Document d2 : searchByOriginalLanguageRes) {
					if(d1.get("title").equals(d2.get("title"))) {
						myDoc.add(d1);
					}
				}
			}
		}
		else if (!titleQuery.equals("") && originalLanguageQuery.equals("") && !voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			for(Document d1 :searchByTitleRes) {
				for(Document d3 :searchByVoteCountRes) {
					if(d1.get("title").equals(d3.get("title"))) {
						myDoc.add(d1);
					}
				}
			}
		}
		else if (!titleQuery.equals("") && originalLanguageQuery.equals("") && voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for(Document d1 :searchByTitleRes) {
				for(Document d4 :searchByVoteAverageRes) {
					if(d1.get("title").equals(d4.get("title"))) {
						myDoc.add(d1);
					}
				}
			}
		}
		else if (titleQuery.equals("") && !originalLanguageQuery.equals("") && !voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			for (Document d2 : searchByOriginalLanguageRes) {
				for(Document d3 :searchByVoteCountRes) {
					if(d2.get("title").equals(d3.get("title"))) {
						myDoc.add(d2);
					}
				}
			}
		}
		else if (titleQuery.equals("") && !originalLanguageQuery.equals("") && voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for (Document d2 : searchByOriginalLanguageRes) {
				for(Document d4 :searchByVoteAverageRes) {
					if(d2.get("title").equals(d4.get("title"))) {
						myDoc.add(d2);
					}
				}
			}
		}
		else if (titleQuery.equals("") && originalLanguageQuery.equals("") && !voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for(Document d3 :searchByVoteCountRes) {
				for(Document d4 :searchByVoteAverageRes) {
					if(d3.get("title").equals(d4.get("title"))) {
						myDoc.add(d3);
					}
				}
			}
		}

		//Gia treia kena
		else if (!titleQuery.equals("") && !originalLanguageQuery.equals("") && !voteCountQuery.equals("") && voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			for(Document d1 :searchByTitleRes) {
				for(Document d2 :searchByOriginalLanguageRes) {
					for(Document d3 :searchByVoteCountRes) {
						if (d1.get("title").equals(d2.get("title")) && d1.get("title").equals(d3.get("title"))) {
							myDoc.add(d1);
						}
					}
				}
			}
		}
		else if (!titleQuery.equals("") && !originalLanguageQuery.equals("") && voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for(Document d1 :searchByTitleRes) {
				for(Document d2 :searchByOriginalLanguageRes) {
					for(Document d4 :searchByVoteAverageRes) {
						if (d1.get("title").equals(d2.get("title")) && d1.get("title").equals(d4.get("title"))) {
							myDoc.add(d1);
						}
					}
				}
			}
		}
		else if (!titleQuery.equals("") && originalLanguageQuery.equals("") && !voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByTitleRes = sr.searchByTitle(titleQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for(Document d1 :searchByTitleRes) {
				for(Document d3 :searchByVoteCountRes) {
					for(Document d4 :searchByVoteAverageRes) {
						if (d1.get("title").equals(d3.get("title")) && d1.get("title").equals(d4.get("title"))) {
							myDoc.add(d1);
						}
					}
				}
			}
		}
		else if (titleQuery.equals("") && !originalLanguageQuery.equals("") && !voteCountQuery.equals("") && !voteAverageQuery.equals("")){
			searchByOriginalLanguageRes = sr.searchByOriginalLanguage(originalLanguageQuery);
			searchByVoteCountRes = sr.searchByVoteCount(voteCountQuery);
			searchByVoteAverageRes = sr.searchByVoteAverage(voteAverageQuery);
			for(Document d2 :searchByOriginalLanguageRes) {
				for(Document d3 :searchByVoteCountRes) {
					for(Document d4 :searchByVoteAverageRes) {
						if (d2.get("title").equals(d3.get("title")) && d2.get("title").equals(d4.get("title"))) {
							myDoc.add(d2);
						}
					}
				}
			}
		}

		DisplayResults display = new DisplayResults();
		if(!(myDoc.size()==0)) {
			display.showAdvancedResults(myDoc, titleQuery);
		}
	}//end of method

	public void historyButtonPressed() {
		 HistoryLogGUI displayHistory = new HistoryLogGUI();
		 displayHistory.showAdvancedHistoryLog();
	 }//end of method
}//end of class