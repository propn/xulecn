package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.TpmNoteBureauDAO;
import com.ztesoft.crm.product.dao.TpmNoteDAO ;

public class TpmNoteBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchTpmNoteData 改方法的参数名称
 		3. findTpmNoteByCond(String note_id,String seq) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();
	public boolean insertTpmNote(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		String nodeId = sequenceManageDAO.getNextSequenceWithDB("S_TPM_NOTE_ID",JNDINames.PM_DATASOURCE);
		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 java.util.Date currentTime = new java.util.Date();//得到当前系统时间
		 String str_date1 = formatter.format(currentTime); //将日期时间格式化
		param.put("note_id",nodeId);
		param.put("state","00A");
		param.put("seq","0");
		param.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
		param.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
		param.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
		param.put("oper_date",str_date1);

		TpmNoteDAO dao = new TpmNoteDAO();
		boolean result = dao.insert(param) ;
		return result ;
	}


	public boolean updateTpmNote(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		String keyStr = "note_id";
		Map keyCondMap  = Const.getMapForTargetStr(param,  keyStr) ;
		TpmNoteDAO dao = new TpmNoteDAO();
		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		param.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
		param.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
		param.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
		boolean result = dao.updateByKey( param, keyCondMap );

		return result ;
	}
	public List getTpmNote(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		String owner_id = Const.getStrValue(param,"owner_id");
		List list = new ArrayList();
		list.add(owner_id);
		TpmNoteDAO dao = new TpmNoteDAO();
		String sql="select a.note_id,a.note_name,a.owner_type,a.owner_id,a.owner_name,a.note,a.order_id from tpm_note a where a.owner_id =? and a.state ='00A'";
		return dao.findBySql(sql,list);
	}
	public List getOwnerId(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		String owner_type = Const.getStrValue(param,"owner_type");
		String owner_name = "%"+Const.getStrValue(param,"owner_name")+"%";
		List list = new ArrayList();
		list.add(owner_name);
		TpmNoteDAO dao = new TpmNoteDAO();
		String sql="";
		if("10A".equals(owner_type)){
			sql="select a.product_id owner_id,a.product_name owner_name from product a where a.product_name like ? and a.state ='00A'";
		}else if("10C".equals(owner_type)){
			sql="select a.offer_id owner_id,a.offer_name owner_name from product_offer a where a.offer_name like ? and a.state ='00A'";
		}else{
			sql="select a.service_offer_id owner_id,a.service_offer_name owner_name from service_offer a where a.service_offer_name like ? and a.state ='00A'";
		}
		return dao.findBySql(sql,list);
	}
	public PageModel searchTpmNoteData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;


		//调用DAO代码
		TpmNoteDAO dao = new TpmNoteDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;


		return result ;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getTpmNoteById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		TpmNoteDAO dao = new TpmNoteDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;

		return result ;
	}


	public List findTpmNoteByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		TpmNoteDAO dao = new TpmNoteDAO();
		List result = dao.findByCond( whereCond, para) ;

		return result ;
	}


	public boolean deleteTpmNoteById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TpmNoteDAO dao = new TpmNoteDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}

	public boolean saveNoteBureau(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto)  ;
		List noteBureau = (ArrayList)param.get("noteBureau");
		TpmNoteBureauDAO dao = new TpmNoteBureauDAO();
		Map keyMap = (HashMap)noteBureau.get(0);
		dao.deleteByKey(keyMap);
		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 java.util.Date currentTime = new java.util.Date();//得到当前系统时间
		 String str_date1 = formatter.format(currentTime); //将日期时间格式化
		for(int i=0;i<noteBureau.size();i++){
			 Map temp = (HashMap)noteBureau.get(i);
			 temp.put("state","00A");
			 temp.put("seq","0");
			 temp.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
			 temp.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
			 temp.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
	   		 temp.put("oper_date",str_date1);

	   		 dao.insert(temp) ;
		}
		return true;
	}
	public boolean deleteTpmNoteBureauById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TpmNoteBureauDAO dao = new TpmNoteBureauDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
	public List getTpmNoteBureau(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto);
		List list = new ArrayList();
		list.add(Const.getStrValue(param,"note_id"));
		TpmNoteBureauDAO dao = new TpmNoteBureauDAO();
		String sql="select a.note_id,a.region_id,b.region_name,a.eff_date,a.exp_date " +
				"from tpm_note_bureau a,region b where a.region_id = b.region_id and a.note_id=?";
		return dao.findBySql(sql,list);
	}
}
