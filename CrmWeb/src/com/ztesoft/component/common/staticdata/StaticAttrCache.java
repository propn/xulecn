/**
 * Classname    : StaticDataCache
 * Description  : 实现数据缓存
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
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
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
	 * getAttrData 从缓存中取静态数据。并且检查数据是否有更新过。
	 * 
	 * @param attrCode
	 * @return String
	 */
	public ArrayList getAttrData(String attrCode) {
		Object attrData = attrCache.get(attrCode);
		return attrData==null?new ArrayList():((ArrayList)attrData);
	}

	/**
	 * clearAttrDataCache 实时更新attrCache。
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
	 * updateStaticAttr 属性数据有更新的时候，请调用本方法设置更新时间。
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
	 * 取得静态属性数据的显示内容。
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

		// 查找 attrValue
		for (int i = 0; i < tempDataList.size(); i++) {
			StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
			if (attrValue != null && attrValue.equals(dataVO.getAttrValue()))
				attrDesc = dataVO.getAttrValueDesc();
		}

		return attrDesc;
	}

	/**
	 * 取得单层的静态属性数据。
	 * 
	 * @param attrCode
	 *            静态属性名称
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
					//找不到静态数据
					throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
							"找不到静态数据, 静态数据定义名：" + attrCode));
				}
			}

			// put into cache
			StaticAttrCache.getInstance().setAttrData(attrCode, dataList);
		}

		return dataList;

	}
	
	/**
	 * 只在cache中取静态属性数据。
	 * 
	 * @param attrCode
	 *            静态属性名称
	 * @throws Exception
	 */
	public ArrayList getStaticAttrFromCache(String attrCode) {

		// cache
		ArrayList dataList = StaticAttrCache.getInstance()
				.getAttrData(attrCode);

		return dataList == null?new ArrayList():dataList;
	}
//不从缓存中取数据，直接查询数据库
	public ArrayList getStaticAttrNotFromCache(String attrCode) throws Exception{

		// cache
		ArrayList dataList = StaticAttrDAOFactory.getInstance().getStaticAttrDAO().findSingleByCode(attrCode);

		return dataList == null?new ArrayList():dataList;
	}
	
	
	/**
	 * 取得单层的静态属性数据。
	 * 
	 * @param attrCode
	 *            静态属性名称
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
	 * 通过属性编码获取，属性取值,拼串列表
	 **/
	public static String getSplitString(String attrCode,String splitKey){
		
//		String valueKey=attrCode+PropCache.valueMapKey;//缓存值的关键字
//		String descKey=attrCode+PropCache.descMapKey;//缓存值描述信息的关键字
//		String valueSplitString="";//值串
//		String descSplitString="";//描述串
//		if(attrCache.keySet().contains(splitKey)){
//			//从缓存中取数据
//			return String.valueOf(attrCache.get(splitKey));
//		}
//		List list=(ArrayList) attrCache.get(attrCode);
//		
//		//如果缓存中数据为空，则把空串放置在缓存中
//		if(list==null||list.isEmpty()){
//			attrCache.put(valueKey, "");
//			attrCache.put(descKey, "");
//			return "";
//		}
//	
//		//拼凑SPLIT串
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

