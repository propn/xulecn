import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * XML tools 
 * @author Thunder.xu
 *
 */
public class XmlUtil {

	public static <T> T unmarshal(Class<T> docClass, File xmlFile) throws Exception {

		InputStream inputStream = new FileInputStream(xmlFile);
		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		T obj = (T) unmarshaller.unmarshal(inputStream);

		return obj;

	}

	public static <T> T unmarshal(Class<T> docClass, String xml) throws JAXBException {
		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		//		unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");   //设置编码方式为GBK
		T obj = (T) unmarshaller.unmarshal(new StringReader(xml));
		return obj;
	}

	public static String marshal(Object obj) throws JAXBException {
		String packageName = obj.getClass().getPackage().getName();
		JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		//		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");   //设置编码方式为GBK
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	public static DocumentBuilder getBuilder() throws ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return builder;
	}

	public static Document getDocument(String path) throws Exception {
		File f = new File(path);
		DocumentBuilder builder = getBuilder();
		Document doc = builder.parse(f);
		return doc;
	}

	public static Document getDocument(InputStream in) throws Exception {
		DocumentBuilder builder = getBuilder();
		Document doc = builder.parse(in);
		return doc;
	}

	public static Document getNewDoc() throws Exception {
		DocumentBuilder builder = getBuilder();
		Document doc = builder.newDocument();
		return doc;
	}

	public static Document getNewDoc(String xmlStr) {
		Document doc = null;
		try {
			StringReader sr = new StringReader(xmlStr);
			InputSource iSrc = new InputSource(sr);
			DocumentBuilder builder = getBuilder();
			doc = builder.parse(iSrc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	public static void save(Document doc, String filePath) {
		try {
			OutputFormat format = new OutputFormat(doc); //Serialize DOM
			format.setEncoding("GB2312");
			StringWriter stringOut = new StringWriter(); //Writer will be a String
			XMLSerializer serial = new XMLSerializer(stringOut, format);
			serial.asDOMSerializer(); // As a DOM Serializer
			serial.serialize(doc.getDocumentElement());
			String STRXML = stringOut.toString(); //Spit out DOM as a String
			String path = filePath;
			writeXml(STRXML, path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeXml(String STRXML, String path) {
		try {
			File f = new File(path);
			PrintWriter out = new PrintWriter(new FileWriter(f));
			out.print(STRXML + "＼n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String toString(Document doc) {
		String STRXML = null;
		try {
			OutputFormat format = new OutputFormat(doc); //Serialize DOM
			format.setEncoding("GB2312");
			StringWriter stringOut = new StringWriter(); //Writer will be a String
			XMLSerializer serial = new XMLSerializer(stringOut, format);
			serial.asDOMSerializer(); // As a DOM Serializer
			serial.serialize(doc.getDocumentElement());
			STRXML = stringOut.toString(); //Spit out DOM as a String
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STRXML;
	}

	public static String toString(Node node, Document doc) {
		String STRXML = null;
		try {
			OutputFormat format = new OutputFormat(doc); //Serialize DOM
			format.setEncoding("GB2312");
			StringWriter stringOut = new StringWriter(); //Writer will be a String
			XMLSerializer serial = new XMLSerializer(stringOut, format);
			serial.asDOMSerializer(); // As a DOM Serializer
			serial.serialize(node);
			STRXML = stringOut.toString(); //Spit out DOM as a String
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STRXML;
	}

	public static String toString(Node node) {
		String STRXML = null;
		try {
			OutputFormat format = new OutputFormat();
//			format.setEncoding("GBK");
			StringWriter stringOut = new StringWriter();
			XMLSerializer serial = new XMLSerializer(stringOut, format);
			serial.setNamespaces(true);
			serial.asDOMSerializer();
			serial.serialize(node);
			STRXML = stringOut.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STRXML;
	}

	public static void main(String[] args) throws Exception {
		String pathRoot = "NeTrees.xml";
		Document doc, doc1;
		try {
			doc = XmlUtil.getDocument(pathRoot);
			doc1 = XmlUtil.getDocument(pathRoot);
			if (doc == doc1) {
				System.out.println("They are  same objects!");
			} else {
				System.out.println("They are different!");
				OutputFormat format = new OutputFormat(doc);
				format.setEncoding("GB2312");
				StringWriter stringOut = new StringWriter();
				XMLSerializer serial = new XMLSerializer(stringOut, format);
				serial.asDOMSerializer();
				serial.serialize(doc.getDocumentElement());
				String STRXML = stringOut.toString();
				System.out.println(STRXML);
			}
		} catch (Exception ex) {
			System.out.print("Reading file\" " + pathRoot + "\" error!");
			ex.printStackTrace();
		}
	}
}
