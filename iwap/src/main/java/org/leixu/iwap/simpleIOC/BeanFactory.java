package org.leixu.iwap.simpleIOC;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 
 */

/**
 * @author E5400
 *
 */
public class BeanFactory {
	private Map<String, Object> beanMap = new HashMap<String, Object>();
	 
    /**
     * bean工厂的初始化.
     * @param xml xml配置文件
     */
    public void init(String xml) {
           try {
                  //读取指定的配置文�?
                  SAXReader reader = new SAXReader();
                  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                  //从class目录下获取指定的xml文件
                  InputStream ins = ClassLoader.getSystemResourceAsStream(xml);
                  
                  
                  Document doc = reader.read(ins);
                  Element root = doc.getRootElement();   
                  Element element;
                  
                  //遍历bean
                  for (Iterator i = root.elementIterator("bean"); i.hasNext();) {   
                         element = (Element) i.next();
                         //获取bean的属性id和class
                         Attribute id = element.attribute("id");   
                         Attribute cls = element.attribute("class");
                         
                         //利用Java反射机制，�?过class的名称获取Class对象
                         Class bean = Class.forName(cls.getText());
                         
                         //获取对应class的信�?
                         java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
                         //获取其属性描�?
                         java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();
                         //设置值的方法
                         Method mSet = null;
                         //创建�?��对象
                         Object obj = bean.newInstance();
                         
                         //遍历该bean的property属�?
                         for (Iterator ite = element.elementIterator("property"); ite.hasNext();) {   
                                Element foo = (Element) ite.next();
                                //获取该property的name属�?
                                Attribute name = foo.attribute("name");
                                String value = null;
                                 
                                //获取该property的子元素value的�?
                                for(Iterator ite1 = foo.elementIterator("value"); ite1.hasNext();) {
                                       Element node = (Element) ite1.next();
                                       value = node.getText();
                                       break;
                                }
                                
                                for (int k = 0; k < pd.length; k++) {
                                       if (pd[k].getName().equalsIgnoreCase(name.getText())) {
                                              mSet = pd[k].getWriteMethod();
                                              //利用Java的反射极致调用对象的某个set方法，并将�?设置进去
                                              mSet.invoke(obj, value);
                                       }
                                }
                         }
                         
                         //将对象放入beanMap中，其中key为id值，value为对�?
                         beanMap.put(id.getText(), obj);
                  }
           } catch (Exception e) {
                  System.out.println(e.toString());
           }
    }
    
    /**
     * 通过bean的id获取bean的对�?
     * @param beanName bean的id
     * @return 返回对应对象
     */
    public Object getBean(String beanName) {
           Object obj = beanMap.get(beanName);
           return obj;
    }
    
    /**
     * 测试方法.
     * @param args
     * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a>
     * Creation date: 2007-10-6 - 上午11:21:14
     */
    public static void main(String[] args) {
           BeanFactory factory = new BeanFactory();
           factory.init("config.xml");
           JavaBean javaBean = (JavaBean) factory.getBean("javaBean");
           System.out.println("userName=" + javaBean.getUserName());
           System.out.println("password=" + javaBean.getPassword());
    }

}
