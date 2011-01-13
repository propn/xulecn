/**
 * Classname    : StaticDataCache
 * Description  : ʵ�����ݻ���
 * Author       : cwf
 * Date         : 2004-05-31
 *
 * Last Update  : 2004-06-02
 * Author       : cwf
 * Version      : 1.0
 */

package com.ztesoft.component.common.staticdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAOFactory;
import com.ztesoft.component.common.staticdata.vo.StaticAttrListVO;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;

public class StaticAttrCache implements Serializable {
	private DynamicDict getDTO(String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1��JSP���ʣ�0��BHO����
		dto.flag = 0;// 1:Action;0:Service
		
		dto.m_ActionId = actionName;
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto ;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	//private static final Logger logger = Logger.getLogger(StaticAttrCache.class);

	private static Map attrCache = Collections.synchronizedMap(new HashMap());

	private static StaticAttrCache instance = new StaticAttrCache();

	//private static long lastUpdateCheck = System.currentTimeMillis();

	//private static long CYCLE_TIME = 1000 * 60 * 5; // 5min
	private final static String split="/";
	private StaticAttrCache() {
	}

	public static StaticAttrCache getInstance() {
		return instance;
	}

	public Map getAttrCache() {
		return attrCache;
	}

	public void setAttrData(String attrCode, ArrayList dataList) {
		attrCache.put(attrCode, dataList);
	}

	/**
	 * getAttrData �ӻ�����ȡ��̬���ݡ����Ҽ�������Ƿ��и��¹���
	 * 
	 * @param attrCode
	 * @return String
	 */
	public ArrayList getAttrData(String attrCode) {
		Object attrData = attrCache.get(attrCode);
		return attrData==null?new ArrayList():((ArrayList)attrData);
	}

	/**
	 * clearAttrDataCache ʵʱ����attrCache��
	 * 
	 * @param attrCode
	 * @return boolean
	 */
	public synchronized boolean clearAttrDataCache(String attrCode) {
		attrCache.remove(attrCode);
		attrCache.remove(attrCode.toLowerCase());
		attrCache.remove(attrCode.toUpperCase());
		
		return true;
	}

	/**
	 * updateStaticAttr ���������и��µ�ʱ������ñ��������ø���ʱ�䡣
	 * 
	 * @param attrCode
	 * @return boolean
	 */
	public synchronized boolean updateAttrData(String attrCode, List dataList) {

		if (attrCode == null || dataList == null)
			return false;

		attrCache.remove(attrCode);
		attrCache.remove(attrCode.toLowerCase());
		attrCache.remove(attrCode.toUpperCase());

		attrCache.put(attrCode, dataList);

		return true;
	}

	/**
	 * ȡ�þ�̬�������ݵ���ʾ���ݡ�
	 * 
	 * @param attrCode
	 * @param attrValue
	 * @return
	 * @throws Exception
	 */
	public String getAttrDesc(String attrCode, String attrValue)
			throws Exception {

		String attrDesc = "";
		ArrayList tempDataList = new ArrayList();
		tempDataList = getAttrData(attrCode);
		if (tempDataList == null || tempDataList.size() == 0) {
			tempDataList = getStaticAttr(attrCode);
		}
		if (tempDataList == null || tempDataList.size() == 0)
			return "";

		// ���� attrValue
		for (int i = 0; i < tempDataList.size(); i++) {
			StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
			if (attrValue != null && attrValue.equals(dataVO.getAttrValue()))
				attrDesc = dataVO.getAttrValueDesc();
		}

		return attrDesc;
	}

	/**
	 * ȡ�õ���ľ�̬�������ݡ�
	 * 
	 * @param attrCode
	 *            ��̬��������
	 * @throws Exception
	 */
	public ArrayList getStaticAttr(String attrCode) throws Exception {

		// cache
		ArrayList dataList = StaticAttrCache.getInstance()
				.getAttrData(attrCode);
		if (dataList == null || dataList.size() == 0) {
		

			DynamicDict dto = getDTO("StaticAttrBean" ,"getStaticAttr") ;
			dto.setValueByName("parameter", attrCode) ;
			ActionDispatch.dispatch(dto);
			dataList= ((ArrayList)dto.getValueByName("result")) ;

			
			if (dataList == null || dataList.size() == 0) {
				
				dto = getDTO("StaticDataBean" ,"getStaticDataByCode") ;
				dto.setValueByName("parameter", attrCode) ;
				ActionDispatch.dispatch(dto);
				dataList= ((ArrayList)dto.getValueByName("result")) ;
				
				if(dataList == null || dataList.size() == 0){
					//�Ҳ�����̬����
					throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
							"�Ҳ�����̬����, ��̬���ݶ�������" + attrCode));
				}
			}

			// put into cache
			StaticAttrCache.getInstance().setAttrData(attrCode, dataList);
		}

		return dataList;

	}
	
	/**
	 * ֻ��cache��ȡ��̬�������ݡ�
	 * 
	 * @param attrCode
	 *            ��̬��������
	 * @throws Exception
	 */
	public ArrayList getStaticAttrFromCache(String attrCode) {

		// cache
		ArrayList dataList = StaticAttrCache.getInstance()
				.getAttrData(attrCode);

		return dataList == null?new ArrayList():dataList;
	}
//���ӻ�����ȡ���ݣ�ֱ�Ӳ�ѯ���ݿ�
	public ArrayList getStaticAttrNotFromCache(String attrCode) throws Exception{

		// cache
		ArrayList dataList = StaticAttrDAOFactory.getInstance().getStaticAttrDAO().findSingleByCode(attrCode);

		return dataList == null?new ArrayList():dataList;
	}
	
	
	/**
	 * ȡ�õ���ľ�̬�������ݡ�
	 * 
	 * @param attrCode
	 *            ��̬��������
	 * @throws Exception 
	 * @throws Exception
	 */
	public boolean initStaticAttr() throws Exception{
		DynamicDict staticAttr = getDTO("StaticAttrBean" ,"getAllStaticAttr") ;
//		staticAttr.setValueByName("parameter", "null") ;
		staticAttr = ActionDispatch.dispatch(staticAttr);
		
//		StaticAttrBean staticAttr = StaticAttrBean.getInstance();
		ArrayList dataList = (ArrayList)staticAttr.getValueByName("result") ;//staticAttr.getAllStaticAttr();

		for (Iterator iter = dataList.iterator(); iter.hasNext();) {
			StaticAttrListVO listVO = (StaticAttrListVO) iter.next();
			StaticAttrCache.getInstance().setAttrData(listVO.getAttrCode(),
					listVO.getDataList());
		}
//
//		StaticDataBean staticData = StaticDataBean.getInstance();
//		
//		dataList = staticData.getAllStaticData();

		DynamicDict staticData = getDTO("StaticDataBean" ,"getAllStaticData") ;
		staticData.setValueByName("parameter", "null") ;
		staticData = ActionDispatch.dispatch(staticData);
		
//		StaticAttrBean staticAttr = StaticAttrBean.getInstance();
		dataList = (ArrayList)staticData.getValueByName("result") ;//staticAttr.getAllStaticAttr();

		
		
		for (Iterator iter = dataList.iterator(); iter.hasNext();) {
			StaticAttrListVO listVO = (StaticAttrListVO) iter.next();
			StaticAttrCache.getInstance().setAttrData(listVO.getAttrCode(),
					listVO.getDataList());
		}

		return true;

	}

	public boolean initStaticSingleData(String attrCode)throws Exception {

//		StaticDataBean staticData = StaticDataBean.getInstance();
		
		List dataList = null;
//			dataList = staticData.getStaticDataByCode(attrCode);


			DynamicDict staticData = getDTO("StaticDataBean" ,"getStaticDataByCode") ;
			staticData.setValueByName("parameter", "null") ;
			staticData = ActionDispatch.dispatch(staticData);
			
//			StaticAttrBean staticAttr = StaticAttrBean.getInstance();
			dataList = (ArrayList)staticData.getValueByName("result") ;//staticAttr.getAllStaticAttr();
	
			
		if (dataList != null && dataList.size() > 0) {
			return updateAttrData(attrCode, dataList);
		} else
			return false;

	}

	public static void main(String[] args ){
		StaticAttrCache c = new StaticAttrCache() ;
		try {
			c.initStaticAttr();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*	*//**
	 * ͨ�����Ա����ȡ������ȡֵ,ƴ���б�
	 **/
	public static String getSplitString(String attrCode,String splitKey){
		
//		String valueKey=attrCode+PropCache.valueMapKey;//����ֵ�Ĺؼ���
//		String descKey=attrCode+PropCache.descMapKey;//����ֵ������Ϣ�Ĺؼ���
//		String valueSplitString="";//ֵ��
//		String descSplitString="";//������
//		if(attrCache.keySet().contains(splitKey)){
//			//�ӻ�����ȡ����
//			return String.valueOf(attrCache.get(splitKey));
//		}
//		List list=(ArrayList) attrCache.get(attrCode);
//		
//		//�������������Ϊ�գ���ѿմ������ڻ�����
//		if(list==null||list.isEmpty()){
//			attrCache.put(valueKey, "");
//			attrCache.put(descKey, "");
//			return "";
//		}
//	
//		//ƴ��SPLIT��
//		
//		
//		Iterator listIt=list.iterator();
//		while(listIt.hasNext()){
//			StaticAttrVO attrvo=(StaticAttrVO)listIt.next();
//			valueSplitString+=attrvo.getAttrValue();
//			descSplitString+=attrvo.getAttrValueDesc();
//			if(listIt.hasNext()){
//				valueSplitString+=split;
//				descSplitString+=split;
//			}
//			
//		}
//		attrCache.put(valueKey, valueSplitString);
//		attrCache.put(descKey, descSplitString);
		return String.valueOf(attrCache.get(splitKey));
	}

}

