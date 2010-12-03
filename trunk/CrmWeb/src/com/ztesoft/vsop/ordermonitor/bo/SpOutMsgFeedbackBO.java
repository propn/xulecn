package com.ztesoft.vsop.ordermonitor.bo;



import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.job.OrderFeedbackJob;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.vo.MsgSynHB;
import com.ztesoft.vsop.order.vo.SpOutMsgFeedback;
import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgDAO;
import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgFeedbackDAO ;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.WorkSheetReturnSVStub;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;
import com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq;
import com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse;

public class SpOutMsgFeedbackBO extends DictAction  {
	private static Logger log4j = Logger.getLogger(OrderFeedbackJob.class);
	private String WorkSheetReturnServiceUrl = "";
	public boolean insertSpOutMsgFeedback(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgFeedback = (Map) param.get("SpOutMsgFeedback") ;
		
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		boolean result = dao.insert(SpOutMsgFeedback) ;
		return result ;
	}

	
	public boolean updateSpOutMsgFeedback(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgFeedback = (Map) param.get("SpOutMsgFeedback") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SpOutMsgFeedback,  keyStr) ;
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		boolean result = dao.updateByKey( SpOutMsgFeedback, keyCondMap );
		
		return result ;
	}
	public Map getInitPara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		Map result = dao.getInitPara(param.get("refresh_time").toString(),param.get("alarm").toString()) ;
		return result;
	}
	public List searchMsgStateData(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		List result = dao.searchState(Const.getStrValue(keyCondMap , "style"));
		return result;
	}
	
	public PageModel searchSpOutMsgFeedbackData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "prod_no")){
			whereCond.append(" and t.prod_no like ? ");
			para.add("%"+Const.getStrValue(param , "prod_no")+"%") ;
		}
		if(Const.containStrValue(param , "in_start_time")){
			String in_start_time = Const.getStrValue(param , "in_start_time");
			in_start_time += " 00:00:00";
			whereCond.append(" and t.in_time > to_date('"+in_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "in_end_time")){
			String in_end_time = Const.getStrValue(param , "in_end_time");
			in_end_time += " 23:59:59";
			whereCond.append(" and t.in_time < to_date('"+in_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "order")){
			whereCond.append(" and t.order_id = ? ");
			para.add(Const.getStrValue(param , "order")) ;
		}
		if(Const.containStrValue(param , "sys")){
			String sys = Const.getStrValue(param , "sys");
			whereCond.append(" and t.int_sys_id in ( "+sys+")");
		}
		if(Const.containStrValue(param , "deal_start_time")){
			String deal_start_time = Const.getStrValue(param , "deal_start_time");
			deal_start_time += " 00:00:00";
			whereCond.append(" and t.feeback_time > to_date('"+deal_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "deal_end_time")){
			String deal_end_time = Const.getStrValue(param , "deal_end_time");
			deal_end_time += " 23:59:59";
			whereCond.append(" and t.feeback_time < to_date('"+deal_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "state")){
			String state = Const.getStrValue(param , "state");
			whereCond.append(" and t.state in ( "+state+")");
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	/**
	 * ���·��͵�ǰ����������
	 */
	public boolean resetOrder(DynamicDict dto ) throws Exception {
		try {
			OrderDao dao = new OrderDao();
			Map feedbackMap =  getSpOutMsgFeedbackById(dto);
			SpOutMsgFeedback feedback =getFeedbackByMap(feedbackMap);
//			������ͨ����Ӧ��ӿ�
			String resp = sendFeedback(feedback);
			feedback.setFeedbackMsg(resp);
			String resultCode = StringUtil.getInstance().getTagValue("ResultCode", resp);
			feedback.setFeedbackResult(resultCode);
			String resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", resp);
			feedback.setFeedbackDesc(resultDesc);
//			����feedback״̬���鵵
			dao.updateFeedbackAndArchive(feedback);
			if("1".equals(feedback.getWriter())){  //OrderBo.java#fkFeedbackд����Ҫ�鵵
				dao.archiveOrder(feedback.getOrderId());
			}
		} catch (RemoteException e) {
			log4j.error("", e);
			LegacyDAOUtil.rollbackOnException();
			throw new RemoteException();
		} catch (Exception e) {
			log4j.error("", e);
			LegacyDAOUtil.rollbackOnException();
			throw new Exception();
		}
		finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return true;
	}
	private String sendFeedback(SpOutMsgFeedback feedback) throws RemoteException {
		if(WorkSheetReturnServiceUrl == null || "".equals(WorkSheetReturnServiceUrl)){
			WorkSheetReturnServiceUrl = DcSystemParamManager.getParameter(VsopConstants.WORK_SHEET_RETURN_SERVICE_URL);
		}
		log4j.info("WorkSheetReturnServiceUrl:" + WorkSheetReturnServiceUrl);
		WorkSheetReturnSVStub endpoint = new WorkSheetReturnSVStub(WorkSheetReturnServiceUrl);
		WorkListVSOPToFKReq workListVSOPToFKReq12 = new WorkListVSOPToFKReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String reqXml = feedback.getMsg();
		log4j.info("#sendFeedback req:" + reqXml);
		param.setRequest(reqXml );
		workListVSOPToFKReq12.setWorkListVSOPToFKReq(param );
		WorkListVSOPToFKReqResponse respObj = endpoint.workListVSOPToFK(workListVSOPToFKReq12 );
		String ret = respObj.getWorkListVSOPToFKReqResponse().getResponse();
		log4j.info("#sendFeedback resp:"+ret);
		return ret;
	}
	public SpOutMsgFeedback getFeedbackByMap(Map map){
		SpOutMsgFeedback feeback = new SpOutMsgFeedback();
		feeback.setId(Const.getStrValue(map , "id"));
		feeback.setInTime(Const.getStrValue(map , "in_time"));
		feeback.setOrderId(Const.getStrValue(map , "order_id"));
		feeback.setStreamNo(Const.getStrValue(map , "out_stream_no"));
		feeback.setMsg(Const.getStrValue(map , "msg"));
		feeback.setWriter(Const.getStrValue(map , "writer"));
		return feeback;
	}
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getSpOutMsgFeedbackById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOutMsgFeedbackByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutMsgFeedbackById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgFeedbackDAO dao = new SpOutMsgFeedbackDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
