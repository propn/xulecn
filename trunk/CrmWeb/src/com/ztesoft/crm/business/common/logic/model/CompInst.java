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
	
	public final static String COMP_INST_ID="comp_inst_id";//父销售品实例ID
	
	public final static String ACTION_TYPE="action_type";//业务动作
	
	public final static String ORD_ID="ord_id";//订单ID
	
	public final static String OFFER_ID="product_offer_id";//规格ID
	
	public final static String OFFER_KIND="offer_kind";//销售品类型
    
	//基础包和可选包实例
	private List offerInsts = null;	
	//基础包与可选包实例构成
	private List offerInstDetails = null;
	//销售品参与人信息
	private List offerInstPartys = null;
	//销售品实例联系人信息
	private List offerInstContacts = null;
	//销售品担保信息
	private List offerInstAssures = null;	
	//业务预约信息
	private List ordBookDates = null;	
	//业务办理原因
	private List ordRemoveCauses =null;
	
	//订单项总数据
	private  OrdItemInfo ordItemInfo = new OrdItemInfo();
	
	
	public OrdItemInfo getOrdItemInfo() {
		return ordItemInfo;
	}

	public void setOrdItemInfo(OrdItemInfo ordItemInfo) {
		this.ordItemInfo = ordItemInfo;
	}

	/** 
	* @Title: loadMainOfferInst 
	* @Description: 加载主销售品实例信息, 把主销售品实例的信息加载到compInst的attributes上
    *               使用前必须保证已经加载过销售品包
	* @param     无
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
    
    /** ******************销售品实例参与人的操作方法************************** */
	/** 
	* @Title: getOfferInstPartys 
	* @Description: 取销售品实例参与人信息，如果没有加载过，先从数据库加载信息 
	* @param   无 
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
	* @Description: 根据rel_id和rel_type取销售品实例参与人信息
	* @param srel_type 关系类型
	* @param srel_id   关系标识
	* @return OfferInstParty    没有找到返回null
	* @throws 无
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
	* @Description:根据rel_id和rel_type删除销售品实例参与人信息
    * @param srel_type 关系类型
    * @param srel_id   关系标识
	* @return OfferInstParty    已经删除的销售品实例参与人信息
	* @throws  无
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
	* @Description: 根据rel_id和rel_type查找销售品实例参与人信息，
    *               如果没有找到，创建一个新的销售品实例参与人信息。如果找到，返回找到的信息 
    * @param srel_type 关系类型
    * @param srel_id   关系标识
	* @return OfferInstParty    
	* @throws 无
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
	

    /** ******************基础包与可选包实例构成操作方法************************** */
	/** 
	* @Title: getOfferInstDetails 
	* @Description: 取组合销售品的所有实例构成
	* @param 无
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
	* @Description: 根据销售品明细标识和销售品实例标识查询销售品实例构成，如果不存在，新增销售品构成。否则返回查询结果
	* @param sProductOfferInstanceId  销售品实例标识
	* @param sOfferDetailId  销售品构成标识
	* @return OfferInstDetail   
	* @throws 无
	*/ 
	public OfferInstDetail addOfferInstDetail(String sProductOfferInstanceId, String sOfferDetailId) {		
		OfferInstDetail offdet = this.getOfferInstDetail(sProductOfferInstanceId, sOfferDetailId);
		
		if (null == offdet)
		{		
			//没有就构造一个新增销售品构成
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
	* @Description: 新增一个销售品实例构成，新增前，取一次所有销售品实例构成
	* @param offerInstDetail 待新增的销售品实例构成
	* @return OfferInstDetail  新增的销售品实例构成
	* @throws 无
	*/ 
	public OfferInstDetail addOfferInstDetail(OfferInstDetail offerInstDetail) {		
		this.getOfferInstDetails();
		
		this.offerInstDetails.add(offerInstDetail);
		
		return offerInstDetail;
	}	
	

	/** 
	* @Title: getOfferInstDetail 
    * @Description: 根据销售品明细标识和销售品实例标识查询销售品实例构成，如果不存在，返回null。否则返回查询结果
    * @param sProductOfferInstanceId  销售品实例标识
    * @param sOfferDetailId  销售品构成标识
	* @return OfferInstDetail   
	* @throws 无
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
    * @Description: 根据销售品实例标识查询销售品实例构成，如果不存在，返回null。否则返回查询结果
    * @param sProductOfferInstanceId  销售品实例标识
    * @param sOfferDetailId  销售品构成标识
	* @return OfferInstDetail   
	* @throws 无
	*/ 
	public OfferInstDetail removeOfferInstDetail(String sproduct_offer_instance_id) {		
		for (int i = 0; i < this.getOfferInstDetails().size(); i++)
		{
			OfferInstDetail offdet = (OfferInstDetail)this.offerInstDetails.get(i);
			if (sproduct_offer_instance_id.equals(offdet.get(OFFER_INST.product_offer_instance_id))){
				if(OfferConsts.ACTION_TYPE_A.equals(offdet.get(OFFER_INST.action_type))){//如果是新增 那么就直接删除 
					this.getOfferInstDetails().remove(i);
				}else{//如果是在用 需要把动作置成D
					offdet.setActionType(OfferConsts.ACTION_TYPE_D);
				}
				return offdet;
			}
		}
		
		return null;
	}
	
	/**
	 * 删除对应的销售品实例
	 * @param sproduct_offer_instance_id
	 * @return
	 */
	public OfferInst removeOfferInst(String sproduct_offer_instance_id) {		
		for (int i = 0; i < this.getOfferInsts().size(); i++){
			OfferInst offerInst = (OfferInst)this.offerInsts.get(i);
			if (sproduct_offer_instance_id.equals(offerInst.get(OFFER_INST.product_offer_instance_id))){
				if(OfferConsts.ACTION_TYPE_A.equals(offerInst.get(OFFER_INST.action_type))){//如果是新增 那么就直接删除 
					this.getOfferInsts().remove(i);
				}else{//如果是在用 需要把动作置成D
					offerInst.setActionType(OfferConsts.ACTION_TYPE_D);
				}
				return offerInst;
			}
		}
		
		return null;
	}
	
	//根据销售品明细标识删除指定销售品构成
	/** 
	* @Title: delOfferInstDetail 
    * @Description: 根据销售品明细标识和销售品实例标识删除销售品实例构成，如果不存在，返回null。否则返回删除结果
    * @param sProductOfferInstanceId  销售品实例标识
    * @param sOfferDetailId  销售品构成标识
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

    /** ******************基础包与可选包实例操作方法************************** */
	/** 
	* @Title: getOfferInsts 
	* @Description: 取基础包与可选包实例
	* @return List    List of Map
	* @throws BusiException
	*/ 
	public List getOfferInsts() {		
		if (null == this.offerInsts)
		{
			this.offerInsts = new ArrayList();
			
			try {
				//当前组合销售品的实例ID是基础包和可选包的父销售品实例标识
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
	* @Description: 根据销售品实例标识，取销售品实例信息 
	* @param product_offer_instance_id 销售品实例标识
	* @return OfferInst    返回类型 
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
    * @Description: 根据销售品实例标识，删除销售品实例信息 
    * @param product_offer_instance_id 销售品实例标识
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
	 * 删除该主销售品下所有相关信息
	 */
	public void delAll(){
		List offerInsts = this.getOfferInsts();
		if(offerInsts!=null && offerInsts.size()!=0){
			for(int i =0;i<offerInsts.size();i++){
				OfferInst offerInst = (OfferInst)offerInsts.get(i);
				String offer_inst_id = offerInst.get(OFFER_INST.product_offer_instance_id);
				this.removeOfferInst(offer_inst_id);//删除实例数据
				this.removeOfferInstDetail(offer_inst_id);//删除明细数据
			}
		}
	}
	
	//基础包与可选包
	/** 
	* @Title: addOfferInst 
    * @Description: 根据销售品实例标识，新增销售品实例信息 
    * @param product_offer_instance_id 销售品实例标识
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
		
		//没有就构造一个新增销售品
		off = new OfferInst();
		
		off.set(BusiTables.OFFER_INST.product_offer_id, product_offer_id);
		off.set(BusiTables.OFFER_INST.comp_inst_id, get(BusiTables.OFFER_INST.product_offer_instance_id)); //父销售品实例标识为当前实例ID		
		off.set(BusiTables.OFFER_INST.product_offer_instance_id, getSeq());		
		off.set(BusiTables.OFFER_INST.cust_id, get(BusiTables.OFFER_INST.cust_id));			
		off.setActionType(KeyValues.ACTION_TYPE_A);
		
		offerInsts.add(off);
		
		return off;
	}
	
	/** 
	* @Title: addOfferInst 
    * @Description: 根据销售品实例标识，新增销售品实例信息 
    * @param offerInst 销售品实例
    * @return OfferInst    返回类型 
	* @throws 
	*/ 
	public OfferInst addOfferInst(OfferInst offerInst) {
		this.getOfferInsts();
		
		this.offerInsts.add(offerInst);
		
		return offerInst;
	}
	
    
	//先将方法写在这里，以后迁移
	/** 
	* @Title: getofferInstPartysByCompID 
	* @Description: 根据父销售品实例标识取销售品参与人信息
	* @param @param scomp_inst_id 父销售品ID
	* @param @throws FrameException    设定文件 
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
				//取查询结果
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
	* @Description: 根据销售品实例标识取销售品实例构成信息
	* @param @param sproduct_offer_instance_id 销售品实例标识
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
	* @Description: 根据父销售品实例标识取销售品实例信息
	* @param @param sCompInstID 父销售品实例标识
	* @return List    List of Map
	* @throws FrameException
	*/ 
	public List getOfferInstsByProdOffInstID(String sCompInstID) throws FrameException {
		OffInstDAO dao = new OffInstDAO();
		
		//基础销售品的销售品实例ID = 父销售品实例ID
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
	* @Description: 根据父销售品ID取担保人信息
	* @param @param scomp_inst_id 父销售品实例ID
	* @return List    List of Map
	* @throws FrameException
	*/ 
	public List getOfferInstAssuresByID(String scomp_inst_id) throws FrameException {
		OffInstAssureDAO dao = new OffInstAssureDAO();
		
		//基础销售品的销售品实例ID = 父销售品实例ID
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
	
    /** ******************销售品实例担保人操作方法************************** */
    //部分功能没有实现，因现在还要进一步讨论
	/** 
	* @Title: getOfferInstAssures 
	* @Description: 取销售品实例担保人信息
	* @return List    List of Map
	* @throws BusiException
	*/ 
	public List getOfferInstAssures() {
		if (null == this.offerInstAssures) {
			this.offerInstAssures = new ArrayList();
			
			//根据comp_inst_Id取担保人信息
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
	* @Description: 新增销售品实例担保信息
	* @param offerInstAssure  销售品实例担保信息
	* @return OfferInstAssure    销售品实例担保信息
	* @throws 无
	*/ 
	public OfferInstAssure addOfferInstAssure(OfferInstAssure offerInstAssure) {
		this.getOfferInstAssures();
		
		this.offerInstAssures.add(offerInstAssure);
		
		return offerInstAssure;
	}
	
	/** 
	* @Title: getOfferInstConsts 
	* @Description: 取销售品实例担保信息（暂未实现）
	* @return List   List of Map
	* @throws 
	*/ 
	public List getOfferInstConsts() {
		if (null == offerInstContacts) {
			//WANJFTODO 暂时不实现
		}
		
		return this.offerInstContacts;
	}
	
	public void setOfferInstContacts(List offerInstContact) {
		this.offerInstContacts = offerInstContact;
	}
	
    /** ******************预约操作方法************************** */
	/** 
	* @Title: getOrdBookDates 
	* @Description:  取预约信息
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
	* @Description: 新增预约信息
	* @return OrdBookDate    预约信息
	* @throws 无
	*/ 
	public OrdBookDate addOrdBookDate(OrdBookDate ordBookDate) {
		this.getOrdBookDates();
		
		this.ordBookDates.add(ordBookDate);
		
		return ordBookDate;
	}
	
	/** 
	* @Title: getOrdBookDatesByID 
	* @Description: 根据父销售品标识取预约信息
	* @param @param sCompInstID 父销售品标识
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
				//取查询结果
				HashMap map = (HashMap)qry_result.get(i);
				
				OrdBookDate bookdate = new OrdBookDate();
				
				bookdate.loadFromMap(map);
				bookdate.set(BusiTables.OFFER_INST.action_type, KeyValues.ACTION_TYPE_K);
				
				result.add(bookdate);
			}
		}
		
		return result;		
		
	}	
	
    //取序列
	private String getSeq() {
		 return SeqUtil.getInst().getNext(Keys.TABLE_OFFER_INST, BusiTables.OFFER_INST.product_offer_instance_id);
	}
	
	
}
