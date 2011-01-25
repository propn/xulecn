import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.chinatelecom.www.crm.ContractRoot;

/**
 * @author Administrator
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Test test = new Test();

		//		InputStream inputStream =new FileInputStream("");
		//		InputStream inputStream = Test.class.getResourceAsStream("E:\\lei\\workspace3\\JAXB\\schema\\ContractRoot.xml");
		//		InputStream inputStream = Test.class.getResourceAsStream("E:\\lei\\workspace3\\JAXB\\schema\\政企客户资料新建下发_请求.xml");
		//		test.unmarshal(ContractRoot.class, inputStream);
		test.test();
	}

	public <T> T unmarshal(Class<T> docClass, InputStream inputStream) throws JAXBException {
		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller u = jc.createUnmarshaller();
		JAXBElement<T> doc = (JAXBElement<T>) u.unmarshal(inputStream);

		//		JAXBXpath jaxbXpath = new JAXBXpath(doc, binder); 
		//		jaxbXpath.evaluate("/purchaseOrder/shipTo");

		return doc.getValue();
	}

	public void test() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.chinatelecom.www.crm.comm");
		Unmarshaller u = jc.createUnmarshaller();

		JAXBElement customerE = (JAXBElement) u.unmarshal(new FileInputStream(
				"E:\\lei\\workspace3\\JAXB\\schema\\政企客户资料新建下发_请求.xml"));

		ContractRoot bo = (ContractRoot) customerE.getValue();

	}

}
