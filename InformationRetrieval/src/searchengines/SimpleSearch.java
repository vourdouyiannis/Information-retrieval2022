package searchengines;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SimpleSearch {
	
	private  final String INDEX_DIR;
	private String searchInput;
	private IndexReader reader;
	private IndexSearcher searcher;

	public SimpleSearch( String INDEX_DIR) {
		
		this.INDEX_DIR =INDEX_DIR;
		try {
			this.reader = DirectoryReader.open(FSDirectory.open(Paths.get(this.INDEX_DIR)));
			this.searcher = new IndexSearcher(reader);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}//end of constructor
	
	public  ArrayList<Document> searchByTitle(String title){
		this.searchInput=title;
		
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("title", new StandardAnalyzer());
			Query idQuery = qp.parse(title);
			TopDocs hits = this.searcher.search(idQuery, 2000);
			
			for (ScoreDoc sd : hits.scoreDocs) {
				Document d = this.searcher.doc(sd.doc);
				results.add(d);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}//end of method
	
	public  ArrayList<Document> searchByOverview(String overview){
		this.searchInput=overview;
		
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("overview", new StandardAnalyzer());
			Query idQuery = qp.parse(overview);
			TopDocs hits = this.searcher.search(idQuery, 2000);
			
			for (ScoreDoc sd : hits.scoreDocs) {
				Document d = this.searcher.doc(sd.doc);
				results.add(d);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}//end of method
	
 	public void addToHistory() {
		File file = new File("resources/history_log.txt");
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter("resources/history_log.txt" ,  true);
			writer.write(this.searchInput+"\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }//end of method
}//end of class
