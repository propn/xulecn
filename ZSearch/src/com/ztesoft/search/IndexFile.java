/**
 * 
 */
package com.ztesoft.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.demo.FileDocument;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author Administrator
 *
 */
public class IndexFile {

	static final File INDEX_DIR = new File("C:\\TEMP\\index");
	static final String docDir = "E:\\lei\\workspace\\firefly\\src";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date start = new Date();

		try {

			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

			IndexWriter writer = new IndexWriter(FSDirectory.open(INDEX_DIR), analyzer, true,
					IndexWriter.MaxFieldLength.LIMITED);

			System.out.println("Indexing to directory '" + INDEX_DIR + "'...");

			File doc = new File(docDir);

			indexDocs(writer, doc);

			System.out.println("Optimizing...");

			writer.optimize();
			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}

	}

	static void indexDocs(IndexWriter writer, File file) throws IOException {
		// do not try to index files that cannot be read
		if (file.canRead()) {

			if (file.isDirectory()) {

				String[] files = file.list();
				// an IO error could occur
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				System.out.println("adding " + file);

				try {
					writer.addDocument(FileDocument.Document(file));
				}
				// at least on windows, some temporary files raise this exception with an "access denied" message
				// checking if the file can be read doesn't help
				catch (FileNotFoundException fnfe) {
				}
			}
		}
	}

}
