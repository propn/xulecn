package com.ztesoft.vsop.ordermonitor.bo;



import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.job.SynHBJob;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.vo.MsgSynHB;
import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgDAO;
import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgSynhbDAO;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.SubsInstSynSVStub;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;

public class SpOutMsgSynhbBO extends DictAction  {
	private static Logger log4j = Logger.getLogger(SynHBJob.class);

	private String HBServiceUrl = "";
	public boolean insertSpOutMsgSynhb(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgSynhb = (Map) param.get("SpOutMsgSynhb") ;
		
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		boolean result = dao.insert(SpOutMsgSynhb) ;
		return result ;
	}

	
	public boolean updateSpOutMsgSynhb(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgSynhb = (Map) param.get("SpOutMsgSynhb") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SpOutMsgSynhb,  keyStr) ;
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		boolean result = dao.updateByKey( SpOutMsgSynhb, keyCondMap );
		
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
	public PageModel searchSpOutMsgSynhbData(DynamicDict dto ) throws Exception {
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
		
		
		//调用DAO代码
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	/**
	 * 从新发送当前订单到计费
	 */
	public boolean resetOrder(DynamicDict dto ) throws Exception {
		Map resultMap =   getSpOutMsgSynhbById(dto);
		MsgSynHB synHb = getSynHbByMap(resultMap);
		try {
			OrderDao dao = new OrderDao();
			String resp = callSynHbService(synHb);
			synHb.setFeebackMsg(resp);
			String resultCode = StringUtil.getInstance().getTagValue("ResultCode", resp);
			synHb.setFeebackResult(resultCode);
			String resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", resp);
			synHb.setFeebackDesc(resultDesc);
			//更新状态和归档
			dao.updateSynHbMsgArchive(synHb);
		} catch (RemoteException e) {
			log4j.error("", e);
			LegacyDAOUtil.rollbackOnException();
			throw new RemoteException();
		} catch (Exception e) {
			log4j.error("", e);
			LegacyDAOUtil.rollbackOnException();
			throw new Exception();
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return true;
	}
	private String callSynHbService(MsgSynHB synHb) throws RemoteException {
		if(HBServiceUrl == null || "".equals(HBServiceUrl)){
			HBServiceUrl = DcSystemParamManager.getParameter(VsopConstants.SYNHB_SERVICE_URL);
		}
		SubsInstSynSVStub endPoint = new SubsInstSynSVStub(HBServiceUrl);
		SubsInstSynToVSOPReq subsInstSynToVSOPReq = new SubsInstSynToVSOPReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String req = synHb.getMsg();
		log4j.info("SubsInstSynToVSOPReq xml:"+req);
		param.setRequest(req);
		subsInstSynToVSOPReq.setSubsInstSynToVSOPReq(param);
		SubsInstSynToVSOPResp respObj = endPoint.subsInstSynToGroup(subsInstSynToVSOPReq );
		String respXml = respObj.getSubsInstSynToVSOPResp().getResponse();
		log4j.info("SubsInstSynToVSOPResp xml:" + respXml);
		return respXml;
	}
	private MsgSynHB getSynHbByMap(Map param){

		MsgSynHB synHb = new MsgSynHB();
		synHb.setId(Const.getStrValue(param ,"id"));
		synHb.setOrderId(Const.getStrValue(param ,"order_id"));
		synHb.setOutOrderId(Const.getStrValue(param ,"oiut_order_id"));
		synHb.setOutStreamNo(Const.getStrValue(param ,"out_stream_no"));
		synHb.setOrderResult(Const.getStrValue(param ,"order_result"));
		synHb.setMsg(Const.getStrValue(param ,"msg"));
		synHb.setFeebackResult(Const.getStrValue(param ,"feeback_result"));
		synHb.setFeebackMsg(Const.getStrValue(param ,"feeback_msg"));
		synHb.setFeebackDesc(Const.getStrValue(param ,"feeback_desc"));
		synHb.setIntSysId(Const.getStrValue(param ,"int_sys_id"));
		synHb.setThreadId(Const.getStrValue(param ,"thread_id"));
		return synHb;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOutMsgSynhbById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOutMsgSynhbByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutMsgSynhbById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgSynhbDAO dao = new SpOutMsgSynhbDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
