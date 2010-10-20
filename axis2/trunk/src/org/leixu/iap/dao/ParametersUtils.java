/**
 * 单例参数工具类
 */
package org.leixu.iap.dao;


/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ParametersUtils {
	
//	private static final Parameters singleton = new Parameters();
	private final ThreadLocal perThreadInstance = new ThreadLocal();
	
    private ParamsUtils helper = null;
    public ParamsUtils getHelper() {
        if (perThreadInstance.get() == null) createHelper();
        return helper;
    }
    private final void createHelper() {
        synchronized(this) {
            if (helper == null){
                helper = new ParamsUtils();
//                Configuration config = new PropertiesConfiguration("usergui.properties");

            }
        }
        perThreadInstance.set(perThreadInstance);
    }

}
