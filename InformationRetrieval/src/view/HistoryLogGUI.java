package view;

import java.awt.Font;
import java.io.*;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HistoryLogGUI {
	
	private JPanel panel;
	private JFrame frame;
	private JScrollPane scrollableTextArea;
	private JEditorPane editorPane ;
	
	public HistoryLogGUI() {
		
		this.editorPane =  new JEditorPane();
		this.frame = new JFrame("History");
		this.panel = new JPanel();
		this.scrollableTextArea = new JScrollPane(editorPane);  
		this.scrollableTextArea.setBounds(50, 50, 600, 450);
		this.scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		this.scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
        this.scrollableTextArea.getViewport().getView().setFont(font);
			
        frame.getContentPane().add(scrollableTextArea);          
        frame.add(panel);
		frame.pack();
		
		frame.setSize(720, 576);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.show();
        
	}//end of constructor

	public void showHistoryLog() {
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File("resources/history_log.txt"))));
			this.editorPane.read(input, "READING FILE ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//end of method
	
	public void showAdvancedHistoryLog() {
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File("resources/advanced_history_log.txt"))));
			this.editorPane.read(input, "READING FILE ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//end of method
}//end of class
