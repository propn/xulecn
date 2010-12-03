/**   
* @Title: DateChange.java 
* @Package com.ztesoft.crm.business.common.logic.model 
* @Description: 用于前后台数据转换
* @author wanjf
* @date 2010-1-25 上午08:58:08 
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
 * @Description: 用于前后台数据转换 
 * @author wanjf
 * @date 2010-1-25 上午08:58:08 
 *  
 */
public class DataChange {
	
	/** 
	* @Title: getServsFromReq 
	* @Description: 取主产品实例列表信息
	* @param @param vacceptrequest 前台传入参数
	* @param @return  里面放Serv对象
	* @return List    返回类型 
	* @throws null
	*/ 
	public List getServsFromReq(VAcceptRequest vacceptrequest) {
		List servs = new ArrayList();
		
		//入参为空
		if (null == vacceptrequest) {
			return servs;
		}
		
		List vServers = vacceptrequest.getServs();
		
		//入参的主产品列表为空
		if (null == vServers) {
			return servs;
		}		
		
		//将前台数据逐个抽取到Serv中
		for (int i = 0; i < vServers.size(); i++) {
			Serv serv = new Serv();
			servs.add(serv);
			
			VServ vserv = (VServ)vServers.get(i);
			
			//复制主产品属性
			serv.loadFromMap(vserv.getServ_attrs());
			
			//抽取主产品属性列表中的属性
			getServAttrList(vacceptrequest, serv);
			
			//抽取附属产品信息
			getProductList(vacceptrequest, vserv, serv);
			
			//抽取帐务定制数据
			getservAcctList(vacceptrequest, vserv, serv);
			
			//抽取主产品实例状态数据
			getservstateList(vacceptrequest, vserv, serv);
			
			//抽取主产品实例附加号码信息
			getservaccnbrList(vacceptrequest, vserv, serv);

  			//抽取主产品实例账单投递信息
			getservbillpostList(vacceptrequest, vserv, serv);
		}
		
		return servs;
	}
	
	/** 
	* @Title: getCompInstsFromReq 
	* @Description: 取销售品实例列表信息
	* @param @param vacceptrequest 前台传入对象
	* @param @return    list里面放置CompInst对象
	* @return List    返回类型 
	* @throws 无
	*/ 
	public List getCompInstsFromReq(VAcceptRequest vacceptrequest) {
		List compinsts = new ArrayList();
		
		//入参为空
		if (null == vacceptrequest) {
			return compinsts;
		}
		
		List vofferInsts = vacceptrequest.getOfferInsts();
		
		//入参的取销售品实例列表为空
		if (null == vofferInsts || vofferInsts.isEmpty()) {
			return compinsts;
		}		
		
		while (0 != vofferInsts.size()) {
			CompInst compinst = new CompInst();
			compinsts.add(compinst);
			
			//1 抽取基础包和可选包信息
			Map offerinstmap = (Map)vofferInsts.get(0);
			
  			//取同一销售品信息
			compinst.setOfferInsts(getOfferInstList(vacceptrequest, offerinstmap));
			
			//取销售品构成信息
			compinst.setOfferInstDetails(getOfferInstDetailList(vacceptrequest, offerinstmap));
			
			//销售品参与人信息
			compinst.setOfferInstPartys(getofferInstPartyList(vacceptrequest, offerinstmap));
			
			//销售品担保信息
			compinst.setOfferInstAssures(getofferInstAssureList(vacceptrequest, offerinstmap));
			
			compinst.loadMainOfferInst();			
		}		
		
		return compinsts;
	}
	
	
	//抽取产品属性信息
	private void getServAttrList(VAcceptRequest vacceptrequest, Serv serv) {		
		if(null != vacceptrequest.getServAttrs() && !vacceptrequest.getServAttrs().isEmpty()) {  //如果前台有加载产品属性
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List vservattrs = vacceptrequest.getServAttrs();			
			
			//取同一serv_id的数据
			List myvservattrs = UtilTools.findAndRemove(serv.getAttributes(), paramkey, vservattrs); 
			
			//将产品属性纵表信息转为横表
			serv.loadFromList(myvservattrs);
		}
	}

	//抽取附属产品信息
	private void getProductList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_product_loaded_flag())) {  //如果前台有加载附属产品
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servproductlist = new ArrayList();
			List vservproducts = vacceptrequest.getServProducts();			
			
			List myvservproducts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservproducts); //取同一serv_id的数据
			
			for (int j = 0; j < myvservproducts.size(); j++) {
				Map product = (Map)myvservproducts.get(j);
				
				ServProduct oneservproduct = new ServProduct();
				oneservproduct.loadFromMap(product);
				
				//抽取附属产品属性信息(由纵表转横表)
				getServProductAttrList(vacceptrequest, oneservproduct);
				
				servproductlist.add(oneservproduct);
			}
			
			serv.setServProducts(servproductlist);
		}
	}
	
	//抽取附属产品属性信息
	private void getServProductAttrList(VAcceptRequest vacceptrequest, ServProduct servproduct) {		
		if(null != vacceptrequest.getServProductAttrs() && !vacceptrequest.getServProductAttrs().isEmpty()) {  //如果前台有加载附属产品属性
			//按附属产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_PRODUCT_ID};
			
			List vservproductattrs = vacceptrequest.getServProductAttrs();			
			
			//取同一serv_id的数据
			List myvservproductattrs = UtilTools.findAndRemove(servproduct.getAttributes(), paramkey, vservproductattrs); 
			
			//将产品属性纵表信息转为横表
			servproduct.loadFromList(myvservproductattrs);
		}
	}
	
  	//抽取帐务定制信息
	private void getservAcctList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acct_loaded_flag())) {  //如果前台有加载附属产品
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servacctlist = new ArrayList();
			List vservacctlist = vacceptrequest.getServAccts();		
			
			List myvservaccts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservacctlist); //取同一serv_id的数据
			
			for (int j = 0; j < myvservaccts.size(); j++) {
				Map vservacct = (Map)myvservaccts.get(j);
				
				ServAcct oneservacct = new ServAcct();
				oneservacct.loadFromMap(vservacct);
				
				servacctlist.add(oneservacct);
			}
			
			serv.setservAccts(servacctlist);
		}
	}
	
	
  	//抽取主产品实例状态数据
	private void getservstateList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_state_loaded_flag())) {  //如果前台有加载主产品实例状态数据
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servstatelist = new ArrayList();
			List vservstatelist = vacceptrequest.getServStates();
			
			List myvservstates = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservstatelist); //取同一serv_id的数据
			
			for (int j = 0; j < myvservstates.size(); j++) {
				Map vservstate = (Map)myvservstates.get(j);
				
				ServState oneservstate = new ServState();
				oneservstate.loadFromMap(vservstate);
				
				servstatelist.add(oneservstate);
			}
			
			serv.setServStates(servstatelist);
		}
	}
	
    //抽取主产品实例附加号码信息
	private void getservaccnbrList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acc_nbr_loaded_falg())) {  //如果前台有加载主产品实例附加号码信息
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servaccnbrlist = new ArrayList();
			List vservaccnbrlist = vacceptrequest.getServStates();
			
			List myvservaccnbrs = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservaccnbrlist); //取同一serv_id的数据
			
			for (int j = 0; j < myvservaccnbrs.size(); j++) {
				Map map = (Map)myvservaccnbrs.get(j);
				
				ServAccNbr oneservaccnbr = new ServAccNbr();
				oneservaccnbr.loadFromMap(map);
				
				servaccnbrlist.add(oneservaccnbr);
			}
			
			serv.setservAccNbrs(servaccnbrlist);
		}
	}

 	//抽取主产品实例账单投递信息
	private void getservbillpostList(VAcceptRequest vacceptrequest, VServ vserv, Serv serv) {		
		if(KeyValues.IFTRUE_T.equals(vserv.getServ_acc_nbr_loaded_falg())) {  //如果前台有加载主产品实例账单定制信息
			//按主产品ID抽取
			String[] paramkey = new String[]{Keys.SERV_ID};
			
			List servbillpostlist = new ArrayList();
			List vservbillpostlist = vacceptrequest.getServBillPosts();			
			
			List myvservbillposts = UtilTools.findAndRemove(vserv.getServ_attrs(), paramkey, vservbillpostlist); //取同一serv_id的数据
			
			for (int j = 0; j < myvservbillposts.size(); j++) {
				Map map = (Map)myvservbillposts.get(j);
				
				ServBillPost oneservbillpost = new ServBillPost();
				oneservbillpost.loadFromMap(map);
				
				servbillpostlist.add(oneservbillpost);
			}
			
			serv.setservBillPosts(servbillpostlist);
		}
	}

    //抽取销售品的基础包可选包信息
	private List getOfferInstList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInsts = vacceptrequest.getOfferInsts();
		List outofferInsts = new ArrayList();
		//取同一销售品信息
		List vMyOfferInsts = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInsts);
		
		//逐条复制销售品信息，同时抽取销售品属性信息
		for (int i = 0; i < vMyOfferInsts.size(); i++) {
			Map map = (Map)vMyOfferInsts.get(i);
			
			OfferInst off = new OfferInst();			
			off.loadFromMap(map);
			
			//抽取附属产品属性信息
			getOfferInstAttrList(vacceptrequest, off);
			
			outofferInsts.add(off);			
		}
		
		return outofferInsts;
	}
	
	//抽取附属产品属性信息
	private void getOfferInstAttrList(VAcceptRequest vacceptrequest, OfferInst offinst) {		
		if(null != vacceptrequest.getOfferInstAttrs() && !vacceptrequest.getOfferInstAttrs().isEmpty()) {  //如果前台有加载附属产品属性
			//按附属产品ID抽取
			String[] paramkey = new String[]{Keys.PRODUCT_OFFER_INSTANCE_ID};
			
			List vofferinstattrs = vacceptrequest.getOfferInstAttrs();			
			
			//取同一serv_id的数据
			List myvofferinstattrs = UtilTools.findAndRemove(offinst.getAttributes(), paramkey, vofferinstattrs); 
			
			//将产品属性纵表信息转为横表
			offinst.loadFromList(myvofferinstattrs);
		}
	}
	
	//抽取销售品构成明细信息
	private List getOfferInstDetailList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstDetails = vacceptrequest.getOfferInstDetails();
		List outofferInstDetails = new ArrayList();
		//取同一父销售品信息
		List vMyOfferInstDetails = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstDetails);
		
		for (int i = 0; i < vMyOfferInstDetails.size(); i++) {
			Map map = (Map)vMyOfferInstDetails.get(i);
			
			OfferInstDetail off = new OfferInstDetail();
			
			off.loadFromMap(map);
			
			//判断是否有eff_date，如果没有就补一个
			if ("".equals(Const.getStrValue(map, "eff_date"))) {
				off.set("eff_date", DateFormatUtils.getFormatedDateTime());
			}
			
			outofferInstDetails.add(off);			
		}
		
		return outofferInstDetails;
	}
	
  	//抽取销售品参与人信息 
	private List getofferInstPartyList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstPartys = vacceptrequest.getOfferInstParties();
		List outofferInstPartys = new ArrayList();
		//销售品参与人信息
		List vMyofferInstPartys = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstPartys);
		
		//map to 对象
		for (int i = 0; i < vMyofferInstPartys.size(); i++) {
			Map map = (Map)vMyofferInstPartys.get(i);
			
			OfferInstParty off = new OfferInstParty();
			
			off.loadFromMap(map);
			outofferInstPartys.add(off);			
		}
		
		return outofferInstPartys;
	}
	
	//抽取销售品担保信息
	private List getofferInstAssureList(VAcceptRequest vacceptrequest, Map offerinstmap) {	
		List vofferInstAssures = vacceptrequest.getOfferInstAssures();
		List outofferInstAssures = new ArrayList();
		//销售品参与人信息
		List vMyofferInstPartys = UtilTools.findAndRemove(offerinstmap, new String[]{Keys.COMP_INST_ID}, vofferInstAssures);
		
		//map to 对象
		for (int i = 0; i < vMyofferInstPartys.size(); i++) {
			Map map = (Map)vMyofferInstPartys.get(i);
			
			OfferInstParty off = new OfferInstParty();
			
			off.loadFromMap(map);
			outofferInstAssures.add(off);			
		}
		
		return outofferInstAssures;
	}
}
