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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class AdvancedSearch {
	private  final String INDEX_DIR; // = "luceneindex";
	private IndexSearcher searcher;

	public AdvancedSearch(String INDEX_DIR) {
		this.INDEX_DIR = INDEX_DIR;
		try {
			this.searcher = createSearcher();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public IndexSearcher createSearcher() throws IOException{
		  Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		  IndexReader reader = DirectoryReader.open(dir);
		  IndexSearcher searcher = new IndexSearcher(reader);
		  return searcher;
		}
	
	public  ArrayList<Document> searchByTitle(String title){
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("title", new StandardAnalyzer());
			Query idQuery = qp.parse(title);	
			TopDocs hits = this.searcher.search(idQuery, 2000);
			
			for (ScoreDoc sd : hits.scoreDocs) {
				Document d = this.searcher.doc(sd.doc);
				results.add(d);
			}
		}//end of try
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}//end of method
		
	public  ArrayList<Document> searchByOriginalLanguage(String original_language){
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("original_language", new StandardAnalyzer());
			Query idQuery = qp.parse(original_language);
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

	public  ArrayList<Document> searchByVoteCount(String vote_count){
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("vote_count", new StandardAnalyzer());
			Query idQuery = qp.parse(vote_count);
			TopDocs hits = this.searcher.search(idQuery, 2000 );
			for (ScoreDoc sd : hits.scoreDocs) {
				Document d = this.searcher.doc(sd.doc);
				results.add(d);
				
			}
		}//end of try
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}//end of method

	public  ArrayList<Document> searchByVoteAverage(String vote_average){
		ArrayList<Document> results = new ArrayList<Document>();
		try {
			QueryParser qp = new QueryParser("vote_average", new StandardAnalyzer());
			Query  idQuery = qp.parse(vote_average);
			TopDocs hits = this.searcher.search(idQuery, 2000 );
			for (ScoreDoc sd : hits.scoreDocs) {
				Document d = this.searcher.doc(sd.doc);
				results.add(d);
			}
		}//end of try
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}//end of method

	public void addToHistory(String title, String originalLanguage , String votesCount , String votesAverage) {
		File file = new File("resources/advanced_history_log.txt");
		FileWriter writer = null;
		
		try {
			file.createNewFile();
			writer = new FileWriter("resources/advanced_history_log.txt" ,  true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if(!title.equals("")) {
			try {
				writer.write("title: "+title +"\t");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
		if(!originalLanguage.equals("")) {
			try {
				writer.write("original_language: "+originalLanguage +"\t");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
		if(!votesCount.equals("")) {
			try {
				writer.write("votes_count: "+votesCount +"\t");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
		if(!votesAverage.equals("")) {
			try {
				writer.write("votes_average: "+votesAverage +"\t");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
		try {
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }//end of method
}//end of class