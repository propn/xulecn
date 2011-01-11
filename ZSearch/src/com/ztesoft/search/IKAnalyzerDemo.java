/**
 * 
 */
package com.ztesoft.search;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
// ���� IKAnalyzer3.0 ����
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

public class IKAnalyzerDemo {
	public static void main(String[] args) {
		//Lucene Document ������
		String fieldName = "text";
		// ��������
		String text = "IK Analyzer ��һ����ϴʵ�ִʺ��ķ��ִʵ����ķִʿ�Դ���߰�����ʹ����ȫ�µ����������ϸ�����з��㷨�� ";
		// ʵ���� IKAnalyzer �ִ���
		Analyzer analyzer = new IKAnalyzer();
		Directory directory = null;

		IndexWriter iwriter = null;
		IndexSearcher isearcher = null;
		try {
			// �����ڴ���������
			directory = new RAMDirectory();
			iwriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
			Document doc = new Document();
			doc.add(new Field(fieldName, text, Field.Store.YES, Field.Index.ANALYZED));
			iwriter.addDocument(doc);
			iwriter.close();
			// ʵ����������
			isearcher = new IndexSearcher(directory);
			// ����������ʹ�� IKSimilarity ���ƶ�������
			isearcher.setSimilarity(new IKSimilarity());

			String keyword = " ���ķִʹ��߰� ";
			// ʹ�� IKQueryParser ��ѯ���������� Query ����
			Query query = IKQueryParser.parse(fieldName, keyword);
			// �������ƶ���ߵ� 5 ����¼
			TopDocs topDocs = isearcher.search(query, 5);
			System.out.println(" ���У� " + topDocs.totalHits);
			// ������
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println(" ���ݣ� " + targetDoc.toString());
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isearcher != null) {
				try {
					isearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
