package com.ztesoft.vsop.order.manager.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.ztesoft.common.dict.ServiceList;


public class VsopCompareDealMidService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.VsopCompareDealMidBO  �滻ΪServiceListע��ķ��� 
	  2. searchVsopCompareDealMidData �ķ����Ĳ�������
	  3. findVsopCompareDealMidByCond() ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertVsopCompareDealMid(HashMap VsopCompareDealMid ) throws Exception {
		Map param = new HashMap() ;
		param.put("VsopCompareDealMid", VsopCompareDealMid) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"insertVsopCompareDealMid" ,param)) ;
		return result ;
	}

	
	public boolean updateVsopCompareDealMid(HashMap VsopCompareDealMid ) throws Exception {
		Map param = new HashMap() ;
		param.put("VsopCompareDealMid", VsopCompareDealMid) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"updateVsopCompareDealMid" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchVsopCompareDealMidData(String oa, 
												  String oaType,
												  String status, 
												  String fileName,
												  String isAdjust,
												  int pageIndex , 
												  int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("oa", oa) ;
		param.put("oaType", oaType) ;
		param.put("status", status) ;
		param.put("fileName", fileName) ;
		param.put("isAdjust", isAdjust) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"searchVsopCompareDealMidData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getVsopCompareDealMidById(String streaming_no) throws Exception {
		Map param = getVsopCompareDealMidKeyMap() ;
		param.put("streaming_no", streaming_no);
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"getVsopCompareDealMidById" ,param)) ;
		
		return result ;
	}
	

	public List findVsopCompareDealMidByCond() throws Exception {
		Map param = getVsopCompareDealMidKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"findVsopCompareDealMidByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteVsopCompareDealMidById() throws Exception {
		Map param = getVsopCompareDealMidKeyMap() ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
						"deleteVsopCompareDealMidById" ,param)) ;
		
		return result ;
	}
	/**
	 * ����
	 * @param selectOa
	 * @param selectOaType
	 * @param selectFlag
	 * @param selectPackageId
	 * @param selectPprodOfferId
	 * @return
	 * @throws Exception
	 */
	public int batchAdjustOrder(List selectOa,
								List selectOaType,
								List selectFlag,
								List selectPackageId,
								List selectPprodOfferId,
								List selectStatus,
								List selectProdId)throws Exception{
		int num = 0;//��ǰ������ϵ�Ѿ������ı䵼�²����ٵ����ļ�������
		if(selectOa != null){
			for(int i=0; i < selectOa.size(); i++){
				Map param = getVsopCompareDealMidKeyMap() ;
				param.put("oa", selectOa.get(i));
				param.put("oaType", selectOaType.get(i));
				param.put("flag", selectFlag.get(i));
				param.put("packageId", selectPackageId.get(i));
				param.put("pprodOfferId", selectPprodOfferId.get(i));
				param.put("status", selectStatus.get(i));
				param.put("productId", selectProdId.get(i));
				int result = DataTranslate._int(ServiceManager.callJavaBeanService(ServiceList.VsopCompareDealMidBO,
												"batchAdjustOrder" ,
												param)) ;
				if(result != 0)num++;//result:0��ʶ�ɹ���1��ǰҪ�ֹ������Ķ�����ϵ�ڵ����Ѿ��ֱ���������2�����쳣����ʧ��
			}
		}
		return num;
	}
	private Map getVsopCompareDealMidKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
}
