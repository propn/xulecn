import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import test.jaxb.BookType;
import test.jaxb.Collection;
import test.jaxb.Collection.Books;

import com.chinatelecom.www.crm.ContractRoot;
import com.chinatelecom.www.crm.TcpCont;
import com.chinatelecom.www.crm.ContractRoot.SvcCont;

public class TestJaxb {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws JAXBException, Exception {

		//		JAXBContext jc = JAXBContext.newInstance("test.jaxb");
		//
		//		Unmarshaller unmarshaller = jc.createUnmarshaller();
		//		Collection collection = (Collection) unmarshaller.unmarshal(new File(
		//				"E:\\lei\\workspace3\\JAXB\\schema\\books.xml"));
		//		

		TestJaxb testJaxb = new TestJaxb();
		Collection collection = testJaxb.unmarshal(Collection.class, new FileInputStream(
				"E:\\lei\\workspace3\\JAXB\\schema\\books.xml"));

		//		Books booksType = collection.getBooks();
		//		List bookList = booksType.getBook();
		//
		//		for (Iterator<BookType> it = bookList.iterator(); it.hasNext();) {
		//			BookType bookType = it.next();
		//
		//			System.out.println(bookType.getBookCategory());
		//			System.out.println(bookType.getDescription());
		//			System.out.println(bookType.getISBN());
		//			System.out.println(bookType.getItemId());
		//			System.out.println(bookType.getName());
		//			System.out.println(bookType.getPrice());
		//			System.out.println(bookType.getAuthors());
		//			System.out.println(bookType.getPromotion());
		//			System.out.println(bookType.getPublicationDate());
		//
		//			System.out.println("####################################");
		//
		//			bookType.setItemId("307");
		//			bookType.setName("JAXB today and beyond");
		//
		//			JAXBContext jaxbContext = JAXBContext.newInstance("test.jaxb");
		//			Marshaller marshaller = jaxbContext.createMarshaller();
		//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		//			marshaller.marshal(collection, new FileOutputStream("E:\\lei\\workspace3\\JAXB\\schema\\jaxbOutput.xml"));
		//			
		//			
		//			
		//		}

		System.out.println("####################################");
		String str = testJaxb.marshal(collection);
		System.out.println(str);

		Collection coll = testJaxb.unmarshal(Collection.class, str);
		Books booksType2 = coll.getBooks();
		List bookList2 = booksType2.getBook();

		for (Iterator<BookType> it = bookList2.iterator(); it.hasNext();) {
			BookType bookType = it.next();

			System.out.println(bookType.getBookCategory());
			System.out.println(bookType.getDescription());
			System.out.println(bookType.getISBN());
			System.out.println(bookType.getItemId());
			System.out.println(bookType.getName());
			System.out.println(bookType.getPrice());
			System.out.println(bookType.getAuthors());
			System.out.println(bookType.getPromotion());
			System.out.println(bookType.getPublicationDate());

			System.out.println("####################################");

		}
		System.out.println("222222222222222222222222222222222");
		testJaxb.test();

	}

	public <T> T unmarshal(Class<T> docClass, InputStream inputStream) throws JAXBException {

		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		T obj = (T) unmarshaller.unmarshal(inputStream);

		return obj;

	}

	public <T> T unmarshal(Class<T> docClass, String xml) throws JAXBException {

		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		T obj = (T) unmarshaller.unmarshal(new StringReader(xml));

		return obj;

	}

	public String marshal(Object obj) throws JAXBException {

		String packageName = obj.getClass().getPackage().getName();
		JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		//		marshaller.marshal(collection, new FileOutputStream("E:\\lei\\workspace3\\JAXB\\schema\\jaxbOutput.xml"));
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	public void test() throws Exception {

		ContractRoot contractRoot = new ContractRoot();

		TcpCont tcpCont = new TcpCont();

		tcpCont.setActionCode("11111");
		contractRoot.setTcpCont(tcpCont);

		SvcCont svcCont = new SvcCont();
		contractRoot.setSvcCont(svcCont);

		String str = marshal(contractRoot);

	}

}
