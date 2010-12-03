package com.ztesoft.crm.business.views.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames;

/**
 * 页面向导
 */
public class BusiWizard {

	private static BusiWizard instance = null;

	private BusiWizard() {
		configure();
	}
	/**
	 * 获取页面向导的单例模式
	 */
	public static BusiWizard getInst() {

		if (instance == null) {
			synchronized (BusiWizard.class) {

				if (instance == null) {
					instance = new BusiWizard();
				}
			}
		}
		return instance;
	}


	/**
	 * 通过参数值生成导航页面
	 * @throws Exception 
	 */
	public List generate(Map arguments) throws Exception {

		// 通过服务提供从page_wizard定义表中获取数据
		String serviceOfferId = Const.getStrValue(arguments, "service_offer_id");

		LinkedList wizards = new LinkedList();
		Iterator it = page_wizard_list.iterator();
		Map defWizard = new HashMap();
		while (it.hasNext()) {
			Map wizard = (Map) it.next();
			String servOfferId = Const.getStrValue(wizard, "service_offer_id");
			String searchType = Const.getStrValue(wizard, "search_type");
			if (serviceOfferId.equals(servOfferId)) {
				if (zero.equals(searchType)) {// 查找类型为0的为默认的向导，其它类型的则需要经过条件编译后获取
					defWizard.putAll(wizard);
				} else {
					wizards.add(wizard);
				}
			}
		}
		/***********取默认为0的路径**************************/
		if (wizards.isEmpty() && !defWizard.isEmpty()) {
			return getWizardPages(defWizard);
		}
		
		/*************取符合条件的路径，取不到就取默认为0的类型***/
		//如果是SQL语句类型的，则执行预编译的SQL语句
		// 如果是JAVA类的，则执行JAVA代码，判断条件是否满足
		// 找到通用类型的变量
		
		SearchInvoker sqlSearchInvoker=new SQLSeqrchInvoker();
		SearchInvoker javaSearchInvoker=new JavaSearchInvoker();
		
		boolean searchResult=false;
		
		it=wizards.iterator();
		while (it.hasNext()) {
			Map wizard = (Map) it.next();
			String searchType = Const.getStrValue(wizard, "search_type");
			String searchCondition = Const.getStrValue(wizard, "search_condition");
			if(one.equals(searchType)){
				searchResult=sqlSearchInvoker.invoke(searchCondition, arguments);
			}else if(two.equals(searchType)){
				searchResult=javaSearchInvoker.invoke(searchCondition, arguments);
			}
			//如果执行符合条件则取，符合条件的路径
			if(searchResult){
				return  getWizardPages(wizard);
			}
		}
		//不满足拦截条件就去默认的向导页面
		if(!searchResult)
			return getWizardPages(defWizard);
		
		return null;

		/** ************************************************************** */
	}
	//通过配置的向导页面获取向导数据
	private List getWizardPages(Map wizard) {
		List result = null;
		String path = Const.getStrValue(wizard, "path");
		if (path == null || "".equals(path))
			return null;
		String[] paths = path.split("-");
		for (int i = 0; i < paths.length; i++) {
			if (page_define_list != null && !page_define_list.isEmpty()) {
				Iterator pageIt = page_define_list.iterator();
				while (pageIt.hasNext()) {
					Map page = (Map) pageIt.next();
					String code = Const.getStrValue(page, "code");
					if (code.equals(paths[i])) {
						if(result==null)
							result=new ArrayList(); 
						result.add(page);
						break;
					}
				}
			}
		}
		return result;
	}

	
	private  final String QUERY_PAGE_WIZARD_SQL="select service_offer_id,search_type,search_condition,path from page_wizard ";
	
	private  final String QUERY_PAGE_DEFINE_SQL="select code,url,comments from page_define ";
	
	 /**
	 * 获取值查询结果
	 * */
	 private  List findBySql(String sql, List whereCondParams) throws FrameException {
			DynamicDict dto =  Base.query(JNDINames.CRM_DATASOURCE, sql, whereCondParams , Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
			return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	 }
	
	/**
	 * 配置类
	 * 
	 * @throws FrameException
	 */
	public void configure() {
		// 查询页面定义列表
		try {

				page_define_list = findBySql(QUERY_PAGE_DEFINE_SQL, null);
	
				page_wizard_list = findBySql(QUERY_PAGE_WIZARD_SQL, null);

		} catch (FrameException e) {
			e.printStackTrace();
		}

	}
	public final static String two = "2";
	public final static String one = "1";
	public final static String zero = "0";

	public List page_define_list;

	public List page_wizard_list;

}
