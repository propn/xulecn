package com.ztesoft.oaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.vo.CustCtrlInfoVo;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.PositionVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RolesVO;

public class PrivilegeService extends DictService {
	
	public PrivilegeService() {
	}
	
	/**
	 * �ж�Ա����ĳȨ����ĳ�������Ƿ���Ȩ
	 * @param privCode	Ȩ���������루VARCHAR(50)�������ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 * @param regionId		
	 * @param regionType	������������	region_type
	 * 															'0':�Ʒ��ߵĵ���
	 *																'1':��Դ�ߵ���
	 *																'2':Ӫ���ĵ���
	 *																'3':Ӫ���ߵ���֯
	 * @param privType		Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
	 * @return
	 * @throws Exception
	 */
	public boolean isPrivilegeInRegion(String privCode , String regionId, String regionType, String privType ) throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleState[] = loginRespond.getRoleState();
		boolean returnValue = false ;
		ArrayList privCodeList = getPrivCodeByType( privType, privCode ) ;
		Set regionIdSet = new HashSet();
		for( int i = 0 ; i < privCodeList.size() ; i ++ ){
			for( int j = 0 ; j < roleState.length ; j ++ ){
				String pCode = (String)privCodeList.get(i);
				//if( pCode.equals(privCode) && regionId.equals(rId) && regionType.equals(roleState[j].getPermitionFlag())){
				if( pCode.equals(roleState[j].getPrivilegeCode()) && regionType.equals(roleState[j].getPermitionFlag())){
					regionIdSet.add(roleState[j].getRegionId());
				}
			}
		}
		if( regionIdSet.size() == 0 ){
			return false ;
		}
		StringBuffer cond = new StringBuffer();
		Iterator it = regionIdSet.iterator();
		while( it.hasNext()){
			cond.append("," + it.next());
		}
		cond.deleteCharAt(0);
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"isPrivilegeInRegion") ;
	 	Map m = new HashMap() ;
	 	m.put("privCode", privCode) ;
		m.put("regionId", regionId) ;
	 	m.put("regionType", regionType) ;
		m.put("privType", privType) ;
		m.put("cond", cond) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
		
		
	}
	
	 public ArrayList getPrivCodeByType( String privType, String privCode ) throws Exception{
		 DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivCodeByType") ;
		 	Map m = new HashMap() ;
		 	m.put("privType", privType) ;
			m.put("privCode", privCode) ;
			dto.setValueByName("parameter", m) ;
			dto = ActionDispatch.dispatch(dto);

			return ((ArrayList)dto.getValueByName("result")) ;
	 }
	/**
	 * ͨ�����ݿ��ѯǰҳ�浱ǰ���ݼ���ǰ�ͻ������µĿɶ��ֶ��б�
	 * @param pageCode
	 * @param datasetCode
	 * @param custTypeId
	 * @return
	 * @throws Exception
	 */
	private ArrayList getCustCtrlDataInfoFromDatabase(String pageCode, String datasetCode, String custTypeId) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId("" , "99I");
		if( privilegeIds.size() == 0 ){
			return new ArrayList() ;
		}

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getTelecomOrganizationListByParentId") ;
		Map m = new HashMap() ;
	 	m.put("pageCode", pageCode) ;
		m.put("datasetCode", datasetCode) ;
	 	m.put("custTypeId", custTypeId) ;
		m.put("privilegeIds", privilegeIds) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 *ȡ����ǰҳ�浱ǰ���ݼ���ǰ�ͻ������µĿɶ��ֶ��б�ÿ�β�ѯ�󻺴���session�У� 
	 * @param pageId
	 * @param datasetId
	 * @param custTypeId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCustCtrlDataInfo(String pageCode, String datasetCode, String custTypeId) throws Exception {
		ArrayList custCtrlDataList = (ArrayList)RequestContext.getContext().getHttpSession().getAttribute("CustCtrlDataList");
		ArrayList returnList = new ArrayList() ;
		if( custCtrlDataList != null ){//���Session���Ѿ��л���
			for( int i = 0 ; i < custCtrlDataList.size() ; i ++ ){
				CustCtrlInfoVo custCtrlInfoVo = (CustCtrlInfoVo)custCtrlDataList.get(i);
				if( pageCode.equals(custCtrlInfoVo.getPageCode()) &&
						datasetCode.equals(custCtrlInfoVo.getDatasetCode()) &&
						custTypeId.equals(custCtrlInfoVo.getCustTypeId())){
					if( "999999".equals(custCtrlInfoVo.getFieldCode())){//����������FieldCodeΪ999999,�򷵻ؿ��б�����������
						return new ArrayList();
					}else{
						returnList.add(custCtrlInfoVo.getFieldCode());//����ѯ����FieldCode���뵽�����б���
					}
				}
			}
		}
		
		//���Session��û�л�����߲�ѯSession�Ժ�,û�з��ֲ�����Ҫ��ѯ��FieldCode,���������ѯ���ݿ�.
		if( custCtrlDataList == null || returnList.size() == 0 ){
			if( custCtrlDataList == null ){
				custCtrlDataList = new ArrayList();
			}
			ArrayList queryDataList = getCustCtrlDataInfoFromDatabase( pageCode, datasetCode, custTypeId ) ;
			if( queryDataList.size() == 0 ){//�����ѯ���ݿ�Ҳ��ѯ��������
				//��Session�б������Ϣ����һ��FieldCodeΪ999999�ļ�¼,�����ؿ��б�����������
				CustCtrlInfoVo vo = new CustCtrlInfoVo() ;
				vo.setCustTypeId(custTypeId) ;
				vo.setDatasetCode(datasetCode) ;
				vo.setPageCode(pageCode) ;
				vo.setFieldCode("999999") ;
				custCtrlDataList.add(vo) ;
				RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",custCtrlDataList) ;
				return new ArrayList() ;
			}else{//��������ݿ����ܹ���ѯ�����ϲ���Ҫ��ļ�¼
				for( int i = 0 ; i < queryDataList.size() ; i ++ ){
					CustCtrlInfoVo vo = (CustCtrlInfoVo)queryDataList.get(i);
					if( pageCode.equals(vo.getPageCode()) &&
					datasetCode.equals(vo.getDatasetCode()) &&
					custTypeId.equals(vo.getCustTypeId())){
						returnList.add(vo.getFieldCode());//����ѯ����FieldCode���뵽�����б���
					}
				}
				if(custCtrlDataList == null){
					//���Session��û�л���,�����ݿ��ѯ�����Ľ�����浽Session
					RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",queryDataList);
				}else{
					custCtrlDataList.addAll(queryDataList);
					//���Session���л���,����û�е��β�ѯ������,�򽫵��β�ѯ���������ӵ�Session�е�
					//������,������Session�Ļ���.
					RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",custCtrlDataList);
				}
			}
		}
		return returnList ;
	}
	
	/**
	 * ��ѯMM_DATA_PRIVILEGE��,��ȡȨ��ID��Ӧ�Ĳ˵�ID
	 * @param privId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getMenuIdByPrivilege( String privId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuIdByPrivilege") ;
				dto.setValueByName("parameter", privId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 * ��ȡԱ����Ȩ�޵Ķ��۲���Ŀ¼
	 * @param partyRoleId Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getPricingParamCatalogByPrivilege( String partyRoleId ) throws Exception {
		
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99G");//��ѯԱ��ӵ�е�����Ϊ���۲���Ŀ¼��Ȩ��
		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
			return new ArrayList() ;
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPricingParamCatalogByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
			}
	/**
	 * ��ȡԱ����Ȩ�޵ĺ���ȼ�
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getRnLevelByPrivilege( String partyRoleId ) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99H");//��ѯԱ��ӵ�е�����Ϊ����ȼ���Ȩ��
		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
			return new ArrayList() ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRnLevelByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	
	/**
	 * ��ȡԱ����Ȩ�޵���֯  ѡ��
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrganizationByPrivilege( String partyRoleId,String cProductId,HttpServletRequest request) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99X" , request);//��ѯԱ��ӵ�е�����Ȩ��
		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
			return new ArrayList() ;
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrganizationByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * ��ȡԱ����Ȩ�޵���֯  ��ͷ����
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrgForNoHeadByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Y" , request);//��ѯԱ��ӵ�е�����Ȩ��
		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
			return new ArrayList() ;
		}
		

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRnLevelByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("partyRoleId", partyRoleId) ;
		m.put("noFamilyId", noFamilyId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	
	
	/**
	 * ��ȡԱ����Ȩ�޵���֯  �������
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrgForDeliverNoByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
		
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Z" , request);//��ѯԱ��ӵ�е�����Ȩ��
		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
			return new ArrayList() ;
		}
		

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrgForDeliverNoByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("partyRoleId", partyRoleId) ;
		m.put("noFamilyId", noFamilyId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * ��ȡԱ����ĳ��Ʒ��Ȩ�ķ����ṩ
	 * @param partyRoleId Ա��ID,Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�
	 * Session�л�ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @param offerId ��ƷID
	 */
	public ArrayList getOfferServiceByPrivilege(String partyRoleId, String offerId ) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99F");//��ѯԱ��ӵ�е�����Ϊ�����ṩ��Ȩ��
		if( privilegeIds.size() == 0 ){
			return new ArrayList() ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrgForDeliverNoByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("voOrg", partyRoleId) ;
		m.put("offerId", offerId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * �Ӵ���Request�л�ȡSession,��Session�л�ȡԱ����ĳ��Ȩ��
	 * @param partyRoleId	Ա����ʶ(Ԥ��)
	 * @param privilegeType	Ȩ��ʹ������:
	 * 99B	Ҷ�Ӳ˵���
	 * 99F	��Ʒ�����ṩ
	 * 99G	���۲���Ŀ¼
	 * 99H	����ȼ�
	 * 99I	�ͻ�����
	 * 99J	�ۺϲ�ѯ
	 * @return
	 */
	public ArrayList getStaffPrivilegeIdFromSession(String partyRoleId , String privilegeType, HttpServletRequest request){
		//��Session�в��ҴӼ�Ȩ���������ص��û���½��Ϣ	
		return getStaffPrivilegeIdFromSession( partyRoleId , privilegeType, request.getSession()) ;		
	}
	
	/**
	 * �Ӵ���Request�л�ȡSession,��Session�л�ȡԱ����ĳ��Ȩ��
	 * @param partyRoleId	Ա����ʶ(Ԥ��)
	 * @param privilegeType	Ȩ��ʹ������:
	 * 99B	Ҷ�Ӳ˵���
	 * 99F	��Ʒ�����ṩ
	 * 99G	���۲���Ŀ¼
	 * 99H	����ȼ�
	 * 99I	�ͻ�����
	 * 99J	�ۺϲ�ѯ
	 * @return
	 */
	public ArrayList getStaffPrivilegeIdFromSession(String partyRoleId , String privilegeType, HttpSession session){
		//��Session�в��ҴӼ�Ȩ���������ص��û���½��Ϣ
		LoginRespond respond = (LoginRespond)session.getAttribute("LoginRespond");
		RoleState[] roleStates = respond.getRoleState();
		ArrayList returnList = new ArrayList() ;
		Set privilegeSet = new HashSet();
		for( int i = 0 ; i < roleStates.length ; i ++ ){
			RoleState roleState = roleStates[i];
			if( privilegeType.equals(roleState.getPartyRoleSchema())){//���Ȩ������Ϊ������ָ��,��洢�������б���
				//returnList.add(roleState.getPrivilegeId());
				privilegeSet.add(roleState.getPrivilegeId());//ȥ���ظ���Ȩ��ID
			}
		}
		returnList.addAll(privilegeSet);
		return returnList ;		
	}

	/**
	 * ��ȡԱ����ĳ��Ȩ��
	 * @param partyRoleId	Ա����ʶ(Ԥ��)
	 * @param privilegeType	Ȩ��ʹ������:
	 * 99B	Ҷ�Ӳ˵���
	 * 99F	��Ʒ�����ṩ
	 * 99G	���۲���Ŀ¼
	 * 99H	����ȼ�
	 * 99I	�ͻ�����
	 * 99J	�ۺϲ�ѯ
	 * @return
	 */
	public ArrayList getStaffPrivilegeId(String partyRoleId, String privilegeType) {
		return this.getStaffPrivilegeIdFromSession(partyRoleId, privilegeType , RequestContext.getContext().getHttpRequest());
	}
	
	/**
	 * �жϵ�ǰ�û��Ƿ�ӵ�в����ƶ���Ȩ��
	 * @param privilegeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeId(String privilegeId) throws Exception {
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			if (privilegeId.equals(roleState.getPrivilegeId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �жϵ�ǰ�û��Ƿ�ӵ�в����ƶ���Ȩ��
	 * @param privilegeCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeCode(String privilegeCode) throws Exception {
		return checkPrivilegeCodeFromSession(RequestContext.getContext().getHttpRequest(), privilegeCode) ;
	}
	public boolean checkPrivilegeCodeFromSession(HttpServletRequest req, String privilegeCode ) throws Exception {
		Object obj = req.getSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			if (privilegeCode.equals(roleState.getPrivilegeCode())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * �������������У��ͻ��˴�������Ȩ���Ƿ�Ϊ��ǰ�û���ӵ�е�Ȩ��, ����ǰ�û���ӵ��
	 * ��Ȩ�޹��˵�,���ص�ǰ�û���û�е�Ȩ��.
	 * @param privilegesToCheck
	 * @return
	 */
	public ArrayList privilegesCheck(String[] privilegesToCheck) {
		ArrayList list = new ArrayList();
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int j = 0; j < privilegesToCheck.length; j++) {
			boolean found = false;
			for (int i = 0; i < roleStates.length; i++) {
				RoleState roleState = roleStates[i];
				if (privilegesToCheck[j].equals(roleState.getPrivilegeCode())) {
					found = true;
					break;
				}
			}
			if (!found) {
				list.add(privilegesToCheck[j]);
			}
		}
		return list;
	}

	/**
	 * �������������У��ͻ��˴�������Ȩ���Ƿ�Ϊ��ǰ�û���ӵ�е�Ȩ��, ����ǰ�û���ӵ�е�
	 * Ȩ�޹��˵�,���ص�ǰ�û���û�е�Ȩ��.
	 * @param privilegesToCheck
	 * @param session
	 * @return
	 */
	public ArrayList privilegesCheckWithSession(String[] privilegesToCheck,
			HttpSession session) {
		ArrayList list = new ArrayList();
		Object obj = session.getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;

		RoleState[] roleStates = loginRespond.getRoleState();

		for (int j = 0; j < privilegesToCheck.length; j++) {
			boolean found = false;
			for (int i = 0; i < roleStates.length; i++) {
				RoleState roleState = roleStates[i];
				if (privilegesToCheck[j].equals(roleState.getPrivilegeCode())) {
					found = true;
					break;
				}
			}
			if (!found) {
				list.add(privilegesToCheck[j]);
				found = false;
			}
		}
		return list;
	}

	// **************************************ENTITY[MmMenu]**************************************
	/**
	 * ����˵�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½��˵���ʶ
	 */
	public String insertMenu(MmMenuVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertMenu") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ���²˵�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateMenu(MmMenuVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updateMenu") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ���˵�ʵ��
	 * 
	 * @param pmmmenu_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public long deleteMenu(String menu_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteMenu") ;
		dto.setValueByName("parameter", menu_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	/**
	 * ɾ���˵�ʵ�壬����������Ӳ˵�Ҳһ��ɾ��
	 * @param menu_id
	 * @return
	 * @throws Exception
	 */
	
	public long deleteMenuWithSubNotes(String menu_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteMenuWithSubMenu") ;
		dto.setValueByName("parameter", menu_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	
	

	/**
	 * ��ȡ�ϼ��˵�Id����Ӧ�������¼��˵�.
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public String getMenuByParentId( String parentId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuByParentId") ;
		dto.setValueByName("parameter", parentId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;


	}
	/**
	 * ��ȡ����TreeList�����XML��ʽ�˵���
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList��������в˵����ɵ�XML
	 * �ַ���
	 */
	public String getMenuList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * �жϲ˵������Ƿ��Ѿ���ϵͳ�д���,�����Ӳ˵���ʱ��,��Ҫ�Բ˵��������Ψһ���ж�
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public int checkMenuCodeInUsed(String menuCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkMenuCodeInUsed") ;
		dto.setValueByName("parameter", menuCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}


	// **************************************ENTITY[Privilege]**************************************

	public String getMenuDataPrivilege(String privilegeId) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuDataPrivilege") ;
		dto.setValueByName("parameter", privilegeId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;


	}
	public ArrayList getDataPrivilegeByPrivilegeType( String privType,String privId ) throws Exception {DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getDataPrivilegeByPrivilegeType") ;
						 	Map m = new HashMap() ;
						 	m.put("privType", privType) ;
							m.put("privId", privId) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

				return ((ArrayList)dto.getValueByName("result")) ;

	}
	
	public ArrayList getDataPrivilegeByPrivilegeId( String privilegeId,String type ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getDataPrivilegeByPrivilegeId") ;
		Map m = new HashMap() ;
	 	m.put("privilegeId", privilegeId) ;
		m.put("type", type) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	public ArrayList getSimpleDataPrivilegeRule() throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getSimpleDataPrivilegeRule") ;
		dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	public void addDataPrivilege(MmDataPrivVO vo ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addDataPrivilege") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
	}
	
	public void addDataPrivileges(MmDataPrivVO[] dataPrivileges,String privType ) throws Exception{

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addDataPrivileges") ;
						 	Map m = new HashMap() ;
						 	m.put("dataPrivileges", dataPrivileges) ;
							m.put("privType", privType) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);
	}
	public void addMenuDataPrivileges(ArrayList dataPrivileges) throws Exception {
		MmDataPrivVO[] params = new MmDataPrivVO[dataPrivileges.size()];
		for(int i = 0;i<dataPrivileges.size();i++)
		{
			params[i] = (MmDataPrivVO)dataPrivileges.get(i);
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addMenuDataPrivileges") ;
				dto.setValueByName("parameter", params) ;
				dto = ActionDispatch.dispatch(dto);
	}
	public void deleteDataPrivilege(ArrayList voLs ) throws Exception{
		MmDataPrivVO[] params = new MmDataPrivVO[voLs.size()];
		for(int i = 0;i<voLs.size();i++)
		{
			params[i] = (MmDataPrivVO)voLs.get(i);
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteDataPrivilege") ;
		dto.setValueByName("parameter", params) ;
		dto = ActionDispatch.dispatch(dto);
	}
	/**
	 * ����Ȩ��ʵ��
	 * 
	 * @param vo
	 *            Ȩ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
	 */
	public String insertPrivilege(PrivVO vo) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertPrivilege") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ����Ȩ�ޱ�ʶ����Ȩ��ʵ��
	 * 
	 * @param vo
	 *            Ȩ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
	 */
	public boolean updatePrivilege(PrivVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updatePrivilege") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * ɾ��Ȩ��ʵ��
	 * 
	 * @param pprivilege_id
	 *            Ȩ�ޱ�ʶ
	 * @return �������
	 */
	public int deletePrivilege(String pprivilege_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deletePrivilege") ;
		dto.setValueByName("parameter", pprivilege_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;

	}

	/**
	 * �ж�һ��Ȩ���Ƿ��Ѿ���ϵͳʹ��,����Ѿ���ϵͳʹ����,����ɾ��
	 * @param privilegeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeInUsed(String privilegeId ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkPrivilegeInUsed") ;
		dto.setValueByName("parameter", privilegeId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}
	/**
	 * ��ȡ����TreeList�����XML��ʽȨ����
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList���������Ȩ�޹��ɵ�XML�ַ���
	 */
	public String getPrivilegeList() throws Exception {


		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;	}
	
	public String getPrivilegeListWithRegionRelatByName( String privilegeName ) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleStates[] = loginRespond.getRoleState();
		Set privilegeSet = new HashSet();
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//������ǳ�������Ա����,���¼�ù�����ӵ�е�Ȩ��,��ʹ����ЩȨ������ѯ,����ǳ�������
		//Ա����,�򷵻����е�Ȩ��
		if( !loginRespond.isSuperStaff() ){
			for( int i = 0 ; i < roleStates.length ; i ++ ){
				privilegeSet.add(roleStates[i].getPrivilegeId());
			}
		}
		if( privilegeSet.size() == 0 && !loginRespond.isSuperStaff() ){
			return "<items/>";
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListWithRegionRelatByName") ;
	 	Map m = new HashMap() ;
	 	m.put("privilegeName", privilegeName) ;
		m.put("privilegeSet", privilegeSet) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((String)dto.getValueByName("result")) ;

	}

	public String getPrivilegeListWithRegionRelatByParentId( String parentId ) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleStates[] = loginRespond.getRoleState();
		Set privilegeSet = new HashSet();
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//������ǳ�������Ա����,���¼�ù�����ӵ�е�Ȩ��,��ʹ����ЩȨ������ѯ,����ǳ�������
		//Ա����,�򷵻����е�Ȩ��
		//if( !superStaffCode.equals(loginRespond.getStaffCode())){
		if( !loginRespond.isSuperStaff() ){
			for( int i = 0 ; i < roleStates.length ; i ++ ){
				privilegeSet.add(roleStates[i].getPrivilegeId());
			}
		}
		//if( privilegeSet.size() == 0 && !superStaffCode.equals(loginRespond.getStaffCode())){
		if( privilegeSet.size() == 0 && !loginRespond.isSuperStaff() ){
			return "<items/>";
		}
		
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListWithRegionRelatByParentId") ;
	 	Map m = new HashMap() ;
	 	m.put("parentId", parentId) ;
		m.put("privilegeSet", privilegeSet) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((String)dto.getValueByName("result")) ;
		
	}
	
	public String getPrivilegeListByParentId(String parentId) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListByParentId") ;
				dto.setValueByName("parameter", parentId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
 
	}


	// **************************************ENTITY[RolePriv]**************************************

	/**
	 * ��������ɫȨ��ʵ��
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @param privIds
	 *            Ȩ��Id����
	 * @return �������
	 */
	public void insertRolePriv(String roleId, ArrayList privIds, String type)
			throws Exception {
		if(privIds == null || privIds.isEmpty() )
			return ;
		
		String[] sarray = new String[privIds.size()] ;
		for(int i=0 , j=privIds.size() ; i < j ; i++){
			sarray[i] = (String)privIds.get(i) ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertRolePriv") ;
						 	Map m = new HashMap() ;
						 	m.put("roleId", roleId) ;
							m.put("privIds", sarray) ;
							m.put("type", type) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);
	}

	/**
	 * ɾ����ɫȨ��ʵ��
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @param privId
	 *            Ȩ��Id
	 * @return �������
	 */
	public boolean deleteRolePriv(String role_id, String priv_id,
			String priv_type) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRolePriv") ;

	 	Map m = new HashMap() ;
	 	m.put("role_id", role_id) ;
		m.put("priv_id", priv_id) ;
		m.put("priv_type", priv_type) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ���ݽ�ɫ��ѯ����Ȩ��
	 * 
	 * @param role_id
	 *            ��ɫ��ʶ
	 * @return �������,
	 *         �ɹ�ʱServiceResult.resultObjectΪָ����ɫ��Ӧ������Ȩ�޹��ɵ�������TreeList�����xml�ַ���
	 */
	public String getPrivXMLItemByRoleId(String role_id, String privilegeType)
			throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivXMLItemByRoleId") ;

	 	Map m = new HashMap() ;
	 	m.put("role_id", role_id) ;
		m.put("privilegeType", privilegeType) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result"));
	}

	/**
	 * ���ݽ�ɫ��ѯ����Ȩ��
	 * 
	 * @param role_id
	 *            ��ɫ��ʶ
	 * @return �������,
	 *         �ɹ�ʱServiceResult.resultObjectΪָ����ɫ��Ӧ������Ȩ�޹��ɵ�������TreeList�����xml�ַ���
	 */
	public ArrayList getPrivByRoleId(String roleId, String privilegeType) throws Exception  {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivByRoleId") ;
						 	Map m = new HashMap() ;
						 	m.put("privilegeType", privilegeType) ;
							m.put("roleId", roleId) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

				return ((ArrayList)dto.getValueByName("result")) ;

	}

	public ArrayList getRelatingRoleByRoleId(String roleId)  throws Exception  {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRelatingRoleByRoleId") ;
		dto.setValueByName("parameter",roleId) ;
		dto = ActionDispatch.dispatch(dto);

return ((ArrayList)dto.getValueByName("result")) ;
	}


	// **************************************ENTITY[Role]**************************************
	
	/**
	 * ��ѯ���н�ɫ
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���н�ɫ���ɵ�������TreeList�����xml�ַ���
	 */
	public String getXMLRoleList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getXMLRoleList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;

	}

	public String getXMLRoleListByName(String roleName) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getXMLRoleListByName") ;
		dto.setValueByName("parameter", roleName) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;	}

	public ArrayList getRoleList()  throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRoleList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * �����ɫ,�����²���Ľ�ɫ�������ǰ�����û�
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���ɫ��ʶ
	 */
	public String insertRole(RolesVO vo) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		String partyRoleId = loginRespond.getPartyRoleId() ;
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertRole") ;
				 	Map m = new HashMap() ;
				 	m.put("partyRoleId", partyRoleId) ;
				 	m.put("vo", vo) ;
					dto.setValueByName("parameter", m) ;
					dto = ActionDispatch.dispatch(dto);
					return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ���½�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateRole(RolesVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updateRole") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ����ɫʵ��
	 * 
	 * @param prole_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public int deleteRole(String prole_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRole") ;
		dto.setValueByName("parameter", prole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}
	   public long deleteRoles(String roles_id) throws Exception
	    {
		   DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRoles") ;
			dto.setValueByName("parameter", roles_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((Long)dto.getValueByName("result")).longValue() ;
	    }

	/**
	 * �ж�һ����ɫ�Ѿ�������ʹ��
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleInUsed( String roleId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkRoleInUsed") ;
		dto.setValueByName("parameter", roleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	// **************************************ENTITY[Position]**************************************
	/**
	 * ��ѯ���и�λ���
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���и�λ��񹹳ɵ�������TreeList�����xml�ַ���
	 */
	public String getPositionList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPositionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	public ArrayList getPositionArrayList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPositionArrayList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * �����λ���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���λ����ʶ
	 */
	public String insertPosition(PositionVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertPosition") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;

	}

	/**
	 * ���ݱ�ʶ���¸�λ���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updatePosition(PositionVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updatePosition") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	//��ѯ������ӵ�е�Ȩ��VO(StaffPrivVO����)
	public List getPrivilegeVosByPartyRoleId( String partyRoleId ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeVosByPartyRoleId") ;
				dto.setValueByName("parameter", partyRoleId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((List)dto.getValueByName("result")) ;


	}
	
	//��ѯ������ӵ�е�Ȩ��Id
	public ArrayList getPrivilegeIdsByPartyRoleId( String partyRoleId ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeIdsByPartyRoleId") ;
				dto.setValueByName("parameter", partyRoleId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;


	}
	
	//��ȡ��ɫ��ӵ�е�Ȩ��ID
	public ArrayList getPrivilegeIdsByRoleId( String roleId ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeIdsByRoleId") ;
		dto.setValueByName("parameter", roleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 * ɾ����λ���ʵ��
	 * 
	 * @param pposition_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public boolean deletePosition(String pposition_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deletePosition") ;
		dto.setValueByName("parameter", pposition_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
	
	//����Ƿ�Ȩ�ޱ����Ƿ����
	public boolean hasPrivlegeByCode(String privCode) throws Exception{
		DynamicDict dto=getServiceDTO("PrivilegeBO", "hasPrivlegeByCode");
		dto.setValueByName("parameter", privCode);
		dto=ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
}
