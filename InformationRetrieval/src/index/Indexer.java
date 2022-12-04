package index;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import com.opencsv.exceptions.CsvValidationException;

public class Indexer {
	private  Reader csvFile;
	private String path; // = "resources/TMDb_updated.csv";
	private Path docDir ;
	private  final String INDEX_DIR; // = "luceneindex";
	private  IndexWriter writer;
	
	
	public Indexer(String INDEX_DIR, String path) {
		this.path = path;
		this.INDEX_DIR = INDEX_DIR;
		this.docDir = Paths.get(path);
		try {
			this.writer= initializeWriter(this.docDir);
		} 
		catch (CsvValidationException | IOException e) {
			e.printStackTrace();
		}
	}//end of constructor
	
	public  IndexWriter initializeWriter( Path docDir) throws CsvValidationException, IOException {
		int line_counter = 0;
		try {
			FSDirectory dir;
			
		    IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		    
		    dir = FSDirectory.open(Paths.get(INDEX_DIR));
			this.csvFile= new FileReader(path);
			config.setOpenMode(OpenMode.CREATE);
		    
			this.writer = new IndexWriter(dir , config );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		org.apache.commons.csv.CSVParser csvParser = CSVFormat.EXCEL.withHeader("title", "overview", "original_language", "vote_count", "vote_average").withSkipHeaderRecord(true).parse(csvFile);

		for (CSVRecord record : csvParser) {
			if(record.size() >= csvParser.getHeaderMap().size()) {
				Document doc = new Document();

				doc.add(new TextField("title", record.get("title"), Field.Store.YES));
				doc.add(new TextField("overview", record.get("overview"), Field.Store.YES));
				doc.add(new TextField("original_language", record.get("original_language"), Field.Store.YES));
				doc.add(new TextField("vote_count", record.get("vote_count"), Field.Store.YES));
				doc.add(new TextField("vote_average", record.get("vote_average"), Field.Store.YES));

				this.writer.addDocument(doc);
				line_counter+=1;
			}
		}
		//System.out.println("Processed "+line_counter+" lines.");
		writer.close();
		return writer;
	}//end of method
	
	public void close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end of method
}//end of class
