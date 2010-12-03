package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.DictService;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.TpmConstraintDAO ;

public class TpmConstraintBO extends DictAction  {
	DictService dictService = new DictService();
	private String party_id ="";
	private String party_role_id ="";
	private String oper_region_id ="";
	
	private void getGlobalData() throws Exception{
		this.party_id = dictService.getGlobalVar("vg_oper_code");
		this.party_role_id = dictService.getGlobalVar("vg_oper_id");		
		this.oper_region_id = dictService.getGlobalVar("vg_business_id");
	}
	
	public boolean insertTpmConstraint(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TpmConstraint = (Map) param.get("TpmConstraint") ;
		
		TpmConstraintDAO dao = new TpmConstraintDAO();
		boolean result = dao.insert(TpmConstraint) ;
		return result ;
	}

	
	public boolean updateTpmConstraint(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TpmConstraint = (Map) param.get("TpmConstraint") ;
		String keyStr = "constraint_id";
		Map keyCondMap  = Const.getMapForTargetStr(TpmConstraint,  keyStr) ;
		TpmConstraintDAO dao = new TpmConstraintDAO();
		boolean result = dao.updateByKey( TpmConstraint, keyCondMap );
		
		return result ;
	}
	
	
	public boolean saveTpmConstraint(DynamicDict dto  ) throws Exception {		
		Map param = Const.getParam(dto) ;
		HashMap TpmConstraintMap= (HashMap)param;		
		
		ArrayList TpmConstraint = (ArrayList) TpmConstraintMap.get("TpmConstraint") ;
		System.out.println("******************TpmConstraint*******************"+TpmConstraint.toString());
		
		DynamicDict TpmConstraintDict = new DynamicDict();
		this.getGlobalData();	
		for(int i = 0; i<TpmConstraint.size(); i++){
			HashMap hm_temp = (HashMap)TpmConstraint.get(i);
			String obj_type = (String)hm_temp.get("OBJ_TYPE");
			
			if(obj_type.equals("constraint")){
				TpmConstraintDict.setValueByName("CONSTRAINT", hm_temp, 1);
			}else if(obj_type.equals("Path")){
				if(hm_temp.get("STATE")!=null){
					TpmConstraintDict.setValueByName("PATH", hm_temp, 1);
				}
			}else if(obj_type.equals("Para")){
				if(hm_temp.get("STATE")!=null){
					TpmConstraintDict.setValueByName("CONSTRAINT_VALUES", hm_temp, 1);
				}
			}else if(obj_type.equals("Serv")){
				if(hm_temp.get("STATE")!=null){
					TpmConstraintDict.setValueByName("CONSTRAINT_SERVICE_VALUES", hm_temp, 1);
				}
			}
		}
		TpmConstraintDict.setValueByName("PARTY_ID", this.party_id);
		TpmConstraintDict.setValueByName("PARTY_ROLE_ID", this.party_role_id);
		TpmConstraintDict.setValueByName("OPER_REGION_ID", this.oper_region_id);
		
		this.MPM2_020(TpmConstraintDict);
		
		return true ;
	}
	
	
	public	long	MPM2_020(DynamicDict aDict) throws FrameException{
		String CONSTRAINT_ID="";
		String oper_type="";
		String PARTY_ID = "";
		String PARTY_ROLE_ID = "";
		String OPER_REGION_ID = "";
		String PATH_ID="";
		String PARA_VALUES_ID="";
		String PARA_SERVICE_VALUES_ID="";
		HashMap H_CONSTRAINT=new HashMap();
		HashMap H_PATH= new HashMap();  
		HashMap H_CONSTRAINT_VALUES= new HashMap();
		HashMap H_CONSTRAINT_SERVICE_VALUES= new HashMap();
					
			PARTY_ID=(String)aDict.getValueByName("PARTY_ID");
			PARTY_ROLE_ID=(String)aDict.getValueByName("PARTY_ROLE_ID");
			OPER_REGION_ID=(String)aDict.getValueByName("OPER_REGION_ID");
			
			H_CONSTRAINT=(HashMap)aDict.getValueByName("CONSTRAINT");
			
			oper_type=(String)H_CONSTRAINT.get("OPER_TYPE");
			
			if(oper_type.equals("01"))//新增
			{
				CONSTRAINT_ID=Base.query_string(JNDINames.PM_DATASOURCE, " select S_CONSTRAINT_ID.nextval from dual ", -210020401);
				if(aDict.getCountByName("PATH")>0)
				{
					PATH_ID=Base.query_string(JNDINames.PM_DATASOURCE, " select S_PATH_ID.nextval from dual ", -210020402);
					aDict.setValueByName("PATH_ID",PATH_ID);
					H_CONSTRAINT.put("PATH_ID",PATH_ID);
				}
				if(aDict.getCountByName("CONSTRAINT_VALUES")>0)
				{
					PARA_VALUES_ID=Base.query_string(JNDINames.PM_DATASOURCE," select S_CONSTRAINT_VALUES_ID.nextval from dual ", -210020403);
					aDict.setValueByName("PARA_VALUES_ID",PARA_VALUES_ID);
					H_CONSTRAINT.put("PARA_VALUES_ID",PARA_VALUES_ID);
				}
				if(aDict.getCountByName("CONSTRAINT_SERVICE_VALUES")>0)
				{
					PARA_SERVICE_VALUES_ID=Base.query_string(JNDINames.PM_DATASOURCE," select S_CONSTRAINT_VALUES_ID.nextval from dual ", -210020404);
					aDict.setValueByName("TAR_SERVICE_OFFER_ID",PARA_SERVICE_VALUES_ID);
					H_CONSTRAINT.put("TAR_SERVICE_OFFER_ID",PARA_SERVICE_VALUES_ID);
				}else{
					String sTAR_SERVICE_OFFER_ID = (String)H_CONSTRAINT.get("TAR_SERVICE_OFFER_ID");
					if(sTAR_SERVICE_OFFER_ID==null || sTAR_SERVICE_OFFER_ID.equals(""))
					{
						aDict.setValueByName("TAR_SERVICE_OFFER_ID","0");
						H_CONSTRAINT.put("TAR_SERVICE_OFFER_ID","0");
					}	
				}
				aDict.setValueByName("CONSTRAINT_ID",CONSTRAINT_ID);
				H_CONSTRAINT.put("CONSTRAINT_ID",CONSTRAINT_ID);
				H_CONSTRAINT.put("PARTY_ID",PARTY_ID);
				H_CONSTRAINT.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
				H_CONSTRAINT.put("OPER_REGION_ID",OPER_REGION_ID);
				DynamicDict CONSTRAINTDict = new DynamicDict();
				CONSTRAINTDict.m_Values=H_CONSTRAINT;
				Base.action__execute(JNDINames.PM_DATASOURCE, -210020405,CONSTRAINTDict,"insert into TPM_CONSTRAINT ($columns,OPER_DATE) values ($columns,sysdate)",new String[]{});
												
				for (int i=0;i<aDict.getCountByName("PATH");i++)
				{
					H_PATH= new HashMap();
					H_PATH=(HashMap)aDict.getValueByName("PATH",i);
					H_PATH.put("PATH_ID",PATH_ID);
					H_PATH.put("PARTY_ID",PARTY_ID);
					H_PATH.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
					H_PATH.put("OPER_REGION_ID",OPER_REGION_ID);
					String aa = (String)H_PATH.get("PART_ID");
					
					DynamicDict PATHDict = new DynamicDict();
					PATHDict.m_Values=H_PATH;
					System.out.println(PATHDict.toString());
					Base.action__execute(JNDINames.PM_DATASOURCE,-210020406,PATHDict,"insert into TPM_CONSTRAINT_PATH ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
					
				}
				
				for (int i=0;i<aDict.getCountByName("CONSTRAINT_VALUES");i++)
				{
					H_CONSTRAINT_VALUES= new HashMap();
					H_CONSTRAINT_VALUES=(HashMap)aDict.getValueByName("CONSTRAINT_VALUES",i);
					H_CONSTRAINT_VALUES.put("PARA_VALUES_ID",PARA_VALUES_ID);
					H_CONSTRAINT_VALUES.put("PARTY_ID",PARTY_ID);
					H_CONSTRAINT_VALUES.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
					H_CONSTRAINT_VALUES.put("OPER_REGION_ID",OPER_REGION_ID);
					
					DynamicDict CONSTRAINT_VALUESDict = new DynamicDict();
					CONSTRAINT_VALUESDict.m_Values=H_CONSTRAINT_VALUES;
					Base.action__execute(JNDINames.PM_DATASOURCE,-210020407,CONSTRAINT_VALUESDict,"insert into TPM_CONSTRAINT_VALUES ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
					
				}
				for (int i=0;i<aDict.getCountByName("CONSTRAINT_SERVICE_VALUES");i++)
				{
					H_CONSTRAINT_SERVICE_VALUES= new HashMap();
					H_CONSTRAINT_SERVICE_VALUES=(HashMap)aDict.getValueByName("CONSTRAINT_SERVICE_VALUES",i);
					H_CONSTRAINT_SERVICE_VALUES.put("PARA_VALUES_ID",PARA_SERVICE_VALUES_ID);
					H_CONSTRAINT_SERVICE_VALUES.put("PARTY_ID",PARTY_ID);
					H_CONSTRAINT_SERVICE_VALUES.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
					H_CONSTRAINT_SERVICE_VALUES.put("OPER_REGION_ID",OPER_REGION_ID);
					
					DynamicDict CONSTRAINT_SERVICE_VALUESDict = new DynamicDict();
					CONSTRAINT_SERVICE_VALUESDict.m_Values=H_CONSTRAINT_SERVICE_VALUES;
					Base.action__execute(JNDINames.PM_DATASOURCE,-210020408,CONSTRAINT_SERVICE_VALUESDict,"insert into TPM_CONSTRAINT_VALUES ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
					
				}
				
				
			}
			else if(oper_type.equals("02"))//修改
			{
				
				CONSTRAINT_ID=(String)H_CONSTRAINT.get("CONSTRAINT_ID");
				if(aDict.getCountByName("CONSTRAINT_SERVICE_VALUES")<=0)
				{
					String vTAR_SERVICE_OFFER_ID = Base.query_string(JNDINames.PM_DATASOURCE,"  select TAR_SERVICE_OFFER_ID from TPM_CONSTRAINT where CONSTRAINT_ID=? ",new String []{CONSTRAINT_ID},-210020409);
					if(!vTAR_SERVICE_OFFER_ID.equals("0"))
						Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{vTAR_SERVICE_OFFER_ID},-210020410);
				}
				Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT where CONSTRAINT_ID=? ",new String []{CONSTRAINT_ID},-210020411);
				
				
				H_CONSTRAINT.put("PARTY_ID",PARTY_ID);
				H_CONSTRAINT.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
				H_CONSTRAINT.put("OPER_REGION_ID",OPER_REGION_ID);
				
				DynamicDict CONSTRAINTDict = new DynamicDict();
				CONSTRAINTDict.m_Values=H_CONSTRAINT;
				Base.action__execute(JNDINames.PM_DATASOURCE,-210020412,CONSTRAINTDict,"insert into TPM_CONSTRAINT ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
				
				if(aDict.getCountByName("PATH")>0)
				{
					PATH_ID=(String)H_CONSTRAINT.get("PATH_ID");
					if(PATH_ID.length()>0)
						Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_PATH where PATH_ID=? ",new String []{PATH_ID},-210020413);
					else
						PATH_ID=Base.query_string(JNDINames.PM_DATASOURCE," select S_PATH_ID.nextval from dual ",-210020414);
					for (int i=0;i<aDict.getCountByName("PATH");i++)
					{
						H_PATH= new HashMap();
						H_PATH=(HashMap)aDict.getValueByName("PATH",i);
						H_PATH.put("PARTY_ID",PARTY_ID);
						H_PATH.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
						H_PATH.put("OPER_REGION_ID",OPER_REGION_ID);
						
						String PATH_ID1=(String)H_PATH.get("PATH_ID"); 
						if(PATH_ID1.length()==0)
						{
							H_PATH.put("PATH_ID",PATH_ID);
							
							Base.update(JNDINames.PM_DATASOURCE,"  update TPM_CONSTRAINT set path_id=? where CONSTRAINT_ID=? ",new String []{PATH_ID,CONSTRAINT_ID},-210020415);
						}
						DynamicDict PATHDict = new DynamicDict();
						PATHDict.m_Values=H_PATH;
						Base.action__execute(JNDINames.PM_DATASOURCE,-210020416,PATHDict,"insert into TPM_CONSTRAINT_PATH ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
						
					}
					
				}
				else
				{
					PATH_ID=(String)H_CONSTRAINT.get("PATH_ID");
					
					if(PATH_ID.length()>0)
						Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_PATH where PATH_ID=? ",new String []{PATH_ID},-210020417);
					
					
					
				}
				if(aDict.getCountByName("CONSTRAINT_VALUES")>0)
				{
					PARA_VALUES_ID=(String)H_CONSTRAINT.get("PARA_VALUES_ID");
					if(PARA_VALUES_ID.length()>0)
							Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{PARA_VALUES_ID},-210020418);
					else
						PARA_VALUES_ID=Base.query_string(JNDINames.PM_DATASOURCE," select S_CONSTRAINT_VALUES_ID.nextval from dual ",-210020419);
					
					for (int i=0;i<aDict.getCountByName("CONSTRAINT_VALUES");i++)
					{
						H_CONSTRAINT_VALUES= new HashMap();
						H_CONSTRAINT_VALUES=(HashMap)aDict.getValueByName("CONSTRAINT_VALUES",i);
						H_CONSTRAINT_VALUES.put("PARTY_ID",PARTY_ID);
						H_CONSTRAINT_VALUES.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
						H_CONSTRAINT_VALUES.put("OPER_REGION_ID",OPER_REGION_ID);
						
						String PARA_VALUES_ID1=(String)H_CONSTRAINT.get("PARA_VALUES_ID");
						if(PARA_VALUES_ID1.length()==0)
						{
							H_CONSTRAINT_VALUES.put("PARA_VALUES_ID",PARA_VALUES_ID);
							Base.update(JNDINames.PM_DATASOURCE,"  update TPM_CONSTRAINT set PARA_VALUES_ID=? where CONSTRAINT_ID=? ",new String []{PARA_VALUES_ID,CONSTRAINT_ID},-210020420);
						}
						DynamicDict CONSTRAINT_VALUESDict = new DynamicDict();
						CONSTRAINT_VALUESDict.m_Values=H_CONSTRAINT_VALUES;
						Base.action__execute(JNDINames.PM_DATASOURCE,-210020421,CONSTRAINT_VALUESDict,"insert into TPM_CONSTRAINT_VALUES ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
						
					}
					
				}
				else
				{
					PARA_VALUES_ID=(String)H_CONSTRAINT.get("PARA_VALUES_ID");
					
					if(PARA_VALUES_ID.length()>0)
						Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{PARA_VALUES_ID},-210020422);
					
					
					
				}
				if(aDict.getCountByName("CONSTRAINT_SERVICE_VALUES")>0)
				{
					PARA_SERVICE_VALUES_ID=(String)H_CONSTRAINT.get("TAR_SERVICE_OFFER_ID");
					if(PARA_SERVICE_VALUES_ID.length()>0 && !PARA_SERVICE_VALUES_ID.equals("0"))
						Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{PARA_SERVICE_VALUES_ID},-210020423);
					else
						PARA_SERVICE_VALUES_ID=Base.query_string(JNDINames.PM_DATASOURCE," select S_CONSTRAINT_VALUES_ID.nextval from dual ",-210020424);
					
					for (int i=0;i<aDict.getCountByName("CONSTRAINT_SERVICE_VALUES");i++)
					{
						H_CONSTRAINT_SERVICE_VALUES= new HashMap();
						H_CONSTRAINT_SERVICE_VALUES=(HashMap)aDict.getValueByName("CONSTRAINT_SERVICE_VALUES",i);
						H_CONSTRAINT_SERVICE_VALUES.put("PARA_VALUES_ID",PARA_SERVICE_VALUES_ID);
						H_CONSTRAINT_SERVICE_VALUES.put("PARTY_ID",PARTY_ID);
						H_CONSTRAINT_SERVICE_VALUES.put("PARTY_ROLE_ID",PARTY_ROLE_ID);
						H_CONSTRAINT_SERVICE_VALUES.put("OPER_REGION_ID",OPER_REGION_ID);
						
						
						String TAR_SERVICE_OFFER_ID1=(String)H_CONSTRAINT.get("TAR_SERVICE_OFFER_ID");
						if(TAR_SERVICE_OFFER_ID1.length()==0 || TAR_SERVICE_OFFER_ID1.equals("0"))
						{
							Base.update(JNDINames.PM_DATASOURCE,"  update TPM_CONSTRAINT set TAR_SERVICE_OFFER_ID=? where CONSTRAINT_ID=? ",new String []{PARA_SERVICE_VALUES_ID,CONSTRAINT_ID},-210020425);
						}
						DynamicDict CONSTRAINT_SERVICE_VALUESDict = new DynamicDict();
						CONSTRAINT_SERVICE_VALUESDict.m_Values=H_CONSTRAINT_SERVICE_VALUES;
						Base.action__execute(JNDINames.PM_DATASOURCE,-210020426,CONSTRAINT_SERVICE_VALUESDict,"insert into TPM_CONSTRAINT_VALUES ($columns,OPER_DATE) values ($columns,sysdate)",new String []{});
						
					}
					
				}
				
			}
			else if(oper_type.equals("03"))//删除
			{
				
				CONSTRAINT_ID=(String)H_CONSTRAINT.get("CONSTRAINT_ID");
				String vTAR_SERVICE_OFFER_ID = Base.query_string(JNDINames.PM_DATASOURCE,"  select TAR_SERVICE_OFFER_ID from TPM_CONSTRAINT where CONSTRAINT_ID=? ",new String []{CONSTRAINT_ID},-210020427);
				if(!vTAR_SERVICE_OFFER_ID.equals("0"))
				Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{vTAR_SERVICE_OFFER_ID},-210020428);
				
				Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT where CONSTRAINT_ID=? ",new String []{CONSTRAINT_ID},-210020429);
				
				PATH_ID=(String)H_CONSTRAINT.get("PATH_ID");
				Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_PATH where PATH_ID=? ",new String []{PATH_ID},-210020430);
				PARA_VALUES_ID=(String)H_CONSTRAINT.get("PARA_VALUES_ID");
				Base.update(JNDINames.PM_DATASOURCE,"  delete from TPM_CONSTRAINT_VALUES where PARA_VALUES_ID=? ",new String []{PARA_VALUES_ID},-210020431);
				
			}
		
		
		return	0;
	}
	
	
	public PageModel searchTpmConstraintData(DynamicDict dto ) throws Exception {		
		
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String object_type  = Const.getStrValue(param,"object_type");
		String object_id  = Const.getStrValue(param,"object_id");
		para.add(object_type);
		para.add(object_id);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		TpmConstraintDAO dao = new TpmConstraintDAO();
		String sql ="select * from tpm_constraint where plan_object_type =  ?  and plan_object_id = ? and state <> '00X'";
		
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		
		return result ;
		
		
		
		/*//		条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "object_type")){
			whereCond.append(" and PLAN_OBJECT_TYPE = ? ");
			para.add(Const.getStrValue(param , "object_type")) ;
		}
		if(Const.containStrValue(param , "object_id")){
			whereCond.append(" and PLAN_OBJECT_ID = ? ");
			para.add(Const.getStrValue(param , "object_id")) ;
		}
				
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		TpmConstraintDAO dao = new TpmConstraintDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;*/
		
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getTpmConstraintById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		TpmConstraintDAO dao = new TpmConstraintDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findTpmConstraintByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		TpmConstraintDAO dao = new TpmConstraintDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteTpmConstraintById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TpmConstraintDAO dao = new TpmConstraintDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
