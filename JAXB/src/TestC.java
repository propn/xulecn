import java.io.File;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.chinatelecom.www.crm.ContractRoot;
import com.chinatelecom.www.crm.ContractRoot.SvcCont;
import com.chinatelecom.www.crm.TcpCont;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author Administrator
 *
 */
public class TestC {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//		JAXBContext jc = JAXBContext.newInstance("com.chinatelecom.www.crm");
		//		Unmarshaller unmarshaller = jc.createUnmarshaller();

		ContractRoot contractRoot = XmlUtil.unmarshal(ContractRoot.class, new File(
				"E:\\lei\\workspace3\\JAXB\\schema\\test2.xml"));

		TcpCont tcpCont = contractRoot.getTcpCont();
		//
		//		System.out.println(tcpCont.getActionCode());
		//		System.out.println(tcpCont.getBusCode());
		//		System.out.println(tcpCont.getDstOrgID());
		//		System.out.println(tcpCont.getDstSysID());
		//		System.out.println(tcpCont.getReqTime());
		//		System.out.println(tcpCont.getRspTime());
		//		System.out.println(tcpCont.getServiceCode());
		//		System.out.println(tcpCont.getServiceLevel());
		//		System.out.println(tcpCont.getSrcOrgID());
		//		System.out.println(tcpCont.getSrcSysSign());
		//		System.out.println(tcpCont.getTransactionID());
		//
		//		System.out.println("#######################################");

		SvcCont svcCont = contractRoot.getSvcCont();
		List<Object> soo = svcCont.getSOO();

		for (Iterator<Object> it = soo.iterator(); it.hasNext();) {

			Element e = (Element) it.next();
			//			Node e = (Node) it.next();

			//¥¶¿ÌanyType
			String soo_id = e.getAttributes().getNamedItem("type").getNodeValue().replaceAll("_", "");
			System.out.println(soo_id);

			e = (Element) e.getFirstChild();
			String xml = toString(e);

			//			System.out.println(xml);

			Class _class = Class.forName("com.chinatelecom.www.crm." + soo_id);
			Object obj = _class.newInstance();

			obj = XmlUtil.unmarshal(_class, xml);

			System.out.println(obj.getClass().getName());

			//			Method m[] = _class.getDeclaredMethods();
			//			System.out.println(m[0].toString());

			//			System.out.println(obj.getCUST().getCUSTID());
			//			System.out.println(obj.getCUST().getINDUSTRYCD());
			//			System.out.println(obj.getCUST().getCUSTNAME());
			//			System.out.println(obj.getCUST().getCUSTADDRESS());

			//			ObjectFactory factory = new ObjectFactory(); 
			//			SvcCont svcCont2=factory.createContractRootSvcCont();
			//			svcCont2.getSOO();

		}

	}

	public static String toString(Node node) {
		String STRXML = null;
		try {
			OutputFormat format = new OutputFormat();
			format.setEncoding("GBK");
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
		//		return STRXML.replace("xmlns=\"http://www.chinatelecom.com/crm/comm/\"", "");
	}

}
