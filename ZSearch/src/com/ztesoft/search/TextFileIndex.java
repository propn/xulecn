/**
 * 
 */
package com.ztesoft.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

/**
 * @author Administrator
 *
 */
public class TextFileIndex {

	static final File INDEX_DIR = new File("C:\\TEMP\\index");
	static final String docDir = "E:\\lei\\workspace\\firefly\\src";

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		/* ָ��Ҫ�����ļ��е�λ��,������d�̵�s�ļ����� */
		File fileDir = new File("d:\\s");

		/* ����������ļ���λ�� */
		File indexDir = new File("d:\\index");
		Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(INDEX_DIR), analyzer, true,
				IndexWriter.MaxFieldLength.LIMITED);

		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();

		//����document������ȥ     
		System.out.println("File���ڱ�����.");

		/* 
		 * ע��Ҫ��ľ������·���Ͷ�ȡ�ļ��ķ��� 
		 * 
		 * */
		String path = "d:\\s\\2.doc";

		String temp = TextFileIndex.readWord(path);

		Document document = new Document();
		Field FieldPath = new Field("path", path, Field.Store.YES, Field.Index.NO);

		Field FieldBody = new Field("body", temp, Field.Store.YES, Field.Index.ANALYZED,
				Field.TermVector.WITH_POSITIONS_OFFSETS);
		document.add(FieldPath);
		document.add(FieldBody);

		indexWriter.addDocument(document);
		//optimize()�����Ƕ����������Ż�   

		indexWriter.optimize();
		indexWriter.close();

		//����һ��������ʱ��   
		long endTime = new Date().getTime();
		System.out.println("�⻨����" + (endTime - startTime) + " ���������ĵ����ӵ���������ȥ!" + fileDir.getPath());
	}

	public static String readWord(String path) {
		StringBuffer content = new StringBuffer("");// �ĵ����� 
		
		try {

			HWPFDocument doc = new HWPFDocument(new FileInputStream(path));
			Range range = doc.getRange();
			int paragraphCount = range.numParagraphs();// ���� 
			
			for (int i = 0; i < paragraphCount; i++) {// ���������ȡ���� 
				Paragraph pp = range.getParagraph(i);
				content.append(pp.text());
			}

		} catch (Exception e) {

		}
		
		return content.toString().trim();
	}

	public static String readPdf(String path) throws Exception {
		StringBuffer content = new StringBuffer("");// �ĵ����� 
		FileInputStream fis = new FileInputStream(path);
		PDFParser p = new PDFParser(fis);
		p.parse();
		PDFTextStripper ts = new PDFTextStripper();
		content.append(ts.getText(p.getPDDocument()));
		fis.close();
		return content.toString().trim();
	}

	public static String readHtml(String urlString) {

		StringBuffer content = new StringBuffer("");
		File file = new File(urlString);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			// ��ȡҳ�� 
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));//������ַ�����Ҫע�⣬Ҫ����htmlͷ�ļ���һ�£����������� 

			String line = null;

			while ((line = reader.readLine()) != null) {
				content.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String contentString = content.toString();
		return contentString;
	}

	public static String readTxt(String path) {
		StringBuffer content = new StringBuffer("");// �ĵ����� 
		try {
			FileReader reader = new FileReader(path);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;

			while ((s1 = br.readLine()) != null) {
				content.append(s1 + "\r");
			}
			br.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString().trim();
	}

}
