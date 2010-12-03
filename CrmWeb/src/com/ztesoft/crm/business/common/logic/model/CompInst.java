package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.exception.BusiException;
import com.ztesoft.crm.business.common.inst.dao.OffInstAssureDAO;
import com.ztesoft.crm.business.common.inst.dao.OffInstDAO;
import com.ztesoft.crm.business.common.inst.dao.OffInstDetaDAO;
import com.ztesoft.crm.business.common.inst.dao.OffInstPartyDAO;
import com.ztesoft.crm.business.common.order.dao.OrdBookDateDAO;
import com.ztesoft.crm.business.common.utils.SeqUtil;

public class CompInst extends Inst {
	
	public final static String COMP_INST_ID="comp_inst_id";//������Ʒʵ��ID
	
	public final static String ACTION_TYPE="action_type";//ҵ����
	
	public final static String ORD_ID="ord_id";//����ID
	
	public final static String OFFER_ID="product_offer_id";//���ID
	
	public final static String OFFER_KIND="offer_kind";//����Ʒ����
    
	//�������Ϳ�ѡ��ʵ��
	private List offerInsts = null;	
	//���������ѡ��ʵ������
	private List offerInstDetails = null;
	//����Ʒ��������Ϣ
	private List offerInstPartys = null;
	//����Ʒʵ����ϵ����Ϣ
	private List offerInstContacts = null;
	//����Ʒ������Ϣ
	private List offerInstAssures = null;	
	//ҵ��ԤԼ��Ϣ
	private List ordBookDates = null;	
	//ҵ�����ԭ��
	private List ordRemoveCauses =null;
	
	//������������
	private  OrdItemInfo ordItemInfo = new OrdItemInfo();
	
	
	public OrdItemInfo getOrdItemInfo() {
		return ordItemInfo;
	}

	public void setOrdItemInfo(OrdItemInfo ordItemInfo) {
		this.ordItemInfo = ordItemInfo;
	}

	/** 
	* @Title: loadMainOfferInst 
	* @Description: ����������Ʒʵ����Ϣ, ��������Ʒʵ������Ϣ���ص�compInst��attributes��
    *               ʹ��ǰ���뱣֤�Ѿ����ع�����Ʒ��
	* @param     ��
	* @return void   
	* @throws 
	*/ 
	public void loadMainOfferInst()
	{
		this.getOfferInsts();
		OfferInst off = null;
		
		for(int i = 0; i < offerInsts.size(); i++) {
			off = (OfferInst)offerInsts.get(i);
			if (off.get(BusiTables.OFFER_INST.product_offer_instance_id).
                           equals(off.get(BusiTables.OFFER_INST.comp_inst_id)))
			{
				this.loadFromMap(off.getAttributes());
				return;
			}
		}
	}
    
    /** ******************����Ʒʵ�������˵Ĳ�������************************** */
	/** 
	* @Title: getOfferInstPartys 
	* @Description: ȡ����Ʒʵ����������Ϣ�����û�м��ع����ȴ����ݿ������Ϣ 
	* @param   �� 
	* @return List    List of Map 
	* @throws  BusiException
	*/ 
	public List getOfferInstPartys () {
		if (null == this.offerInstPartys)
		{
			this.offerInstPartys = new ArrayList();
			
			try {
				this.offerInstPartys = getofferInstPartysByCompID(get(BusiTables.OFFER_INST.comp_inst_id));
			} catch (FrameException e) {
                throw new BusiException(e);
			}			
		}
		
		return this.offerInstPartys;
	}


	public void setOfferInstPartys(List offerInstPartys) {
		this.offerInstPartys = offerInstPartys;
	}
	
	/** 
	* @Title: getOfferInstParty 
	* @Description: ����rel_id��rel_typeȡ����Ʒʵ����������Ϣ
	* @param srel_type ��ϵ����
	* @param srel_id   ��ϵ��ʶ
	* @return OfferInstParty    û���ҵ�����null
	* @throws ��
	*/ 
	public OfferInstParty getOfferInstParty(String srel_type, String srel_id) {		
		for (int i = 0; i < this.getOfferInstPartys().size(); i++)
		{
			OfferInstParty offparty = (OfferInstParty)this.offerInstPartys.get(i);
			
			if (   srel_id.equals(offparty.get(BusiTables.OFFER_INST_PARTY.rel_id)) 
			    && srel_type.equals(offparty.get(BusiTables.OFFER_INST_PARTY.rel_type)))
			{
				return offparty;
			}
		}
		
		return null;
	}

    
	/** 
	* @Title: delOfferInstParty 
	* @Description:����rel_id��rel_typeɾ������Ʒʵ����������Ϣ
    * @param srel_type ��ϵ����
    * @param srel_id   ��ϵ��ʶ
	* @return OfferInstParty    �Ѿ�ɾ��������Ʒʵ����������Ϣ
	* @throws  ��
	*/ 
	public OfferInstParty delOfferInstParty (String srel_type, String srel_id) {
		OfferInstParty offparty = getOfferInstParty(srel_type, srel_id);
		
		if (null != offparty)
		{
			this.offerInstPartys.remove(offparty);			
		}
		
		return offparty;
	}
	
	/** 
	* @Title: addOfferInstParty 
	* @Description: ����rel_id��rel_type��������Ʒʵ����������Ϣ��
    *               ���û���ҵ�������һ���µ�����Ʒʵ����������Ϣ������ҵ��������ҵ�����Ϣ 
    * @param srel_type ��ϵ����
    * @param srel_id   ��ϵ��ʶ
	* @return OfferInstParty    
	* @throws ��
	*/ 
	public OfferInstParty addOfferInstParty (String srel_type, String srel_id) {
		OfferInstParty offparty = getOfferInstParty(srel_type, srel_id);
		
		if (null == offparty)
		{
			offparty = new OfferInstParty();
			
			offparty.set(BusiTables.OFFER_INST.comp_inst_id, get(BusiTables.OFFER_INST.comp_inst_id));
			offparty.set(BusiTables.OFFER_INST_PARTY.rel_id, srel_id);
			offparty.set(BusiTables.OFFER_INST_PARTY.rel_type, srel_type);
			offparty.setActionType(KeyValues.ACTION_TYPE_A);
			
			offerInstPartys.add(offparty);
		}
				
		return offparty;
	}
	

    /** ******************���������ѡ��ʵ�����ɲ�������************************** */
	/** 
	* @Title: getOfferInstDetails 
	* @Description: ȡ�������Ʒ������ʵ������
	* @param ��
	* @return List    List of Map
	* @throws BusiException
	*/ 
	public List getOfferInstDetails() {		
		if (null == offerInstDetails)
		{
			offerInstDetails = new ArrayList();
			
			try {
				offerInstDetails = getOfferInstDetailsByProOffInstID(get(BusiTables.OFFER_INST.product_offer_instance_id));
			} catch (FrameException e) {
                e.printStackTrace();
                throw new BusiException(e);
			}			
		}
		
		return offerInstDetails;
	}

	public void setOfferInstDetails(List offerInstDetails) {
		this.offerInstDetails = offerInstDetails;
	}
	

	/** 
	* @Title: addOfferInstDetail 
	* @Description: ��������Ʒ��ϸ��ʶ������Ʒʵ����ʶ��ѯ����Ʒʵ�����ɣ���������ڣ���������Ʒ���ɡ����򷵻ز�ѯ���
	* @param sProductOfferInstanceId  ����Ʒʵ����ʶ
	* @param sOfferDetailId  ����Ʒ���ɱ�ʶ
	* @return OfferInstDetail   
	* @throws ��
	*/ 
	public OfferInstDetail addOfferInstDetail(String sProductOfferInstanceId, String sOfferDetailId) {		
		OfferInstDetail offdet = this.getOfferInstDetail(sProductOfferInstanceId, sOfferDetailId);
		
		if (null == offdet)
		{		
			//û�о͹���һ����������Ʒ����
			offdet = new OfferInstDetail();
			
			offdet.set(BusiTables.OFFER_INST.product_offer_instance_id, sProductOfferInstanceId);
			offdet.set(BusiTables.OFFER_INST_DETAIL.offer_detail_id, sOfferDetailId);
			offdet.setActionType(KeyValues.ACTION_TYPE_A);
			
	        offerInstDetails.add(offdet);
		}
		
		return offdet;
	}	
	
	/** 
	* @Title: addOfferInstDetail 
	* @Description: ����һ������Ʒʵ�����ɣ�����ǰ��ȡһ����������Ʒʵ������
	* @param offerInstDetail ������������Ʒʵ������
	* @return OfferInstDetail  ����������Ʒʵ������
	* @throws ��
	*/ 
	public OfferInstDetail addOfferInstDetail(OfferInstDetail offerInstDetail) {		
		this.getOfferInstDetails();
		
		this.offerInstDetails.add(offerInstDetail);
		
		return offerInstDetail;
	}	
	

	/** 
	* @Title: getOfferInstDetail 
    * @Description: ��������Ʒ��ϸ��ʶ������Ʒʵ����ʶ��ѯ����Ʒʵ�����ɣ���������ڣ�����null�����򷵻ز�ѯ���
    * @param sProductOfferInstanceId  ����Ʒʵ����ʶ
    * @param sOfferDetailId  ����Ʒ���ɱ�ʶ
	* @return OfferInstDetail   
	* @throws ��
	*/ 
	public OfferInstDetail getOfferInstDetail(String sproduct_offer_instance_id, String soffer_detail_id) {		
		for (int i = 0; i < this.getOfferInstDetails().size(); i++)
		{
			OfferInstDetail offdet = (OfferInstDetail)this.offerInstDetails.get(i);
			
			if (   soffer_detail_id.equals(offdet.get(BusiTables.OFFER_INST_DETAIL.offer_detail_id)) 
				&& sproduct_offer_instance_id.equals(offdet.get(BusiTables.OFFER_INST.product_offer_instance_id)))
			{
				return offdet;
			}
		}
		
		return null;
	}
	
	
	/** 
	* @Title: getOfferInstDetail 
    * @Description: ��������Ʒʵ����ʶ��ѯ����Ʒʵ�����ɣ���������ڣ�����null�����򷵻ز�ѯ���
    * @param sProductOfferInstanceId  ����Ʒʵ����ʶ
    * @param sOfferDetailId  ����Ʒ���ɱ�ʶ
	* @return OfferInstDetail   
	* @throws ��
	*/ 
	public OfferInstDetail removeOfferInstDetail(String sproduct_offer_instance_id) {		
		for (int i = 0; i < this.getOfferInstDetails().size(); i++)
		{
			OfferInstDetail offdet = (OfferInstDetail)this.offerInstDetails.get(i);
			if (sproduct_offer_instance_id.equals(offdet.get(OFFER_INST.product_offer_instance_id))){
				if(OfferConsts.ACTION_TYPE_A.equals(offdet.get(OFFER_INST.action_type))){//��������� ��ô��ֱ��ɾ�� 
					this.getOfferInstDetails().remove(i);
				}else{//��������� ��Ҫ�Ѷ����ó�D
					offdet.setActionType(OfferConsts.ACTION_TYPE_D);
				}
				return offdet;
			}
		}
		
		return null;
	}
	
	/**
	 * ɾ����Ӧ������Ʒʵ��
	 * @param sproduct_offer_instance_id
	 * @return
	 */
	public OfferInst removeOfferInst(String sproduct_offer_instance_id) {		
		for (int i = 0; i < this.getOfferInsts().size(); i++){
			OfferInst offerInst = (OfferInst)this.offerInsts.get(i);
			if (sproduct_offer_instance_id.equals(offerInst.get(OFFER_INST.product_offer_instance_id))){
				if(OfferConsts.ACTION_TYPE_A.equals(offerInst.get(OFFER_INST.action_type))){//��������� ��ô��ֱ��ɾ�� 
					this.getOfferInsts().remove(i);
				}else{//��������� ��Ҫ�Ѷ����ó�D
					offerInst.setActionType(OfferConsts.ACTION_TYPE_D);
				}
				return offerInst;
			}
		}
		
		return null;
	}
	
	//��������Ʒ��ϸ��ʶɾ��ָ������Ʒ����
	/** 
	* @Title: delOfferInstDetail 
    * @Description: ��������Ʒ��ϸ��ʶ������Ʒʵ����ʶɾ������Ʒʵ�����ɣ���������ڣ�����null�����򷵻�ɾ�����
    * @param sProductOfferInstanceId  ����Ʒʵ����ʶ
    * @param sOfferDetailId  ����Ʒ���ɱ�ʶ
	* @return OfferInstDetail    
	* @throws 
	*/ 
	public OfferInstDetail delOfferInstDetail(String sproduct_offer_instance_id, String soffer_detail_id) {
		OfferInstDetail offdet = getOfferInstDetail(sproduct_offer_instance_id, soffer_detail_id);
		
		if (null != offdet)
		{
			this.offerInstDetails.remove(offdet);
			return offdet;
		}

		return null;
	}

    /** ******************���������ѡ��ʵ����������************************** */
	/** 
	* @Title: getOfferInsts 
	* @Description: ȡ���������ѡ��ʵ��
	* @return List    List of Map
	* @throws BusiException
	*/ 
	public List getOfferInsts() {		
		if (null == this.offerInsts)
		{
			this.offerInsts = new ArrayList();
			
			try {
				//��ǰ�������Ʒ��ʵ��ID�ǻ������Ϳ�ѡ���ĸ�����Ʒʵ����ʶ
				this.offerInsts = getOfferInstsByProdOffInstID(get(BusiTables.OFFER_INST.product_offer_instance_id));
			} catch (FrameException e) {
                e.printStackTrace();
                throw new BusiException(e);
			}			
		}
		
		return this.offerInsts;
	}

	public void setOfferInsts(List offerInsts) {
		this.offerInsts = offerInsts;
	}
	
	
	/** 
	* @Title: getOfferInst 
	* @Description: ��������Ʒʵ����ʶ��ȡ����Ʒʵ����Ϣ 
	* @param product_offer_instance_id ����Ʒʵ����ʶ
	* @return OfferInst    �������� 
	* @throws 
	*/ 
	public OfferInst getOfferInst(String sProductOfferInstanceID) {
		
		for (int i = 0; i < this.getOfferInsts().size(); i++)
		{
			OfferInst off = (OfferInst)this.offerInsts.get(i);
			
			if (sProductOfferInstanceID.equals(off.get(BusiTables.OFFER_INST.product_offer_instance_id)))
			{
				return off;
			}
		}
		
		return null;
	}
	
	/** 
	* @Title: delOfferInst 
    * @Description: ��������Ʒʵ����ʶ��ɾ������Ʒʵ����Ϣ 
    * @param product_offer_instance_id ����Ʒʵ����ʶ
    * @return OfferInst    
	* @throws 
	*/ 
	public OfferInst delOfferInst(String sProductOfferInstanceID) {
		OfferInst off = getOfferInst(sProductOfferInstanceID);
		
		if (null != off)
		{
			this.offerInsts.remove(off);
			return off;
		}

		return null;
	}
	
	
	
	/**
	 * ɾ����������Ʒ�����������Ϣ
	 */
	public void delAll(){
		List offerInsts = this.getOfferInsts();
		if(offerInsts!=null && offerInsts.size()!=0){
			for(int i =0;i<offerInsts.size();i++){
				OfferInst offerInst = (OfferInst)offerInsts.get(i);
				String offer_inst_id = offerInst.get(OFFER_INST.product_offer_instance_id);
				this.removeOfferInst(offer_inst_id);//ɾ��ʵ������
				this.removeOfferInstDetail(offer_inst_id);//ɾ����ϸ����
			}
		}
	}
	
	//���������ѡ��
	/** 
	* @Title: addOfferInst 
    * @Description: ��������Ʒʵ����ʶ����������Ʒʵ����Ϣ 
    * @param product_offer_instance_id ����Ʒʵ����ʶ
    * @return OfferInst    
	* @throws 
	*/ 
	public OfferInst addOfferInst(String product_offer_id) {
		this.getOfferInsts();
		OfferInst off = null;
		
		for (int i = 0; i < this.getOfferInsts().size(); i++)
		{
			off = (OfferInst)this.getOfferInsts().get(i);
			
			if (product_offer_id.equals(off.get(BusiTables.OFFER_INST.product_offer_id)))
			{
				return off;
			}
		}
		
		//û�о͹���һ����������Ʒ
		off = new OfferInst();
		
		off.set(BusiTables.OFFER_INST.product_offer_id, product_offer_id);
		off.set(BusiTables.OFFER_INST.comp_inst_id, get(BusiTables.OFFER_INST.product_offer_instance_id)); //������Ʒʵ����ʶΪ��ǰʵ��ID		
		off.set(BusiTables.OFFER_INST.product_offer_instance_id, getSeq());		
		off.set(BusiTables.OFFER_INST.cust_id, get(BusiTables.OFFER_INST.cust_id));			
		off.setActionType(KeyValues.ACTION_TYPE_A);
		
		offerInsts.add(off);
		
		return off;
	}
	
	/** 
	* @Title: addOfferInst 
    * @Description: ��������Ʒʵ����ʶ����������Ʒʵ����Ϣ 
    * @param offerInst ����Ʒʵ��
    * @return OfferInst    �������� 
	* @throws 
	*/ 
	public OfferInst addOfferInst(OfferInst offerInst) {
		this.getOfferInsts();
		
		this.offerInsts.add(offerInst);
		
		return offerInst;
	}
	
    
	//�Ƚ�����д������Ժ�Ǩ��
	/** 
	* @Title: getofferInstPartysByCompID 
	* @Description: ���ݸ�����Ʒʵ����ʶȡ����Ʒ��������Ϣ
	* @param @param scomp_inst_id ������ƷID
	* @param @throws FrameException    �趨�ļ� 
	* @return List    List of Map 
	* @throws 
	*/ 
	public List getofferInstPartysByCompID(String scomp_inst_id) throws FrameException  {
		OffInstPartyDAO dao = new OffInstPartyDAO();
		
		String whereCond = " and comp_inst_id = ? and sysdate between eff_date and exp_date";
		
		List params = new ArrayList();
		List qry_result = null;
		List result =  new ArrayList();
		
		params.add(scomp_inst_id);
		
		qry_result = dao.findByCond(whereCond, params);
		
		if (null != qry_result) {		
			for (int i = 0; i < qry_result.size(); i++)
			{
				//ȡ��ѯ���
				HashMap map = (HashMap)qry_result.get(i);
				
				OfferInstParty offparty = new OfferInstParty();
				
				offparty.loadFromMap(map);
				offparty.setActionType(KeyValues.ACTION_TYPE_K);
				
				result.add(offparty);
			}
		}
		
		return result;		
	}
	
    
	/** 
	* @Title: getOfferInstDetailsByProOffInstID 
	* @Description: ��������Ʒʵ����ʶȡ����Ʒʵ��������Ϣ
	* @param @param sproduct_offer_instance_id ����Ʒʵ����ʶ
	* @return List    List of Map
	* @throws FrameException
	*/ 
	public List getOfferInstDetailsByProOffInstID(String sProductOfferInstanceID) throws FrameException {
		OffInstDetaDAO dao = new OffInstDetaDAO();
		
		String whereCond = " and product_offer_instance_id = ? and sysdate between eff_date and exp_date ";
		List params = new ArrayList();
		List qry_result = null;
		List result =  new ArrayList();
		
		params.add(sProductOfferInstanceID);
		
		qry_result = dao.findByCond(whereCond, params);
		
		if (null != qry_result) {		
			for (int i = 0; i < qry_result.size(); i++) {
				HashMap map = (HashMap)qry_result.get(i);
				
				OfferInstDetail off = new OfferInstDetail();
				off.loadFromMap(map);
				off.setActionType(KeyValues.ACTION_TYPE_K);
				
				result.add(off);			
			}
		}
		
		return result;
	}
	
	/** 
	* @Title: getOfferInstsByProdOffInstID 
	* @Description: ���ݸ�����Ʒʵ����ʶȡ����Ʒʵ����Ϣ
	* @param @param sCompInstID ������Ʒʵ����ʶ
	* @return List    List of Map
	* @throws FrameException
	*/ 
	public List getOfferInstsByProdOffInstID(String sCompInstID) throws FrameException {
		OffInstDAO dao = new OffInstDAO();
		
		//��������Ʒ������Ʒʵ��ID = ������Ʒʵ��ID
		String whereCond = " and comp_inst_id = ? and sysdate between eff_date and exp_date ";
		List params = new ArrayList();
		List qry_result = null;
		List result = new ArrayList();
		
		params.add(sCompInstID);		
		
		qry_result = dao.findByCond(whereCond, params);
		
		if (null != qry_result) {		
			for (int i = 0; i < qry_result.size(); i++){
				HashMap map = (HashMap)qry_result.get(i);
				OfferInst off = new OfferInst();
				
				off.loadFromMap(map);
				off.setActionType(KeyValues.ACTION_TYPE_K);
				
				result.add(off);			
			}
		}
		
		return result;
	}	

	/** 
	* @Title: getOfferInstAssuresByID 
	* @Description: ���ݸ�����ƷIDȡ��������Ϣ
	* @param @param scomp_inst_id ������Ʒʵ��ID
	* @return List    List of Map
	* @throws FrameException
	*/ 
	public List getOfferInstAssuresByID(String scomp_inst_id) throws FrameException {
		OffInstAssureDAO dao = new OffInstAssureDAO();
		
		//��������Ʒ������Ʒʵ��ID = ������Ʒʵ��ID
		String whereCond = " and comp_inst_id = ? ";
		List params = new ArrayList();
		List qry_result = null;
		List result = new ArrayList();
		
		params.add(scomp_inst_id);		
		
		qry_result = dao.findByCond(whereCond, params);
		
		if (null != qry_result) {		
			for (int i = 0; i < qry_result.size(); i++){
				HashMap map = (HashMap)qry_result.get(i);
				OfferInst off = new OfferInst();
				
				off.loadFromMap(map);
				off.setActionType(KeyValues.ACTION_TYPE_K);
				
				result.add(off);			
			}
		}
		
		return result;
	}
	
    /** ******************����Ʒʵ�������˲�������************************** */
    //���ֹ���û��ʵ�֣������ڻ�Ҫ��һ������
	/** 
	* @Title: getOfferInstAssures 
	* @Description: ȡ����Ʒʵ����������Ϣ
	* @return List    List of Map
	* @throws BusiException
	*/ 
	public List getOfferInstAssures() {
		if (null == this.offerInstAssures) {
			this.offerInstAssures = new ArrayList();
			
			//����comp_inst_Idȡ��������Ϣ
			try {
				this.offerInstAssures = getOfferInstAssuresByID(get(BusiTables.OFFER_INST.comp_inst_id));
			} catch (FrameException e) {
                e.printStackTrace();
                throw new BusiException(e);
			}
		}
		
		return offerInstAssures;
	}
	
	
	
	public void setOfferInstAssures(List offerInstAssures) {
		this.offerInstAssures = offerInstAssures;
	}
	
	/** 
	* @Title: addOfferInstAssure 
	* @Description: ��������Ʒʵ��������Ϣ
	* @param offerInstAssure  ����Ʒʵ��������Ϣ
	* @return OfferInstAssure    ����Ʒʵ��������Ϣ
	* @throws ��
	*/ 
	public OfferInstAssure addOfferInstAssure(OfferInstAssure offerInstAssure) {
		this.getOfferInstAssures();
		
		this.offerInstAssures.add(offerInstAssure);
		
		return offerInstAssure;
	}
	
	/** 
	* @Title: getOfferInstConsts 
	* @Description: ȡ����Ʒʵ��������Ϣ����δʵ�֣�
	* @return List   List of Map
	* @throws 
	*/ 
	public List getOfferInstConsts() {
		if (null == offerInstContacts) {
			//WANJFTODO ��ʱ��ʵ��
		}
		
		return this.offerInstContacts;
	}
	
	public void setOfferInstContacts(List offerInstContact) {
		this.offerInstContacts = offerInstContact;
	}
	
    /** ******************ԤԼ��������************************** */
	/** 
	* @Title: getOrdBookDates 
	* @Description:  ȡԤԼ��Ϣ
	* @return List   List of Map
	* @throws BusiException
	*/ 
	public List getOrdBookDates() {
		if (null == this.ordBookDates) {			
			try {
				getOrdBookDatesByID(get(BusiTables.OFFER_INST.comp_inst_id));
			} catch (FrameException e) {
				e.printStackTrace();
                throw new BusiException(e);
			}			
		}
		
		return this.ordBookDates;
	}
	
	/** 
	* @Title: addOrdBookDate 
	* @Description: ����ԤԼ��Ϣ
	* @return OrdBookDate    ԤԼ��Ϣ
	* @throws ��
	*/ 
	public OrdBookDate addOrdBookDate(OrdBookDate ordBookDate) {
		this.getOrdBookDates();
		
		this.ordBookDates.add(ordBookDate);
		
		return ordBookDate;
	}
	
	/** 
	* @Title: getOrdBookDatesByID 
	* @Description: ���ݸ�����Ʒ��ʶȡԤԼ��Ϣ
	* @param @param sCompInstID ������Ʒ��ʶ
	* @return List   List of Map
	* @throws FrameException
	*/ 
	public List getOrdBookDatesByID(String sCompInstID) throws FrameException {
		OrdBookDateDAO dao = new OrdBookDateDAO();
		
		String whereCond = " and comp_inst_id = ? ";
		
		List params = new ArrayList();
		List qry_result = null;
		List result =  new ArrayList();
		
		params.add(sCompInstID);
		
		qry_result = dao.findByCond(whereCond, params);
		
		if (null != qry_result) {		
			for (int i = 0; i < qry_result.size(); i++)
			{
				//ȡ��ѯ���
				HashMap map = (HashMap)qry_result.get(i);
				
				OrdBookDate bookdate = new OrdBookDate();
				
				bookdate.loadFromMap(map);
				bookdate.set(BusiTables.OFFER_INST.action_type, KeyValues.ACTION_TYPE_K);
				
				result.add(bookdate);
			}
		}
		
		return result;		
		
	}	
	
    //ȡ����
	private String getSeq() {
		 return SeqUtil.getInst().getNext(Keys.TABLE_OFFER_INST, BusiTables.OFFER_INST.product_offer_instance_id);
	}
	
	
}
