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

		/* 指明要索引文件夹的位置,这里是d盘的s文件夹下 */
		File fileDir = new File("d:\\s");

		/* 这里放索引文件的位置 */
		File indexDir = new File("d:\\index");
		Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(INDEX_DIR), analyzer, true,
				IndexWriter.MaxFieldLength.LIMITED);

		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();

		//增加document到索引去     
		System.out.println("File正在被索引.");

		/* 
		 * 注意要变的就是这里，路径和读取文件的方法 
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
		//optimize()方法是对索引进行优化   

		indexWriter.optimize();
		indexWriter.close();

		//测试一下索引的时间   
		long endTime = new Date().getTime();
		System.out.println("这花费了" + (endTime - startTime) + " 毫秒来把文档增加到索引里面去!" + fileDir.getPath());
	}

	public static String readWord(String path) {
		StringBuffer content = new StringBuffer("");// 文档内容 
		
		try {

			HWPFDocument doc = new HWPFDocument(new FileInputStream(path));
			Range range = doc.getRange();
			int paragraphCount = range.numParagraphs();// 段落 
			
			for (int i = 0; i < paragraphCount; i++) {// 遍历段落读取数据 
				Paragraph pp = range.getParagraph(i);
				content.append(pp.text());
			}

		} catch (Exception e) {

		}
		
		return content.toString().trim();
	}

	public static String readPdf(String path) throws Exception {
		StringBuffer content = new StringBuffer("");// 文档内容 
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
			// 读取页面 
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));//这里的字符编码要注意，要对上html头文件的一致，否则会出乱码 

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
		StringBuffer content = new StringBuffer("");// 文档内容 
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
