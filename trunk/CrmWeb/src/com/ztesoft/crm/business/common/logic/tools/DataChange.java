/**   
* @Title: DateChange.java 
* @Package com.ztesoft.crm.business.common.logic.model 
* @Description: ����ǰ��̨����ת��
* @author wanjf
* @date 2010-1-25 ����08:58:08 
* @version V1.0  
*/ 
package com.ztesoft.crm.business.common.logic.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInst;
import com.ztesoft.crm.business.common.logic.model.OfferInstDetail;
import com.ztesoft.crm.business.common.logic.model.OfferInstParty;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServAccNbr;
import com.ztesoft.crm.business.common.logic.model.ServAcct;
import com.ztesoft.crm.business.common.logic.model.ServBillPost;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
import com.ztesoft.crm.business.common.logic.model.ServState;
import com.ztesoft.crm.business.common.model.VAcceptRequest;
import com.ztesoft.crm.business.common.model.VServ;

/** 
 * @ClassName: DateChange 
 * @Description: ����ǰ��̨����ת�� 
 * @author wanjf
 * @date 2010-1-25 ����08:58:08 
 *  
 */
public class DataChange {
	
	/** 
	* @Title: getServsFromReq 
	* @Description: ȡ����Ʒʵ���б���Ϣ
	* @param @param vacceptrequest ǰ̨�������
	* @param @return  �����Serv����
	* @return List    �������� 
	* @throws null
	*/ 
	public List getServsFromReq(VAcceptRequest vacceptrequest) {
		List servs = new ArrayList();
		
		//���Ϊ��
		if (null == vacceptrequest) {
			return servs;
		}
		
		List vServers = vacceptrequest.getServs();
		
		//��ε�����Ʒ�б�Ϊ��
		if (null == vServers) {
			return servs;
		}		
		
		//��ǰ̨���������ȡ��Serv��
		for (int i = 0; i < vServers.size(); i++) {
			Serv serv = new Serv();
			servs.add(serv);
			
			VServ vserv = (VServ)vServers.get(i);
			
			//��������Ʒ����
			serv.loadFromMap(vserv.getServ_attrs());
			
			//��ȡ����Ʒ�����б��е�����
			getServAttrList(vacceptrequest, serv);
			
			//��ȡ������Ʒ��Ϣ
			getProductList(vacceptrequest, vserv, serv);
			
			//��ȡ����������
			getservAcctList(vacceptrequest, vserv, serv);
			
			//��ȡ����Ʒʵ��״̬����
			getservstateList(vacceptrequest, vserv, serv);
			
			//��ȡ����Ʒʵ�����Ӻ�����Ϣ
			getservaccnbrList(vacceptrequest, vserv, serv);

  			//��ȡ����Ʒʵ���˵�Ͷ����Ϣ
			getservbillpostList(vacceptrequest, vserv, serv);
		}
		
		return servs;
	}
	
	/** 
	* @Title: getCompInstsFromReq 
	* @Description: ȡ����Ʒʵ���б���Ϣ
	* @param @param vacceptrequest ǰ̨�������
	* @param @return    list�������CompInst����
	* @return List    �������� 
	* @throws ��
	*/ 
	public List getCompInstsFromReq(VAcceptRequest vacceptrequest) {
		List compinsts = new ArrayList();
		
		//���Ϊ��
		if (null == vacceptrequest) {
			return compinsts;
		}
		
		List vofferInsts = vacceptrequest.getOfferInsts();
		
		//��ε�ȡ����Ʒʵ���б�Ϊ��
		if (null == vofferInsts || vofferInsts.isEmpty()) {
			return compinsts;
		}		
		
		while (0 != vofferInsts.size()) {
			CompInst compinst = new CompInst();
			compinsts.add(compinst);
			
			//1 ��ȡ�������Ϳ�ѡ����Ϣ
			Map offerinstmap = (Map)vofferInsts.get(0);
			
  			//ȡͬһ����Ʒ��Ϣ
			compinst.setOfferInsts(getOfferInstList(vacceptrequest, offerinstmap));
			
			//ȡ����Ʒ������Ϣ
			compinst.setOfferInstDetails(getOfferInstDetailList(vacceptrequest, offerinstmap));
			
			//����Ʒ��������Ϣ
			compinst.setOfferInstPartys(getofferInstPartyList(vacceptrequest, offerinstmap));
			
			//����Ʒ������Ϣ
			compinst.setOfferInstAssures(getofferInstAssureList(vacceptrequest, offerinstmap));
			
			compinst.loadMainOfferInst();			
		}		
		
		return compinsts;
	}
	
	
	//��ȡ��Ʒ������Ϣ
	private void getServAttrList(VAcceptRequest vacceptrequest, Serv serv) {		
		if(null != vacceptrequest.getServAttrs() && !vacceptrequest.getServAttrs().isEmpty()) {  //���ǰ̨�м��ز�Ʒ����
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List vservattrs = vacceptrequest.getServAttrs();			
			
			//ȡͬһserv_id������
			List myvservattrs = UtilTools.findAndRemove(serv.getAttributes(), paramkey, vservattrs); 
			
			//����Ʒ�����ݱ���ϢתΪ���
			serv.loadFromList(myvservattrs);
		}
	}

	//��ȡ������Ʒ��Ϣ
	private void getProductList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_product_loaded_flag())) {  //���ǰ̨�м��ظ�����Ʒ
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servproductlist = new ArrayList();
			List vservproducts = vacceptrequest.getServProducts();			
			
			List myvservproducts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservproducts); //ȡͬһserv_id������
			
			for (int j = 0; j < myvservproducts.size(); j++) {
				Map product = (Map)myvservproducts.get(j);
				
				ServProduct oneservproduct = new ServProduct();
				oneservproduct.loadFromMap(product);
				
				//��ȡ������Ʒ������Ϣ(���ݱ�ת���)
				getServProductAttrList(vacceptrequest, oneservproduct);
				
				servproductlist.add(oneservproduct);
			}
			
			serv.setServProducts(servproductlist);
		}
	}
	
	//��ȡ������Ʒ������Ϣ
	private void getServProductAttrList(VAcceptRequest vacceptrequest, ServProduct servproduct) {		
		if(null != vacceptrequest.getServProductAttrs() && !vacceptrequest.getServProductAttrs().isEmpty()) {  //���ǰ̨�м��ظ�����Ʒ����
			//��������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_PRODUCT_ID};
			
			List vservproductattrs = vacceptrequest.getServProductAttrs();			
			
			//ȡͬһserv_id������
			List myvservproductattrs = UtilTools.findAndRemove(servproduct.getAttributes(), paramkey, vservproductattrs); 
			
			//����Ʒ�����ݱ���ϢתΪ���
			servproduct.loadFromList(myvservproductattrs);
		}
	}
	
  	//��ȡ��������Ϣ
	private void getservAcctList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acct_loaded_flag())) {  //���ǰ̨�м��ظ�����Ʒ
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servacctlist = new ArrayList();
			List vservacctlist = vacceptrequest.getServAccts();		
			
			List myvservaccts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservacctlist); //ȡͬһserv_id������
			
			for (int j = 0; j < myvservaccts.size(); j++) {
				Map vservacct = (Map)myvservaccts.get(j);
				
				ServAcct oneservacct = new ServAcct();
				oneservacct.loadFromMap(vservacct);
				
				servacctlist.add(oneservacct);
			}
			
			serv.setservAccts(servacctlist);
		}
	}
	
	
  	//��ȡ����Ʒʵ��״̬����
	private void getservstateList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_state_loaded_flag())) {  //���ǰ̨�м�������Ʒʵ��״̬����
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servstatelist = new ArrayList();
			List vservstatelist = vacceptrequest.getServStates();
			
			List myvservstates = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservstatelist); //ȡͬһserv_id������
			
			for (int j = 0; j < myvservstates.size(); j++) {
				Map vservstate = (Map)myvservstates.get(j);
				
				ServState oneservstate = new ServState();
				oneservstate.loadFromMap(vservstate);
				
				servstatelist.add(oneservstate);
			}
			
			serv.setServStates(servstatelist);
		}
	}
	
    //��ȡ����Ʒʵ�����Ӻ�����Ϣ
	private void getservaccnbrList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acc_nbr_loaded_falg())) {  //���ǰ̨�м�������Ʒʵ�����Ӻ�����Ϣ
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servaccnbrlist = new ArrayList();
			List vservaccnbrlist = vacceptrequest.getServStates();
			
			List myvservaccnbrs = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservaccnbrlist); //ȡͬһserv_id������
			
			for (int j = 0; j < myvservaccnbrs.size(); j++) {
				Map map = (Map)myvservaccnbrs.get(j);
				
				ServAccNbr oneservaccnbr = new ServAccNbr();
				oneservaccnbr.loadFromMap(map);
				
				servaccnbrlist.add(oneservaccnbr);
			}
			
			serv.setservAccNbrs(servaccnbrlist);
		}
	}

 	//��ȡ����Ʒʵ���˵�Ͷ����Ϣ
	private void getservbillpostList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acc_nbr_loaded_falg())) {  //���ǰ̨�м�������Ʒʵ���˵�������Ϣ
			//������ƷID��ȡ
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servbillpostlist = new ArrayList();
			List vservbillpostlist = vacceptrequest.getServBillPosts();			
			
			List myvservbillposts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservbillpostlist); //ȡͬһserv_id������
			
			for (int j = 0; j < myvservbillposts.size(); j++) {
				Map map = (Map)myvservbillposts.get(j);
				
				ServBillPost oneservbillpost = new ServBillPost();
				oneservbillpost.loadFromMap(map);
				
				servbillpostlist.add(oneservbillpost);
			}
			
			serv.setservBillPosts(servbillpostlist);
		}
	}

    //��ȡ����Ʒ�Ļ�������ѡ����Ϣ
	private List getOfferInstList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInsts = vacceptrequest.getOfferInsts();
		List outofferInsts = new ArrayList();
		//ȡͬһ����Ʒ��Ϣ
		List vMyOfferInsts = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInsts);
		
		//������������Ʒ��Ϣ��ͬʱ��ȡ����Ʒ������Ϣ
		for (int i = 0; i < vMyOfferInsts.size(); i++) {
			Map map = (Map)vMyOfferInsts.get(i);
			
			OfferInst off = new OfferInst();			
			off.loadFromMap(map);
			
			//��ȡ������Ʒ������Ϣ
			getOfferInstAttrList(vacceptrequest, off);
			
			outofferInsts.add(off);			
		}
		
		return outofferInsts;
	}
	
	//��ȡ������Ʒ������Ϣ
	private void getOfferInstAttrList(VAcceptRequest vacceptrequest, OfferInst offinst) {		
		if(null != vacceptrequest.getOfferInstAttrs() && !vacceptrequest.getOfferInstAttrs().isEmpty()) {  //���ǰ̨�м��ظ�����Ʒ����
			//��������ƷID��ȡ
			String[] paramkey = new String[]{Keys.PRODUCT_OFFER_INSTANCE_ID};
			
			List vofferinstattrs = vacceptrequest.getOfferInstAttrs();			
			
			//ȡͬһserv_id������
			List myvofferinstattrs = UtilTools.findAndRemove(offinst.getAttributes(), paramkey, vofferinstattrs); 
			
			//����Ʒ�����ݱ���ϢתΪ���
			offinst.loadFromList(myvofferinstattrs);
		}
	}
	
	//��ȡ����Ʒ������ϸ��Ϣ
	private List getOfferInstDetailList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstDetails = vacceptrequest.getOfferInstDetails();
		List outofferInstDetails = new ArrayList();
		//ȡͬһ������Ʒ��Ϣ
		List vMyOfferInstDetails = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstDetails);
		
		for (int i = 0; i < vMyOfferInstDetails.size(); i++) {
			Map map = (Map)vMyOfferInstDetails.get(i);
			
			OfferInstDetail off = new OfferInstDetail();
			
			off.loadFromMap(map);
			
			//�ж��Ƿ���eff_date�����û�оͲ�һ��
			if ("".equals(Const.getStrValue(map, "eff_date"))) {
				off.set("eff_date", DateFormatUtils.getFormatedDateTime());
			}
			
			outofferInstDetails.add(off);			
		}
		
		return outofferInstDetails;
	}
	
  	//��ȡ����Ʒ��������Ϣ 
	private List getofferInstPartyList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstPartys = vacceptrequest.getOfferInstParties();
		List outofferInstPartys = new ArrayList();
		//����Ʒ��������Ϣ
		List vMyofferInstPartys = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstPartys);
		
		//map to ����
		for (int i = 0; i < vMyofferInstPartys.size(); i++) {
			Map map = (Map)vMyofferInstPartys.get(i);
			
			OfferInstParty off = new OfferInstParty();
			
			off.loadFromMap(map);
			outofferInstPartys.add(off);			
		}
		
		return outofferInstPartys;
	}
	
	//��ȡ����Ʒ������Ϣ
	private List getofferInstAssureList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstAssures = vacceptrequest.getOfferInstAssures();
		List outofferInstAssures = new ArrayList();
		//����Ʒ��������Ϣ
		List vMyofferInstPartys = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstAssures);
		
		//map to ����
		for (int i = 0; i < vMyofferInstPartys.size(); i++) {
			Map map = (Map)vMyofferInstPartys.get(i);
			
			OfferInstParty off = new OfferInstParty();
			
			off.loadFromMap(map);
			outofferInstAssures.add(off);			
		}
		
		return outofferInstAssures;
	}
}
