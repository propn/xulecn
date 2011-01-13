/**
 * 
 */
package com.ztesoft.component.common.signon.simulate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAO;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAOFactory;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOFactory;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAO;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAOFactory;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAO;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAO;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAOFactory;
import com.ztesoft.oaas.dao.partner.PartnerDAO;
import com.ztesoft.oaas.dao.partner.PartnerDAOFactory;
import com.ztesoft.oaas.dao.party.PartyDAO;
import com.ztesoft.oaas.dao.party.PartyDAOFactory;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.dao.region.RegionDAO;
import com.ztesoft.oaas.dao.region.RegionDAOFactory;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOFactory;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAOFactory;
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAO;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAOFactory;
import com.ztesoft.oaas.struct.ChangePasswordRequest;
import com.ztesoft.oaas.struct.ChangePasswordRespond;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRequest;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.AreaCodeVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationTypeVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PartyVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.oaas.vo.StaffVO;
/**
 * @author Administrator
 *
 */
public class LoginBO extends DictAction{
	
	private ArrayList getStaffPrivileges( DynamicDict dto ) throws Exception {
		String partyRoleId =  (String)dto.getValueByName("parameter");
		ArrayList list = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {
			SQL = "select a.privilege_id, a.region_id, a.region_type,b.privilege_code, b.privilege_type " +
						" from staff_privilege a, privilege b where a.privilege_id = b.privilege_id " +
						" and a.party_role_id = " + partyRoleId ;
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.createStatement() ;
			//.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rs = stmt.executeQuery( DAOSQLUtils.getFilterSQL(SQL));
			while( rs.next()){
				DataVO vo = new DataVO() ;
				vo.setPrivCode(rs.getString("privilege_code"));
				vo.setPrivId(rs.getString("privilege_id"));
				vo.setPrivType(rs.getString("privilege_type"));
				vo.setRegionId(rs.getString("region_id"));
				vo.setRegionType(rs.getString("region_type"));
				list.add(vo);
			}

			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt);
			
			SQL = " select c.region_id, c.region_type , a.*, b.privilege_code, b.privilege_type as privilege_type2 from role_privilege a, privilege b, staff_role c "+
			" where a.role_id = c.role_id and a.privilege_id = b.privilege_id and c.party_role_id = '" + partyRoleId+"'" ;
//			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			stmt = conn.createStatement() ;
			//.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rs = stmt.executeQuery( DAOSQLUtils.getFilterSQL(SQL));
//			rs = stmt.executeQuery();
			while( rs.next()){
				DataVO vo = new DataVO() ;
				vo.setPrivCode(rs.getString("privilege_code"));
				vo.setPrivId(rs.getString("privilege_id"));
				vo.setPrivType(rs.getString("privilege_type2"));
				vo.setRegionId(rs.getString("region_id"));
				vo.setRegionType(rs.getString("region_type"));
				list.add(vo);
			}
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			se.printStackTrace();
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt);
//			DAOUtils.closeConnection(conn, this);
		}
		return list ;
	}
	
	/**
	 * 取得登陆者的权限及对密码进行校验（不通过鉴权），
	 * 同时实现IP内控，针对密码有效期进行校验
	 * 1.如果登陆状态有效,那么需要进行时间有效的校验,如果超时了,把工号冻结,必须事情激活
	 * 2.如果是被激活状态,那么需要进行修改密码
	 * 3.如果是被冻结状态,那么不能登陆
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public LoginRespond simulateLoginNotNeedProxy(DynamicDict dto ) throws Exception {
		LoginRequest request=  (LoginRequest)dto.getValueByName("parameter");
        LoginRespond resLogin = new LoginRespond();
        
        String lanId = "";
        String partyRoleId = "";
        String partyRoleName = "";
        String orgCode = "";
        String orgName = "";   
        String pwdCrypt="";//密码密文
        String loginStatus="";//登陆状态,0表示有效,2表示被激活，‘1’表示该工号已经冻结，必须要申请激活 9为登陆状态,否则可以注销之
        //String loginCount ="";//登陆次数
        String state ="";//工号状态
        String SQL = "select a.party_role_id, a.party_role_name,b.org_code, b.org_name,a.password,a.login_status,a.login_count,a.state ,a.lan_id "+
                            " from party_role a, organization b, staff c where c.staff_code = '" + 
                            request.getStaffCode() + "' and "+
                            " a.party_role_id = c.party_role_id and b.party_id = a.org_party_id " ;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
        	conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
        	SQL = DAOSQLUtils.getFilterSQL(SQL);

            
            stmt = conn.createStatement() ;
            //.prepareStatement( SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
            rs = stmt.executeQuery(SQL);

//        	System.out.println("T----------------->5" + conn) ;
            if( rs.next()){
                partyRoleId = rs.getString("party_role_id");
                partyRoleName = rs.getString("party_role_name");
                orgCode = rs.getString("org_code");
                orgName = rs.getString("org_name");
                pwdCrypt=rs.getString("password");
                loginStatus = rs.getString("login_status");
                state = rs.getString("state");
                lanId = rs.getString("lan_id");
            }
        }
        catch (SQLException se) {
            Debug.print(SQL,this);
            se.printStackTrace();
        }
        finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt);
//            DAOUtils.closeConnection(conn, this);
        }
        
        
        if( partyRoleId == null || "".equals(partyRoleId)){
            resLogin.setSuccess("0");
            resLogin.setReason("工号不存在!");
            return resLogin ;
        }
        if( state == null || "".equals(state)|| "00X".equals(state)){
            resLogin.setSuccess("0");
            resLogin.setReason("工号无效!");
            return resLogin ;
        }
        
        String reqPwd="";
        reqPwd=request.getPassword();
        reqPwd=reqPwd==null?"":reqPwd;

        DesEncrypt descript= new DesEncrypt();
        //去掉partyroleId和密码组合加密  liuyuming 2010-09-18
//        String validatePwd =descript.encryptTriDes(partyRoleId+reqPwd);
        String validatePwd =descript.encryptTriDes(reqPwd);
        
        if(null==validatePwd||"".equals(validatePwd)){
        	 resLogin.setSuccess("0");
	         resLogin.setReason("密码加密失败!");
	         return resLogin ;
        }
        String loginType=request.getLoginType();
        if(!"LoginWan".equals(loginType)){//true是10000系统登陆
         //取消密码校验
        if(!validatePwd.equals(pwdCrypt)){
        	 System.out.println("密码加密以后的密文"+validatePwd);
        	 System.out.println("数据库的密文"+pwdCrypt);
        	 resLogin.setSuccess("0");
	         resLogin.setReason("密码错误,请重新输入!");
	         return resLogin ;
        }
         
        }

        resLogin.setStrMsgNo("");
        resLogin.setSuccess("1");
        resLogin.setReason("");
        resLogin.setSocketId("");
        resLogin.setPartyRoleId(partyRoleId);
        resLogin.setStaffCode(request.getStaffCode());
        resLogin.setPassword("");
        resLogin.setPartyName(partyRoleName);
        resLogin.setSocialIdType("");
        resLogin.setSocialId("");
        resLogin.setOrgCode(orgCode);
        resLogin.setOrgName(orgName);
        resLogin.setDutyId("");
        resLogin.setAddi1("");
        resLogin.setSrvTime("");
        resLogin.setIsEnd("");
        resLogin.setLanId(lanId);
        
        dto.setValueByName("parameter", partyRoleId);
        ArrayList alPrivs = getStaffPrivileges( dto) ;
        if(alPrivs!=null && alPrivs.size()>0){
            RoleState[] arrRoleState = new RoleState[alPrivs.size()];
            Iterator iterPrivs = alPrivs.iterator();
            for(int i=0; i<arrRoleState.length; i++){
                arrRoleState[i] = new RoleState();
                DataVO voPriv = (DataVO)iterPrivs.next();
                arrRoleState[i].setPrivilegeId(voPriv.getPrivId());
                arrRoleState[i].setRegionId(voPriv.getRegionId());
                arrRoleState[i].setPermitionFlag(voPriv.getRegionType());
                arrRoleState[i].setPartyRoleSchema(voPriv.getPrivType());
                arrRoleState[i].setPrivilegeCode(voPriv.getPrivCode());
            }
            resLogin.setRoleState(arrRoleState);
        }
        //update login_status 
//        PartyRoleBean roleBean = PartyRoleBean.getInstance() ;
        Map m = new HashMap() ;
        m.put("partyRoleId", partyRoleId) ;
        m.put("loginStatus", "9") ;
        dto.setValueByName("parameter", m) ;
        updatePartyRoleLoginStatus(dto); 
        return resLogin;
    }
	
	 public boolean updatePartyRoleLoginStatus(DynamicDict dto) throws Exception 
	    {
//		 Map m = (Map)dto.getValueByName("parameter") ;
//		 		 String partyRoleId = (String)m.get("partyRoleId");
//		 		 String loginStatus  = (String)m.get("loginStatus");
//		 		 
//			String updateSql ="update party_role set login_status ="+loginStatus+" where party_role_id ="+partyRoleId;
//			CommonDAO dao = new CommonDAO();
//			dao.update(updateSql);
			return true ;
	    }

	 public String getMenus(DynamicDict dto) throws Exception {

		 Map m = (Map)dto.getValueByName("parameter") ;
		 String privset = (String)m.get("privset");
		 String parentMenuId  = (String)m.get("parentMenuId");
		 String staffCode  = (String)m.get("staffCode");
		 		 
		 MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
		 ArrayList alMenus = daoMmMenu.getMmMenusByPrivSet(privset,parentMenuId,staffCode);
         return XMLSegBuilder.toXmlItems(alMenus);
	 }

	 public String getStaffPassword(DynamicDict dto ) throws Exception {
		 String staffCode=  (String)dto.getValueByName("parameter");
		 String strResultBuff = "";
	    	if( staffCode == null || "".equals(staffCode)){
	    		return strResultBuff;
	    	}
	    	try{
		    	
		    	StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		        StaffVO staffVO = daoStaff.getStaffByStaffCode( staffCode );
		    	
		        if( staffVO == null ){
		    		return strResultBuff;
		    	}
		    	PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
		    	PartyRoleVO partyRoleVO = partyRoleDao.findByPrimaryKey(staffVO.getPartyRoleId());
		    	if( partyRoleVO == null ){
		    		return strResultBuff;
		    	}
		    	String password = partyRoleVO.getPassword();
		    	EncryptRequest request = new EncryptRequest();
		    	request.setFlag("F");
		    	request.setSecretBuff(password);
		    	EncryptRespond respond = this.encrypt(request);
		    	strResultBuff = respond.getStrResultBuff();
		    	strResultBuff = strResultBuff.replaceAll(partyRoleVO.getPartyRoleId(),"");
	    	}catch( Exception e ){
	    		e.printStackTrace();
	    	} 
	    	return strResultBuff;
	    }
	    /**
	     * 加密
	     * 
	     * @param request
	     *            加密解密请求数据
	     * @return 操作结果, EncryptRespond对象
	     */
	    public EncryptRespond encrypt(EncryptRequest request) throws Exception
	    {
	    	return OAASProxy.encrypt(request);
	    	}
	    

	    public OrganizationVO getInitalOrganization(DynamicDict dto ) throws Exception{
			String partyRoleId = (String)dto.getValueByName("parameter");
			PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
			PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey(partyRoleId);
			
			if( partyRoleVO == null )
				return null ;
			dto.setValueByName("parameter", partyRoleVO.getOrgPartyId()) ;
			return getOrganization(dto);
		}

	    public OrganizationVO getOrganization(DynamicDict dto) throws Exception
	    {
	    	String party_id=  (String)dto.getValueByName("parameter");
	        OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
	        OrganizationVO voOrg = daoOrganization.findByPrimaryKey(party_id);
	        
	        OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
	        OrganizationTypeVO voOrgType = daoOrganizationType.findByPrimaryKey(voOrg.getOrgTypeId());
	        voOrg.setOrgTypeName(voOrgType.getOrgTypeName());
	        
	        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
	        PartyVO voParty = daoParty.findByPrimaryKey(party_id);
	        
	        voOrg.setEffDate(voParty.getEffDate());
	        voOrg.setAddDescription(voParty.getAddDescription());
	        if(!"00".equals(voOrg.getOrgClass()))
	        {
	            PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	            ArrayList alPartyRoles = daoPartyRole.getPartyRolesByParty(party_id);
	            if(alPartyRoles.size()>0)
	            {
	                PartyRoleVO voPartyRole = (PartyRoleVO)alPartyRoles.get(0);
	                voOrg.setOrgManager(voPartyRole.getOrgManager());
	                voOrg.setExpDate(voPartyRole.getExpDate());
	                if("01".equals(voOrg.getOrgClass()))
	                {
	                    PartnerDAO daoPartner = PartnerDAOFactory.getPartnerDAO();
	                    voOrg.setPartnerType(daoPartner.findByPrimaryKey(voPartyRole.getPartyRoleId()).getPartnerType());
	                }
	            }
	        }
	        return voOrg;
	    }
	    
	    public StaffVO getStaff(DynamicDict dto) throws Exception {
	    	String partyRoleId =  (String)dto.getValueByName("parameter");
			StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
			return daoStaff.findByPrimaryKey(partyRoleId);
	    }

	    public List getInitalDefaultDealExch(DynamicDict dto) throws Exception {
	    	PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
			return daoPriv.findByCond("privilege_code = 'DEFPRV'");
	    }
	    
	    public List getStaffPriv(DynamicDict dto) throws Exception {
	    	String partyRoleId =  (String)dto.getValueByName("parameter");
	    	StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	    	
			return daoStaffPriv.getStaffPrivsByStaff(partyRoleId);
	    }
	    
	    public RegionVO getRegion(DynamicDict dto) throws Exception {
	    	String regionId =  (String)dto.getValueByName("parameter");
	    	RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
	    	
			return daoRegion.findByPrimaryKey(regionId);
	    }
	    
	    public ArrayList getInitDepartTerm(DynamicDict dto) throws Exception {
	    	String partyId =  (String)dto.getValueByName("parameter");
	    	
	    	MpDepartTermDAO daoMpDepartTerm = MpDepartTermDAOFactory.getMpDepartTermDAO();
	    	return daoMpDepartTerm.getMpDepartTermsByParty(partyId);
	    }

	    public MpDepartVO getMpDepart(DynamicDict dto) throws Exception {
	    	String orgId =  (String)dto.getValueByName("parameter");
	    	MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
			return daoMpDepart.findByPrimaryKey(orgId);
	    }
	    
	    public RrLanVO getRrLanByCond(DynamicDict dto) throws Exception {
	    	String partyRoleId =  (String)dto.getValueByName("parameter");
//	    	MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
//	    	daoMpDepart.setDto(dto);
//			return daoMpDepart.findByPrimaryKey(orgId);
			
			PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
			
			PartyRoleVO vo = partyRoleDao.findByPrimaryKey(partyRoleId);
			dto.setValueByName("parameter", vo.getLanId()) ;
			return getRrLan(dto);
	    }
	    
	    public RrLanVO getRrLan(DynamicDict dto) throws Exception
	    {
	    	String rrlan_id = (String)dto.getValueByName("parameter");
	        RrLanDAO daoRrLan = RrLanDAOFactory.getRrLanDAO();
	        RrLanVO voRet = daoRrLan.findByPrimaryKey(rrlan_id);
	        AreaCodeDAO daoAreaCode = AreaCodeDAOFactory.getAreaCodeDAO();
	        AreaCodeVO voAreaCode= daoAreaCode.findByPrimaryKey(rrlan_id);
	        if(voAreaCode!=null)
	        {
	            voRet.setAreaCode(voAreaCode.getAreaCode());
	        }
	        return voRet;
	    }
	    public RrBusinessVO getInitBusiness(DynamicDict dto) throws Exception
	    {
	    	String businessId =  (String)dto.getValueByName("parameter");
	    	RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
			return daoRrBusiness.findByPrimaryKey(businessId);
	    }

	    public PartyRoleVO getPartyRole(DynamicDict dto) throws Exception
	    {
	    	String partyRoleId =  (String)dto.getValueByName("parameter");
	    	PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
			return partyRoleDao.findByPrimaryKey(partyRoleId);
			
	    }
	    public static void main(String[] args){
	    	DesEncrypt descript= new DesEncrypt();
	    	String enStr = descript.encryptTriDes("200416779123");
	    	System.out.println("password:"+enStr);
	    }
	    public ChangePasswordRespond updatePassword(DynamicDict dto ) throws Exception {
	    	ChangePasswordRequest request =  (ChangePasswordRequest)dto.getValueByName("parameter");
	    	String staffCode = request.getStaffCode();
	    	String oldPwd = request.getOldPassword();
	    	String newPwd = request.getNewPassword();
	    	ChangePasswordRespond res = new ChangePasswordRespond();
	    	//修改密码 做加密处理 liuyuming 2010-09-21
	        DesEncrypt descript= new DesEncrypt();
	        oldPwd = descript.encryptTriDes(oldPwd);
	        newPwd = descript.encryptTriDes(newPwd);
	    	oldPwd=oldPwd==null?"":oldPwd; 
	    	newPwd=newPwd==null?"":newPwd; 
	    	if(null==oldPwd||"".equals(oldPwd)){
	    		res.setSuccess("0");
	    		res.setReason("旧密码加密失败!");
		        return res ;
	        }
	    	if(null==newPwd||"".equals(newPwd)){
	    		res.setSuccess("0");
	    		res.setReason("新密码加密失败!");
		        return res ;
	        }
	    	
	    	 Connection conn = null;
	         Statement stmt = null;
	         ResultSet rs = null;
	         String SQL = "update party_role set password='"+newPwd+
	         			  "' where party_role_id = (select party_role_id from staff where staff_code='"+staffCode+
	         			  "') and password='"+oldPwd+"'";
	         try {
	         	conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
	         	stmt = conn.createStatement();
	         	int num = stmt.executeUpdate(SQL);
	         	
	         	if(num >= 1){
	         		res.setReason("密码修改成功!");
	         		res.setStrMsgNo("密码修改成功!");
	         		res.setSuccess("0");
	         	}else{
	         		res.setReason("旧密码不正确或修改密码失败!");
	         		res.setStrMsgNo("");
	         		res.setSuccess("1");
	         	}
	         }
	         catch (Exception se) {
	             Debug.print(SQL,this);
	             se.printStackTrace();
	         }
	         finally {
	             DAOUtils.closeResultSet(rs, this);
	             DAOUtils.closeStatement(stmt);
//	             DAOUtils.closeConnection(conn, this);
	         }
	    	return res;
	    }
}
