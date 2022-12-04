package modelResults;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import org.apache.lucene.document.Document;

public class DisplayResults {
	private JFrame frame;
	private JLabel label;
	private JPanel cPanel;
	private JScrollPane scrollableTextArea;
	private JEditorPane editorPane ;
	
	public  DisplayResults() {
		this.editorPane =  new JEditorPane();
		this.frame = new JFrame("Results");
		this.cPanel =  new JPanel(); 
		this.label = new JLabel();
		this.frame.getContentPane().setLayout(null);  
		this.label.setSize(new Dimension(720, 576));
				
		scrollableTextArea = new JScrollPane(editorPane);
		scrollableTextArea.setBounds(50, 50, 700, 630);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
        scrollableTextArea.getViewport().getView().setFont(font);
        
        frame.getContentPane().add(scrollableTextArea);  
		
		this.frame.add(cPanel);
		this.frame.pack();
		
		this.frame.setSize(850, 720);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.show();
		
	}//end of constructor
	
	public void showSimpleResults(ArrayList<Document> resultsByTitle , ArrayList<Document> resultsByOverview , String userInput) {
		String str="";
		
		for(Document d : resultsByTitle) {			
			str=str + "Title: " + d.get("title") + "\nOverview: " +  d.get("overview") + "\nOriginal Language: "+ d.get("original_language") +"\nVote Count: " + d.get("vote_count") +"\nVote Average: "+d.get("vote_average")+"\n\n";
		}
		
		for(Document d : resultsByOverview) {			
			str=str + "\nTitle: " + d.get("title") + "\nOverview: " +  d.get("overview") + "\nOriginal Language: "+ d.get("original_language") +"\nVote Count: " + d.get("vote_count") +"\nVote Average: "+d.get("vote_average")+"\n";
		}
		  this.editorPane.setText(str);
		  try {
			highlight(this.editorPane, userInput);  
			highlight(this.editorPane, userInput.substring(0, 1).toUpperCase() + userInput.substring(1));
			highlight(this.editorPane, userInput.substring(0, 1).toLowerCase() + userInput.substring(1));
			highlight(this.editorPane, userInput.toLowerCase());
			highlight(this.editorPane, userInput.toUpperCase());

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.editorPane.setCaretPosition(0); 
	}//end of method

	public void showAdvancedResults(ArrayList<Document> resultsByTitle , String userInput ) { 
		String str="";		
		ArrayList<String> usedTitles= new ArrayList<String>();
		
		for(Document d : resultsByTitle) {
			if(usedTitles.contains(d.get("title")) == false) {
				usedTitles.add(d.get("title"));
				str=str + "\nTitle: " + d.get("title") + "\nOverview: " +  d.get("overview") + "\nOriginal Language: "+ d.get("original_language") +"\nVote Count: " + d.get("vote_count") +"\nVote Average: "+d.get("vote_average")+"\n";
			}
			else {
				continue;
			}		
		}
		  try {
			  this.editorPane.setText(str);
			  if(!userInput.equals("")) {
				  highlight(this.editorPane, userInput);  
				  highlight(this.editorPane, userInput.substring(0, 1).toUpperCase() + userInput.substring(1));
				  highlight(this.editorPane, userInput.substring(0, 1).toLowerCase() + userInput.substring(1));
				  highlight(this.editorPane, userInput.toLowerCase());
				  highlight(this.editorPane, userInput.toUpperCase());
			  }
			  this.editorPane.setCaretPosition(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	}//end of method
	
	public static void highlight(JTextComponent textComp, String pattern)   throws Exception {
	 	MyHighlightPainter myHighlightPainter = new MyHighlightPainter(Color.yellow);
	    Highlighter hilite = textComp.getHighlighter();
	    javax.swing.text.Document doc = textComp.getDocument();
	    String text = doc.getText(0, doc.getLength());
	    int pos = 0;

	    while ((pos = text.indexOf(pattern, pos)) >= 0) {
	      hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
	      pos += pattern.length();
	    }
	}//end of method
}//end of class
