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
 * ҳ����
 */
public class BusiWizard {

	private static BusiWizard instance = null;

	private BusiWizard() {
		configure();
	}
	/**
	 * ��ȡҳ���򵼵ĵ���ģʽ
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
	 * ͨ������ֵ���ɵ���ҳ��
	 * @throws Exception 
	 */
	public List generate(Map arguments) throws Exception {

		// ͨ�������ṩ��page_wizard������л�ȡ����
		String serviceOfferId = Const.getStrValue(arguments, "service_offer_id");

		LinkedList wizards = new LinkedList();
		Iterator it = page_wizard_list.iterator();
		Map defWizard = new HashMap();
		while (it.hasNext()) {
			Map wizard = (Map) it.next();
			String servOfferId = Const.getStrValue(wizard, "service_offer_id");
			String searchType = Const.getStrValue(wizard, "search_type");
			if (serviceOfferId.equals(servOfferId)) {
				if (zero.equals(searchType)) {// ��������Ϊ0��ΪĬ�ϵ��򵼣��������͵�����Ҫ��������������ȡ
					defWizard.putAll(wizard);
				} else {
					wizards.add(wizard);
				}
			}
		}
		/***********ȡĬ��Ϊ0��·��**************************/
		if (wizards.isEmpty() && !defWizard.isEmpty()) {
			return getWizardPages(defWizard);
		}
		
		/*************ȡ����������·����ȡ������ȡĬ��Ϊ0������***/
		//�����SQL������͵ģ���ִ��Ԥ�����SQL���
		// �����JAVA��ģ���ִ��JAVA���룬�ж������Ƿ�����
		// �ҵ�ͨ�����͵ı���
		
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
			//���ִ�з���������ȡ������������·��
			if(searchResult){
				return  getWizardPages(wizard);
			}
		}
		//����������������ȥĬ�ϵ���ҳ��
		if(!searchResult)
			return getWizardPages(defWizard);
		
		return null;

		/** ************************************************************** */
	}
	//ͨ�����õ���ҳ���ȡ������
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
	 * ��ȡֵ��ѯ���
	 * */
	 private  List findBySql(String sql, List whereCondParams) throws FrameException {
			DynamicDict dto =  Base.query(JNDINames.CRM_DATASOURCE, sql, whereCondParams , Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
			return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	 }
	
	/**
	 * ������
	 * 
	 * @throws FrameException
	 */
	public void configure() {
		// ��ѯҳ�涨���б�
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
