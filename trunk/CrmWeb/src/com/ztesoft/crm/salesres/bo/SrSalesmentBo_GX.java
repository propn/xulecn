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
 * webservice 通过系统定时调度（如，每隔3min执行）通过ejb调用该方法以获得在调度时间间隔内写入表得数据
 * @return
 * @throws Exception
 */
  public List getRcToCardRecordsByRealTime() throws Exception{
	 InfRcToCardDAO dao =  SrDAOFactory.getInstance().getInfRcToCardDAO();
	 
	 String schedule = "180";//系统对表扫描得时间间隔，以second为单位
	 
	  List list = dao.findByCond(" STATE='0' AND TS >= current_date-"+schedule+"/24/60/60 and TS<=current_date order by seq_id desc");
	  //将vo转换为map
	  List rtcList = new ArrayList();
	 if(list != null){
		 for (int i=0;i<list.size();i++){
			 rtcList.add(((InfRcToCardVO)list.get(i)).unloadToHashMap());
		 }
	 }
	  return rtcList;
  }

  /**
   * webservice 通过系统定时调度每天晚上定时把当天所销售出库、退库的数据，再传一次给卡管理系统,暂设定在前日得 00:00:00 至前日得 23:59:59
   * @return
   * @throws Exception
   */
    public List getRcToCardRecordsByDay() throws Exception{
  	 InfRcToCardDAO dao =  SrDAOFactory.getInstance().getInfRcToCardDAO();
  	 
  	 //String schedule = String.valueOf(new Integer(60*60*24));//系统对表扫描得时间间隔，以second为单位
  	 
  	  List list = dao.findByCond(" TS >= trunc(current_date-1) and TS<=trunc(current_date)-1/24/60/60 order by seq_id desc");
  	  
  	  return list;
    }
    
    /**
     * 处理从卡管理系统返回的处理结果。
     * @throws Exception
     */
    public void handleReturnInfo(List list) throws Exception{
    	//更新state状态
    	SrDAOFactory.getInstance().getInfRcToCardDAO().updateStateByBatch(list,new String[]{"resultCode","logId"});
    	//对成功返回的,state=2，数据插入到infl_rc_to_card表中
    	
    	SrDAOFactory.getInstance().getInflRcToCardDAO().insertByBatch(list);
    }
    
    /**
     * 当webservice服务端被客户端调用时，通过ejb调用此方法将传来的卡库存数据更新到仓库里
     * @param map
     * @return
     * 入参：
     * 名称	描述
	businessid	     营业厅ID	
	cardSerialCode	卡信息序列号
	cardTypeName	卡种名称
	crmSerialCode	Crm规格序号
	cardSubject		卡主题
	cardNumber		卡志号
	cardTypeName	卡种名称
	cardValue		卡面值
	cardAddValue	卡附加面值
	cardMatureDate	卡到期日期
	cardCount		卡张数
	cardAccount		实收金额
	出参：
	名称	描述
	resultCode		返回结果 0：成功 -1：失败
	errorInfo		错误信息
	crmSerialCode	Crm规格序号
//////////////////////////////////////////////
 * 如果crmserialcode为空，表明卡管理新增了一种卡类型
 * 否则，表示crm系统中与卡管理cardSerialCode对应得卡资源种类
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
		rtnMap.put("errorInfo", "未得到有效参数");
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
		rtnMap.put("errorInfo", "未得到有效参数");
		rtnMap.put("crmSerialCode", crmSerialCode);
		return rtnMap;
	}
	
	if (crmSerialCode==null || crmSerialCode.equals("")){
		String[] sqlList = {"DC_SQL"};
		List familyList = SrDAOFactory.getInstance().getSqlComDAO().qryComSql("select DC_SQL from dc_sql where DC_NAME='Rc_Card_FamilyId'",sqlList);
		if(familyList == null || familyList.size() == 0 ){
			resultCode = -1;
			errorInfo = "无法取得卡资源得家族信息";
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
			errorInfo = "生成卡资源失败";
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
					errorInfo = "生成卡资源属性失败";
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
				}
			}
		}else{
			resultCode = -1;
			errorInfo = "卡资源属性定义不正确";
		}
			
		if (resultCode ==0){
			RcStorageVO vo = SrDAOFactory.getInstance().getRcStorageDAO().findByPrimaryKey(String.valueOf(businessid));
			if(vo == null){
				resultCode = -1;
				errorInfo = "仓库实例不存在";
			}else if(vo.getStorageState()==null || !vo.getStorageState().equals("0")){
				resultCode = -1;
				errorInfo = "仓库实例不可用";
			}else{
				RcStockVO stockvo = new RcStockVO();
				stockvo.setSalesRescId(String.valueOf(crmSerialCode));
				stockvo.setStorageId(String.valueOf(businessid));
				stockvo.setStockAmount(String.valueOf(cardCount));
				
				try{
					SrDAOFactory.getInstance().getRcStockDAO().insert(stockvo);//
				}catch(DAOSystemException ex){
					resultCode = -1;
					errorInfo = "生成卡资源库存信息失败";
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
					errorInfo = "生成卡资源和营销资源对应关系失败";
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
				errorInfo = "卡资源不存在";
			}else if(!svo.getState().equals("00A")){
				resultCode = -1;
				errorInfo = "卡资源不可用";
			/*}else if(Integer.parseInt(vo.getDownLimit())>Integer.parseInt(String.valueOf(cardAccount))){
				resultCode = -1;
				errorInfo = "卡数量已经低于该仓库得下限";
			}else if(Integer.parseInt(vo.getUpLimit())<Integer.parseInt(String.valueOf(cardAccount))){
				resultCode = -1;
				errorInfo = "卡数量已经高于该仓库得上限";
			}else{
				vo.setStockAmount(String.valueOf(cardAccount));*/
			}else{
				SalesRescIdRelaVO salesRelaVO = SrDAOFactory.getInstance().getSalesRescIdRelaDAO().findByPrimaryKey("xXx", String.valueOf(cardSerialCode), String.valueOf(crmSerialCode));
				if(salesRelaVO.getSalesRescId().equals("")&& salesRelaVO.getNcSalesRescId().equals("")&& salesRelaVO.getDcDeviceScode().equals("")){
					resultCode = -1;
					errorInfo = "卡资源和营销资源无对应关系";
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
						errorInfo = "卡数量不在仓库上下限数量之间";
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
						errorInfo = "更新库存数量失败";
						ex.printStackTrace();
						String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
						ex.printStackTrace();
						throw new DAOSystemException(errmsg);
						
					}
				}
				
			}
			
		}else{
			resultCode = -1;
			errorInfo = "卡资源和仓库无对应关系或者无该卡资源或者无该仓库实体";
			
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
 * 入参：
 * Name	Description
	serialNo	操作流水号
	timeFlag	时间
	terminalModel	终端型号
	terminalType	终端类型
	cost	终端成本
	batchNum	批次
	manufacturer	厂商

	出参：
	Name	Description
	ResultCode	返回结果
	ErrorInfo	错误信息
	crmSerialCode	Crm规格序号

 */
public HashMap gxM8ToRc(HashMap map)throws Exception{
	HashMap rtnmap = new HashMap();
	int resultCode = 0;
	String errorInfo = "";
	String crmSerialCode ="";
	if(map == null){
		resultCode = -1;
		errorInfo = "无法得到参数";
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
		errorInfo = "无法得到参数";
		rtnmap.put("resultCode", new Integer(resultCode));
		rtnmap.put("errorInfo", errorInfo);
		rtnmap.put("crmSerialCode", "");
		return rtnmap	;
	}
	
	String[] sqlList = {"DC_SQL"};
	List familyList = SrDAOFactory.getInstance().getSqlComDAO().qryComSql("select DC_SQL from dc_sql where DC_NAME='Rc_Terminal_FamilyId'",sqlList);
	if(familyList == null || familyList.size() == 0 ){
		resultCode = -1;
		errorInfo = "无法取得终端资源得家族信息";
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
			errorInfo = "生成终端资源失败";
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
					errorInfo = "生成终端资源属性失败";
					String errmsg = resultCode+"---"+errorInfo+"---"+crmSerialCode;
					ex.printStackTrace();
					throw new DAOSystemException(errmsg);
				}
			}
		}else{
			resultCode = -1;
			errorInfo = "终端资源属性定义不正确";
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
