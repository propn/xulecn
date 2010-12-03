package com.ztesoft.oaas.common.util;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.oaas.vo.MpDepartTermVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.oaas.vo.StaffPrivVO;
import com.ztesoft.oaas.vo.StaffVO;

public class GlobalVariableHelper {
	
	public static DynamicDict getDTO(int actionType , String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
		dto.flag = actionType;// 1:Action;0:Service
		
		dto.m_ActionId = actionName ;//"UACONFIGBEAN";
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto ;
	}
	
	private static Logger logger = Logger.getLogger(GlobalVariableHelper.class);

	public static final String TERMINAL_IP = "vg_terminal_ip";

	public static final String ACCPET_SOURCE = "vg_accept_source";

	public static final String OPER_ID = "vg_oper_id";//PARTY_ROLE_ID

	public static final String OPER_CODE = "vg_oper_code";//STAFF_CODE

	public static final String OPER_NAME = "vg_oper_name";//PARTY_ROLE_NAME
	
	public static final String OPER_ORG_ID = "vg_oper_orgId";//PARTY_ID所在部门ID

	public static final String OPER_ORG_NAME = "vg_oper_orgName";//所在部门名称
	
	public static final String OPER_ORG_CODE = "vg_oper_orgCode";//所在部门编码
	
	public static final String OPER_ORG_LEVEL = "vg_oper_orgLevel";//所在部门组织级别
	  
	public static final String COMPANY_ID = "vg_company_id" ;//分公司标识
	
	public static final String COMPANY_NAME = "vg_company_name" ;//分公司名称
	
	public static final String GROUP_ID = "vg_group_id" ;//班组ID,如果员工所在组织为部门,则班组ID为-1
	
	public static final String LAN_ID = "vg_lan_id";

	public static final String LAN_CODE = "vg_lan_code";

	public static final String LAN_NAME = "vg_lan_name";

	public static final String BUSINESS_ID = "vg_business_id";

	public static final String BUSINESS_CODE = "vg_business_code";

	public static final String BUSINESS_NAME = "vg_business_name";

	public static final String DEPART_ID = "vg_depart_id";

	public static final String DEPART_TYPE = "vg_depart_type";

	public static final String TERM_CODE = "vg_term_code";
	
	public static final String CHANNEL_SEGMENT_ID = "vg_channel_segment_id";

	public static final String BUSINESS_PATH_CODE = "vg_business_path_code";
	
	public static final String ORG_POST_ID = "vg_org_post_id" ;//员工岗位ID
	
	public static final String DEFAULT_DEAL_EXCH_ID = "vg_default_deal_exch_id";
	
	public static final String DEFAULT_DEAL_EXCH_NAME = "vg_default_deal_exch_name";
	
	public static final String DEV_USER_BELONG = "vg_dev_user_belong";
	
	public static final String DEV_USER_BELONG_NAME = "vg_dev_user_belong_name";
	
	public static final String AGENT_FEE_PARTY_ID = "agent_fee_party_id";
	
	public static final String OPER_LAN = "vg_oper_lan";
	public static final String PROV_ID = "prov_id";
	
	private HttpServletRequest request;

	public GlobalVariableHelper(HttpServletRequest req) {
		this.request = req;
	}

	private HttpServletRequest getRequest() {
		return this.request;
	}
	
	private void initDefaultDealExch() throws Exception{
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getInitalDefaultDealExch") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		String privId = "" ;
		if(obj != null && !((List)obj).isEmpty()){
			List privList = ((List)obj) ;
			PrivVO privVO = (PrivVO)privList.get(0);
			privId = privVO.getPrivId();
		}else{
			setVariableToSession("vg_default_deal_exch_id","");
			setVariableToSession("vg_default_deal_exch_name","");
		}
		
		String partyRoleId = this.getVariable(this.OPER_ID);
		
		
		
		dto = getDTO( 0,"LOGINBO" ,"getStaffPriv") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		obj = dto.getValueByName("result") ;
		List list = (List) obj ;
		
		if( obj == null && ((List)obj).isEmpty()){
			setVariableToSession("vg_default_deal_exch_id","");
			setVariableToSession("vg_default_deal_exch_name","");
		}
		for( int i = 0 ; i < list.size() ; i ++ ){
			StaffPrivVO vo = (StaffPrivVO)list.get(i);
			if( privId.equals(vo.getPrivId())){
				setVariableToSession("vg_default_deal_exch_id", vo.getRegionId());
				
				dto = getDTO( 0,"LOGINBO" ,"getRegion") ;
				dto.setValueByName("parameter", vo.getRegionId()) ;
				dto = ActionDispatch.dispatch(dto);
				obj = dto.getValueByName("result") ;
				
				if(obj != null ){
					RegionVO regionVO = (RegionVO)obj ;
					setVariableToSession("vg_default_deal_exch_name", regionVO.getRegionName());
				}
					
			}
		}
	}
	 // 部门班次
	private void initDepartTerm() throws Exception{
		//通过部门ID获取部门班次
		String partyId = this.getVariable("vg_depart_id");
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getInitDepartTerm") ;
		dto.setValueByName("parameter", partyId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		if(obj == null || ((List)obj).isEmpty()){
			setVariableToSession("vg_term_code", "");
			return ;
		}
		MpDepartTermVO voMpDepartTerm = (MpDepartTermVO)((List) obj).get(0);
		setVariableToSession("vg_term_code", voMpDepartTerm.getTermCode());
	}
	
	//部门表,初始化营业区信息,本地网信息,所属部门和部门类型
	private void initDepartmentInfo() throws Exception {
		String orgId = this.getVariable("vg_oper_orgId");
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getMpDepart") ;
		dto.setValueByName("parameter", orgId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		if(obj != null){
			MpDepartVO mpDepartVO = (MpDepartVO)obj ;
			this.setVariableToSession("vg_depart_type",mpDepartVO.getDepartType());//部门类型
			this.setVariableToSession("vg_business_id",mpDepartVO.getRegionId()) ;//营业区编号
			this.setVariableToSession("vg_depart_id",orgId);//所属部门ID
		
		}
	}
	
	//初始化员工所在本地网
	private void initRrLan() throws Exception {
		String partyRoleId = this.getVariable(this.OPER_ID);

		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getRrLanByCond") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if (obj != null) {
			RrLanVO voRrLan =(RrLanVO)obj ; 
			setVariableToSession("vg_lan_id",voRrLan.getLanId());//本地网编号
			setVariableToSession("vg_lan_code", voRrLan.getLanCode());// 所属的本地网编码
			setVariableToSession("vg_lan_name", voRrLan.getLanName());// 所属的本地网名称
		} else {
			setVariableToSession("vg_lan_code", "");// 所属的本地网编码
			setVariableToSession("vg_lan_name", "");// 所属的本地网名称
		}
	}
	//初始化员工所在营业区
	private void initBusiness() throws Exception{
		String businessId = this.getVariable("vg_business_id");
		
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getInitBusiness") ;
		dto.setValueByName("parameter", businessId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if (obj != null) {
			RrBusinessVO voRrBusiness=(RrBusinessVO) obj ;
			setVariableToSession("vg_business_code", voRrBusiness.getBusinessCode());// 所属的营业区编码
			setVariableToSession("vg_business_name", voRrBusiness.getBusinessName());// 所属的营业区名称
		} else {
			logger.debug("没有对应的Business信息.");
			setVariableToSession("vg_business_code", "");// 所属的营业区编码
			setVariableToSession("vg_business_name", "");// 所属的营业区名称
		}
	}
	
	//员工表,初始化 渠道分类标识
	private void initStaff() throws Exception {
		String partyRoleId = this.getVariable("vg_oper_id");
		

		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getStaff") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		
		if( obj != null){
			this.setVariableToSession("vg_channel_segment_id",((StaffVO)obj).getChannelSegmentId());
		}else{
			this.setVariableToSession("vg_channel_segment_id","");	
		}
	}
	
	//获取员工所在部门ID
	private void initOrganizationId() throws Exception{
		String partyRoleId = this.getVariable(this.OPER_ID);
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getInitalOrganization") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		if(obj == null){
			this.setVariableToSession("vg_oper_orgId","");
			return ;
		}
		OrganizationVO orgVO = (OrganizationVO) obj ;
		if( "6".equals(orgVO.getOrgTypeId())){//如果所属组织为班组
			orgVO = getOrganization(orgVO.getParentPartyId());//获取班组的上级组织(即部门)
		}
		this.setVariableToSession("vg_oper_orgId",orgVO.getPartyId());
		this.setVariableToSession("vg_oper_orgLevel",orgVO.getOrgTypeId());
	}
//	获取员工所在部门名称
	private void initOrganizationName() throws Exception {
		String orgId = this.getVariable("vg_oper_orgId");
		OrganizationVO orgVO = getOrganization(orgId);
		if( orgVO != null ){
			this.setVariableToSession("vg_oper_orgName",orgVO.getOrgName());
			this.setVariableToSession("vg_oper_orgCode",orgVO.getOrgCode());
		}else{
			this.setVariableToSession("vg_oper_orgName","");
			this.setVariableToSession("vg_oper_orgCode","");
		}
	}
	
	//初始化Region表,获取员工所在营业厅对应的区域的PATH_CODE
	private void initRegion() throws Exception {
		String businessId = this.getVariable("vg_business_id");
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getRegion") ;
		dto.setValueByName("parameter", businessId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if( obj != null ){
			this.setVariableToSession("vg_business_path_code",((RegionVO)obj).getPathCode());
		}else{
			this.setVariableToSession("vg_business_path_code","");
		}
	}
	
	//初始化分公司ID
	private void initCompanyId() throws Exception{
		String orgId = getVariable("vg_oper_orgId");//所属组织ID
		String companyId = getCompanyIdByOrgId( orgId ) ;
		this.setVariableToSession("vg_company_id", companyId);

		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getOrganization") ;
		dto.setValueByName("parameter", companyId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if(obj != null){
			this.setVariableToSession("vg_company_name",((OrganizationVO)obj).getOrgName());
		}
		
	}
	
	/**
	 * 通过员工所属组织的ID获取员工所在的分公司ID
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private String getCompanyIdByOrgId(String orgId) throws Exception{
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getOrganization") ;
		dto.setValueByName("parameter", orgId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if(obj == null){
			return "";
		}
		OrganizationVO orgVO =(OrganizationVO) obj ;
		if( "3".equals(orgVO.getOrgTypeId())){//分公司的ORG_TYPE_ID 是3
				return orgVO.getPartyId();
		}else{
			if( "-1".equals(orgVO.getParentPartyId())){
				return "";
			}else{
				return getCompanyIdByOrgId( orgVO.getParentPartyId());
			}
		}
		
	}
	/**
	 * 通过员工所属组织的ID获取员工代收金额限制组织ID
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private String getAgentFeePartyId( String orgId ) throws Exception {
		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getOrganization") ;
		dto.setValueByName("parameter", orgId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		if( obj == null ){
			return "";
		}else{
			OrganizationVO orgVO =(OrganizationVO) obj ;
			if( "92B".equals(orgVO.getOrgType())){//对应的组织org_type，如果为“92B:社会代办点”，则“代收金额限制组织ID”为组织ID
				return orgVO.getPartyId();
			}else{
				if( "-1".equals(orgVO.getParentPartyId())){
					return "";
				}else{
					return getAgentFeePartyId( orgVO.getParentPartyId());
				}
			}
		}
	}
	private void initAgentFeePartyId() throws Exception {
		String orgId = getVariable("vg_oper_orgId");//所属组织ID
		String agentFeePartyId = getAgentFeePartyId(orgId);
		this.setVariableToSession("agent_fee_party_id", agentFeePartyId);
	}

	private void initDevUserBelong() throws Exception{
		String partyRoleId = this.getVariable(this.OPER_ID);


		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getStaff") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;
		
		
		if( obj == null ){
			this.setVariableToSession("vg_dev_user_belong","");
			this.setVariableToSession("vg_dev_user_belong_name","");
			return ;
		}
		StaffVO staffVo = (StaffVO)obj ;
		if( staffVo.getDevUserBelong() == null || "".equals(staffVo.getDevUserBelong())){
			this.setVariableToSession("vg_dev_user_belong","");
			this.setVariableToSession("vg_dev_user_belong_name","");
			return ;
		}
		
		OrganizationVO orgVo = getOrganization(staffVo.getDevUserBelong());
		this.setVariableToSession("vg_dev_user_belong",staffVo.getDevUserBelong());
		this.setVariableToSession("vg_dev_user_belong_name",orgVo.getOrgName());
	}
	
	 public OrganizationVO getOrganization(String party_id) throws Exception
	    {
		 DynamicDict dto = getDTO( 0,"LOGINBO" ,"getOrganization") ;
			dto.setValueByName("parameter", party_id) ;
			dto = ActionDispatch.dispatch(dto);
			Object obj = dto.getValueByName("result") ;
			if(obj == null)
				return null ;
			return (OrganizationVO)obj ;
	    }
	//初始化班组ID
	private void initGroupId() throws Exception{
		
		String partyRoleId = this.getVariable(this.OPER_ID);

		
		DynamicDict dto = getDTO( 0,"LOGINBO" ,"getPartyRole") ;
		dto.setValueByName("parameter",partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		Object obj = dto.getValueByName("result") ;

		PartyRoleVO partyRoleVO = (PartyRoleVO) obj ;
		
		String operOrgId = partyRoleVO.getOrgPartyId();
		
		OrganizationVO orgVO = getOrganization(operOrgId);
		if( "6".equals(orgVO.getOrgTypeId())){
			this.setVariableToSession("vg_group_id",orgVO.getPartyId());
		}else{
			this.setVariableToSession("vg_group_id","-1");
		}
	}
	
//	拼写错误的方法,以后要消除这个方法
	public String getVarialbe( String variableName) throws Exception {
		return this.getVariable(variableName);
	}
	
	//拼写正确的方法,以后以这个方法为准
	public String getVariable(String variableName) throws Exception {
		String value = null ;
		
		if( "prov_id".equalsIgnoreCase(variableName)){
			return "20";//省份ID，广西为20，重庆为31，天津为3
		}
		
		if( "vg_org_post_id".equals(variableName)){//工作流需要的岗位ID,暂时实现为传递一个0,以后具体再修改.
			return "0";
		}
		if ("vg_terminal_ip".equals(variableName)) {
			return getVariableFromSession("vg_terminal_ip"); //IP地址无需从数据库中获取,在登陆的时候就已经存放在Session中了
		}
		if ("vg_accept_source".equals(variableName)) {
			return getVariableFromSession("vg_accept_source");//vg_accept_source地址无需从数据库中获取,在登陆的时候就已经存放在Session中了
		}
		if ("vg_oper_id".equals(variableName)) {
			return getVariableFromSession("vg_oper_id");//vg_oper_id无需从数据库中获取,用户正常登陆以后就从鉴权服务器返回并存放在Session中了
		}
		if ("vg_oper_code".equals(variableName)) {
			return getVariableFromSession("vg_oper_code");//vg_oper_code无需从数据库中获取,用户正常登陆以后就从鉴权服务器返回并存放在Session中了
		}
		
		if ("vg_lan_id".equals(variableName)) {
			value = getVariableFromSession("vg_lan_id");
			//如果Session中还没有本地网ID,则需要通过用户登陆的IP地址从 "部门终端范围表" 中获取
			if( value == null ) {
				initDepartmentInfo() ;//初始化终端范围
				value = getVariableFromSession("vg_lan_id");
			}
			return value ; 
		}
		if( "vg_default_deal_exch_id".equals(variableName)){
			value = getVariableFromSession("vg_default_deal_exch_id");
			if( value == null ){
				initDefaultDealExch();
				value = getVariableFromSession("vg_default_deal_exch_id");
			}
			return value ;
		}
		if( "vg_default_deal_exch_name".equals(variableName)){
			value = getVariableFromSession("vg_default_deal_exch_name");
			if( value == null ){
				initDefaultDealExch();
				value = getVariableFromSession("vg_default_deal_exch_name");
			}
			return value ;
		}
		
		if ("vg_business_id".equals(variableName)) {
			value = getVariableFromSession("vg_business_id");
			if( value == null ){
				initDepartmentInfo() ;
				value = getVariableFromSession("vg_business_id");
			}
			return value ;
		}
		
		if ("vg_depart_id".equals(variableName)) {
			value = getVariableFromSession("vg_depart_id");
			if( value == null ){
				initDepartmentInfo() ;
				value = getVariableFromSession("vg_depart_id");
			}
			return value ;
		}
		
		if ("vg_lan_code".equals(variableName)) {
			value = getVariableFromSession("vg_lan_code");
			if( value == null ){
				initRrLan();
				value = getVariableFromSession("vg_lan_code");
			}
			return value ;
		}
		
		if ("vg_lan_name".equals(variableName)) {
			value = getVariableFromSession("vg_lan_name");
			if( value == null ){
				initRrLan();
				value = getVariableFromSession("vg_lan_name");
			}
		}
		
		if ("vg_business_code".equals(variableName)) {
			value = getVariableFromSession("vg_business_code");
			if( value == null ){
				initBusiness() ;
				value = getVariableFromSession("vg_business_code");
			}
			return value ;
		}
		
		if ("vg_business_name".endsWith(variableName)) {
			value = getVariableFromSession("vg_business_name");
			if( value == null ){
				initBusiness() ;
				value = getVariableFromSession("vg_business_name");
			}
			return value ;
		}
		
		if ("vg_term_code".equals(variableName)) {
			value = getVariableFromSession("vg_term_code");
			if( value == null ){
				initDepartTerm();//初始化部门班次
				value = getVariableFromSession("vg_term_code");
			}
			return value ;
		}
		
		if ("vg_depart_type".equals(variableName)) {//部门类型================
			value = getVariableFromSession("vg_depart_type");
			if( value == null ){
				initDepartmentInfo() ;
				value = getVariableFromSession("vg_depart_type");
			}
			return value ;
		}
		
		if ("vg_oper_name".equals(variableName)) {//员工姓名,在登陆的时候从鉴权服务器获取
			return getVariableFromSession("vg_oper_name");
		}
		
		if("vg_channel_segment_id".equals(variableName)) {//STAFF 表的 渠道分类标识
			value = getVariableFromSession("vg_channel_segment_id");
			if( value == null ){
				initStaff() ;
				value = getVariableFromSession("vg_channel_segment_id");
			}
			return value ;
		}
		
		if("vg_business_path_code".equals(variableName)) {
			value = getVariableFromSession("vg_business_path_code");
			if( value == null ){
				initRegion() ;
				value = getVariableFromSession("vg_business_path_code");
			}
			return value ;
		}
		
		if("vg_oper_orgId".equals(variableName)) {
			value = getVariableFromSession("vg_oper_orgId");
			
			if( value == null ){
				initOrganizationId() ;
				value = getVariableFromSession("vg_oper_orgId");
			}
			return value ;
		}
		
		if("vg_oper_orgLevel".equals(variableName)) {
			value = getVariableFromSession("vg_oper_orgLevel");
			
			if( value == null ){
				initOrganizationId() ;
				value = getVariableFromSession("vg_oper_orgLevel");
			}
			return value ;
		}
		
		if("vg_oper_orgName".equals(variableName)) {
			value = getVariableFromSession("vg_oper_orgName");
			if( value == null){
				initOrganizationName() ;
				value = getVariableFromSession("vg_oper_orgName");
			}
			return value ;
		}
		
		if("vg_oper_orgCode".equals(variableName)) {
			value = getVariableFromSession("vg_oper_orgCode");
			if( value == null){
				initOrganizationName() ;
				value = getVariableFromSession("vg_oper_orgCode");
			}
			return value ;
		}
		
		if("vg_company_id".equals(variableName)){
			value = getVariableFromSession("vg_company_id");
			if( value == null ){
				initCompanyId() ;
				value = getVariableFromSession("vg_company_id");
			}
			return value ;
		}
		if("vg_company_name".equals(variableName)){
			value = getVariableFromSession("vg_company_name");
			if( value == null ){
				initCompanyId() ;
				value = getVariableFromSession("vg_company_name");
			}
			return value ;
		}
		
		
		if( "vg_group_id".equals(variableName)){
			value = getVariableFromSession("vg_group_id");
			if( value == null ){
				initGroupId() ;
				value = getVariableFromSession("vg_group_id");
			}
			return value ;
		}
		if( "vg_dev_user_belong".equals(variableName)){
			value = getVariableFromSession( "vg_dev_user_belong");
			if( value == null ){
				initDevUserBelong();
				value = getVariableFromSession("vg_dev_user_belong");
			}
			return value ;
		}
		if( "vg_dev_user_belong_name".equals(variableName)){
			value = getVariableFromSession( "vg_dev_user_belong_name");
			if( value == null ){
				initDevUserBelong();
				value = getVariableFromSession("vg_dev_user_belong_name");
			}
			return value ;
		}
		if( "agent_fee_party_id".equals(variableName)){//代收金额限制组织ID(agent_fee_party_id)全局变量
			value = getVariableFromSession("agent_fee_party_id");
			if( value == null ){
				initAgentFeePartyId();
				value = getVariableFromSession("agent_fee_party_id");
			}
		}
		return null;
	}

	private String getVariableFromSession(String variableName) throws Exception {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		HashMap map = (HashMap) session.getAttribute("LoginRtnParamsHashMap");
		if (map == null) {
			throw new Exception();
		} else {
			String value = (String) map.get(variableName);
			return value ;
		}
	}

	private void setVariableToSession(String variableName, String variableValue)
			throws Exception {
		HashMap map = (HashMap) getRequest().getSession().getAttribute(
				"LoginRtnParamsHashMap");
		if (map == null) {
			throw new Exception();
		}
		map.put(variableName, variableValue);
	}
}
