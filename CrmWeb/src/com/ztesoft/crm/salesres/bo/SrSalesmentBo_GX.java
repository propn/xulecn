package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.salesres.dao.InfRcToCardDAO;
import com.ztesoft.crm.salesres.dao.RcFamilyDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.InfRcToCardVO;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;
import com.ztesoft.crm.salesres.vo.SalesRescIdRelaVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

public class SrSalesmentBo_GX{
  public SrSalesmentBo_GX() {
  }
/**
 * webservice ͨ��ϵͳ��ʱ���ȣ��磬ÿ��3minִ�У�ͨ��ejb���ø÷����Ի���ڵ���ʱ������д��������
 * @return
 * @throws Exception
 */
  public List getRcToCardRecordsByRealTime() throws Exception{
	 InfRcToCardDAO dao =  SrDAOFactory.getInstance().getInfRcToCardDAO();
	 
	 String schedule = "180";//ϵͳ�Ա�ɨ���ʱ��������secondΪ��λ
	 
	  List list = dao.findByCond(" STATE='0' AND TS >= current_date-"+schedule+"/24/60/60 and TS<=current_date order by seq_id desc");
	  //��voת��Ϊmap
	  List rtcList = new ArrayList();
	 if(list != null){
		 for (int i=0;i<list.size();i++){
			 rtcList.add(((InfRcToCardVO)list.get(i)).unloadToHashMap());
		 }
	 }
	  return rtcList;
  }

  /**
   * webservice ͨ��ϵͳ��ʱ����ÿ�����϶�ʱ�ѵ��������۳��⡢�˿�����ݣ��ٴ�һ�θ�������ϵͳ,���趨��ǰ�յ� 00:00:00 ��ǰ�յ� 23:59:59
   * @return
   * @throws Exception
   */
    public List getRcToCardRecordsByDay() throws Exception{
  	 InfRcToCardDAO dao =  SrDAOFactory.getInstance().getInfRcToCardDAO();
  	 
  	 //String schedule = String.valueOf(new Integer(60*60*24));//ϵͳ�Ա�ɨ���ʱ��������secondΪ��λ
  	 
  	  List list = dao.findByCond(" TS >= trunc(current_date-1) and TS<=trunc(current_date)-1/24/60/60 order by seq_id desc");
  	  
  	  return list;
    }
    
    /**
     * ����ӿ�����ϵͳ���صĴ�������
     * @throws Exception
     */
    public void handleReturnInfo(List list) throws Exception{
    	//����state״̬
    	SrDAOFactory.getInstance().getInfRcToCardDAO().updateStateByBatch(list,new String[]{"resultCode","logId"});
    	//�Գɹ����ص�,state=2�����ݲ��뵽infl_rc_to_card����
    	
    	SrDAOFactory.getInstance().getInflRcToCardDAO().insertByBatch(list);
    }
    
    /**
     * ��webservice����˱��ͻ��˵���ʱ��ͨ��ejb���ô˷����������Ŀ�������ݸ��µ��ֿ���
     * @param map
     * @return
     * ��Σ�
     * ����	����
	businessid	     Ӫҵ��ID	
	cardSerialCode	����Ϣ���к�
	cardTypeName	��������
	crmSerialCode	Crm������
	cardSubject		������
	cardNumber		��־��
	cardTypeName	��������
	cardValue		����ֵ
	cardAddValue	��������ֵ
	cardMatureDate	����������
	cardCount		������
	cardAccount		ʵ�ս��
	���Σ�
	����	����
	resultCode		���ؽ�� 0���ɹ� -1��ʧ��
	errorInfo		������Ϣ
	crmSerialCode	Crm������
//////////////////////////////////////////////
 * ���crmserialcodeΪ�գ�����������������һ�ֿ�����
 * ���򣬱�ʾcrmϵͳ���뿨����cardSerialCode��Ӧ�ÿ���Դ����
     */
public HashMap gxCardToRc(HashMap map)throws Exception {
	HashMap rtnMap = new HashMap();
	Object businessid =null;
	Object cardSerialCode = null;
	Object cardTypeName = null;
	Object crmSerialCode = null;
	Object cardSubject = null;
	Object cardNumber = null;
	Object cardValue = null;
	Object cardAddValue = null;
	Object cardMatureDate = null;
	Object cardCount = null;
	Object cardAccount = null;
	
	int resultCode = 0;
	String errorInfo = "";
	
	if(map == null){
		rtnMap.put("resultCode", new Integer(-1));
		rtnMap.put("errorInfo", "δ�õ���Ч����");
		rtnMap.put("crmSerialCode", crmSerialCode);
		return rtnMap;
	}else{
		businessid = map.get("businessid");
		cardSerialCode = map.get("cardSerialCode");
		cardTypeName= map.get("cardTypeName");
		crmSerialCode= map.get("crmSerialCode");
		cardSubject= map.get("cardSubject");
		cardNumber= map.get("cardNumber");
		cardValue= map.get("cardValue");
		cardAddValue= map.get("cardAddValue");
		cardMatureDate= map.get("cardMatureDate");
		cardCount= map.get("cardCount");
		cardAccount= map.get("cardAccount");
	}
	if (businessid==null ||cardSerialCode==null ||cardTypeName==null||cardSubject==null|| cardNumber==null 
			||cardValue==null ||cardAddValue==null ||cardMatureDate==null ||cardCount==null){
		rtnMap.put("resultCode", new Integer(-1));
		rtnMap.put("errorInfo", "δ�õ���Ч����");
		rtnMap.put("crmSerialCode", crmSerialCode);
		return rtnMap;
	}
	
	if (crmSerialCode==null || crmSerialCode.equals("")){
		String[] sqlList = {"DC_SQL"};
		List familyList = SrDAOFactory.getInstance().getSqlComDAO().qryComSql("select DC_SQL from dc_sql where DC_NAME='Rc_Card_FamilyId'",sqlList);
		if(familyList == null || familyList.size() == 0 ){
			resultCode = -1;
			errorInfo = "�޷�ȡ�ÿ���Դ�ü�����Ϣ";
		}else{
		crmSerialCode = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence("SEQ_SALES_RESOURCE_ID");
		SalesRescVO srvo = new SalesRescVO();
		srvo.setFamilyId(String.valueOf(((HashMap)familyList.get(0)).get("DC_SQL")));
		srvo.setSalesRescId(String.valueOf(crmSerialCode));
		srvo.setSalesRescName(String.valueOf(cardTypeName));
		srvo.setManageMode("1");
		srvo.setSalesRescCode(String.valueOf(crmSerialCode));
		srvo.setCreateDate(DateFormatUtils.getFormatedDateTime());
		srvo.setEffDate(srvo.getCreateDate());
		srvo.setStateDate(srvo.getCreateDate());
		srvo.setExpDate(String.valueOf(cardMatureDate));
		srvo.setState("00A");
		srvo.setSalesRescWorth(String.valueOf(cardValue));
		SalesRescDAO sdao = SrDAOFactory.getInstance().getSalesRescDAO();
		
		try{
			sdao.insert(srvo);
		}catch(DAOSystemException ex){
			resultCode = -1;
			errorInfo = "���ɿ���Դʧ��";
			String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
			ex.printStackTrace();
			throw new DAOSystemException(errmsg);
		}
		List attrList = SrDAOFactory.getInstance().getRcFamilyDAO().findAttrInfo2(srvo.getFamilyId(), "0");
		if(attrList != null && attrList.size() >= 5){
			for(int i=0;i<attrList.size();i++){
				RcAttrDefVO attvo = (RcAttrDefVO)attrList.get(i);
				try{
					sdao.insertAttrInfo(srvo.getSalesRescId(), attvo.getAttrId(), String.valueOf(map.get(attvo.getAttrName())));
				}catch(DAOSystemException ex){
					resultCode = -1;
					errorInfo = "���ɿ���Դ����ʧ��";
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
				}
			}
		}else{
			resultCode = -1;
			errorInfo = "����Դ���Զ��岻��ȷ";
		}
			
		if (resultCode ==0){
			RcStorageVO vo = SrDAOFactory.getInstance().getRcStorageDAO().findByPrimaryKey(String.valueOf(businessid));
			if(vo == null){
				resultCode = -1;
				errorInfo = "�ֿ�ʵ��������";
			}else if(vo.getStorageState()==null || !vo.getStorageState().equals("0")){
				resultCode = -1;
				errorInfo = "�ֿ�ʵ��������";
			}else{
				RcStockVO stockvo = new RcStockVO();
				stockvo.setSalesRescId(String.valueOf(crmSerialCode));
				stockvo.setStorageId(String.valueOf(businessid));
				stockvo.setStockAmount(String.valueOf(cardCount));
				
				try{
					SrDAOFactory.getInstance().getRcStockDAO().insert(stockvo);//
				}catch(DAOSystemException ex){
					resultCode = -1;
					errorInfo = "���ɿ���Դ�����Ϣʧ��";
					ex.printStackTrace();
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
				}
				SalesRescIdRelaVO salesRelavo = new SalesRescIdRelaVO();
				salesRelavo.setDcDeviceScode("xXx");
				salesRelavo.setSalesRescId(String.valueOf(crmSerialCode));
				salesRelavo.setNcSalesRescId(String.valueOf(cardSerialCode));
				try{
					SrDAOFactory.getInstance().getSalesRescIdRelaDAO().insert(salesRelavo);
				}catch(DAOSystemException ex){
					resultCode = -1;
					errorInfo = "���ɿ���Դ��Ӫ����Դ��Ӧ��ϵʧ��";
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
					
				}
			}
		}
		
	}
		
	}else{
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		
		RcStockVO vo = dao.findByPrimaryKey(String.valueOf(crmSerialCode), String.valueOf(businessid));
		if(vo!=null){
			SalesRescVO svo = SrDAOFactory.getInstance().getSalesRescDAO().findByPrimaryKey(vo.getSalesRescId());
			if(svo.getSalesRescId().equals("")){
				resultCode = -1;
				errorInfo = "����Դ������";
			}else if(!svo.getState().equals("00A")){
				resultCode = -1;
				errorInfo = "����Դ������";
			/*}else if(Integer.parseInt(vo.getDownLimit())>Integer.parseInt(String.valueOf(cardAccount))){
				resultCode = -1;
				errorInfo = "�������Ѿ����ڸòֿ������";
			}else if(Integer.parseInt(vo.getUpLimit())<Integer.parseInt(String.valueOf(cardAccount))){
				resultCode = -1;
				errorInfo = "�������Ѿ����ڸòֿ������";
			}else{
				vo.setStockAmount(String.valueOf(cardAccount));*/
			}else{
				SalesRescIdRelaVO salesRelaVO = SrDAOFactory.getInstance().getSalesRescIdRelaDAO().findByPrimaryKey("xXx", String.valueOf(cardSerialCode), String.valueOf(crmSerialCode));
				if(salesRelaVO.getSalesRescId().equals("")&& salesRelaVO.getNcSalesRescId().equals("")&& salesRelaVO.getDcDeviceScode().equals("")){
					resultCode = -1;
					errorInfo = "����Դ��Ӫ����Դ�޶�Ӧ��ϵ";
				}
			}
			if (resultCode == 0 ){
				/*RcStockLimitVO rclivo = SrDAOFactory.getInstance().getRcStockLimitDAO().findByPrimaryKey(svo.getFamilyId(), vo.getStorageId());
				//System.out.println(svo.getFamilyId()+"--"+vo.getStorageId()+"--"+rclivo.getDownLimit()+"--"+rclivo.getUpLimit());
				if(rclivo!=null && rclivo.getDownLimit()!=null && rclivo.getUpLimit()!=null
						&& !rclivo.getDownLimit().equals("") && !rclivo.getUpLimit().equals("")){
					if(Integer.parseInt(rclivo.getDownLimit())>Integer.parseInt(String.valueOf(cardCount)) 
							||Integer.parseInt(rclivo.getUpLimit())<Integer.parseInt(String.valueOf(cardCount)) ){
						resultCode = -1;
						errorInfo = "���������ڲֿ�����������֮��";
					}
				}*/
				if(resultCode == 0){
					vo.setStockAmount(String.valueOf(cardCount));
					try{
						RcStockDAO stockdao = SrDAOFactory.getInstance().getRcStockDAO();
						vo.setStockAmount(String.valueOf(cardCount));
						if(stockdao.findByPrimaryKey(vo.getSalesRescId(), vo.getStorageId())==null){
							stockdao.insert(vo);
						}else{
							stockdao.updateAmount(vo.getStorageId(), vo.getSalesRescId(), vo.getStockAmount());
						}
					}catch(DAOSystemException ex){
						resultCode = -1;
						errorInfo = "���¿������ʧ��";
						ex.printStackTrace();
						String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
						ex.printStackTrace();
						throw new DAOSystemException(errmsg);
						
					}
				}
				
			}
			
		}else{
			resultCode = -1;
			errorInfo = "����Դ�Ͳֿ��޶�Ӧ��ϵ�����޸ÿ���Դ�����޸òֿ�ʵ��";
			
		}
		
	}
	
	if(resultCode == -1){
		String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
		
		throw new DAOSystemException(errmsg);
		
	}
	rtnMap.put("resultCode", new Integer(resultCode));
	rtnMap.put("errorInfo", errorInfo);
	rtnMap.put("crmSerialCode", crmSerialCode);
	return rtnMap;
}

/**
 * 
 * @param map
 * @return
 * @throws Exception
 * ��Σ�
 * Name	Description
	serialNo	������ˮ��
	timeFlag	ʱ��
	terminalModel	�ն��ͺ�
	terminalType	�ն�����
	cost	�ն˳ɱ�
	batchNum	����
	manufacturer	����

	���Σ�
	Name	Description
	ResultCode	���ؽ��
	ErrorInfo	������Ϣ
	crmSerialCode	Crm������

 */
public HashMap gxM8ToRc(HashMap map)throws Exception{
	HashMap rtnmap = new HashMap();
	int resultCode = 0;
	String errorInfo = "";
	String crmSerialCode ="";
	if(map == null){
		resultCode = -1;
		errorInfo = "�޷��õ�����";
		rtnmap.put("resultCode", new Integer(resultCode));
		rtnmap.put("errorInfo", errorInfo);
		rtnmap.put("crmSerialCode", "");
		return rtnmap	;
	}
	Object serialNo = map.get("serialNo");
	Object timeFlag = map.get("timeFlag");
	Object terminalModel = map.get("terminalModel");
	Object terminalType = map.get("terminalType");
	Object cost = map.get("cost");
	Object batchNum = map.get("batchNum");
	Object manufacturer = map.get("manufacturer");
	
	if(serialNo == null || "".equals(serialNo)||timeFlag==null ||"".equals(timeFlag)
			||terminalModel == null || "".equals(terminalModel) || terminalType==null ||"".equals(terminalType) || cost==null ||
			"".equals(cost) || batchNum == null ||"".equals(batchNum) || manufacturer == null ||"".equals(manufacturer)){
		resultCode = -1;
		errorInfo = "�޷��õ�����";
		rtnmap.put("resultCode", new Integer(resultCode));
		rtnmap.put("errorInfo", errorInfo);
		rtnmap.put("crmSerialCode", "");
		return rtnmap	;
	}
	
	String[] sqlList = {"DC_SQL"};
	List familyList = SrDAOFactory.getInstance().getSqlComDAO().qryComSql("select DC_SQL from dc_sql where DC_NAME='Rc_Terminal_FamilyId'",sqlList);
	if(familyList == null || familyList.size() == 0 ){
		resultCode = -1;
		errorInfo = "�޷�ȡ���ն���Դ�ü�����Ϣ";
	}else{
		crmSerialCode = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence("SEQ_SALES_RESOURCE_ID");
		SalesRescVO srvo = new SalesRescVO();
		srvo.setFamilyId(String.valueOf(((HashMap)familyList.get(0)).get("DC_SQL")));
		srvo.setSalesRescId(String.valueOf(crmSerialCode));
		srvo.setSalesRescName(String.valueOf(terminalModel));
		srvo.setManageMode("0");
		srvo.setSalesRescCode(String.valueOf(crmSerialCode));
		srvo.setCreateDate(DateFormatUtils.getFormatedDateTime());
		srvo.setEffDate(srvo.getCreateDate());
		srvo.setStateDate(srvo.getCreateDate());
		srvo.setExpDate(String.valueOf("3000-9-9"));
		srvo.setState("00A");
		srvo.setSalesRescWorth(String.valueOf(cost));
		SalesRescDAO sdao = SrDAOFactory.getInstance().getSalesRescDAO();
		
		try{
			sdao.insert(srvo);
		}catch(DAOSystemException ex){
			resultCode = -1;
			errorInfo = "�����ն���Դʧ��";
			String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
			ex.printStackTrace();
			throw new DAOSystemException(errmsg);
		}
		//System.out.println(srvo.getFamilyId());
		RcFamilyDAO familydao = SrDAOFactory.getInstance().getRcFamilyDAO();
		familydao.setAtt_flag(1);
		List attrList = familydao.findAttrInfo2(srvo.getFamilyId(), "1");
		if(attrList != null && attrList.size() >= 3){
			for(int i=0;i<attrList.size();i++){
				RcAttrDefVO attvo = (RcAttrDefVO)attrList.get(i);
				try{
					sdao.insertAttrInfo(srvo.getSalesRescId(), attvo.getAttrId(), String.valueOf(map.get(attvo.getAttrName())));
				}catch(DAOSystemException ex){
					resultCode = -1;
					errorInfo = "�����ն���Դ����ʧ��";
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
				}
			}
		}else{
			resultCode = -1;
			errorInfo = "�ն���Դ���Զ��岻��ȷ";
			String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
			
			throw new DAOSystemException(errmsg);
		}
	}
	rtnmap.put("resultCode", new Integer(resultCode));
	rtnmap.put("errorInfo", errorInfo);
	rtnmap.put("crmSerialCode", crmSerialCode);
	
	return rtnmap;
	
}
}
