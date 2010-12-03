package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV_ACCT;
import com.ztesoft.crm.business.common.inst.dao.BillPostDAO;
import com.ztesoft.crm.business.common.inst.dao.ServAccNbrDAO;
import com.ztesoft.crm.business.common.inst.dao.ServAcctDAO;
import com.ztesoft.crm.business.common.inst.dao.ServProductDAO;
import com.ztesoft.crm.business.common.inst.dao.ServStateDAO;
import com.ztesoft.crm.business.common.utils.SeqUtil;

public class Serv extends Inst {

	public final static String COMP_INST_ID="comp_inst_id";//父销售品实例ID

	public final static String ORD_ID="ord_id";//订单ID
	
	public final static String PRODUCT_ID="product_id";//规格
	
	public final static String SERV_ID="serv_id";//实例
	
	public final static String ACTION_TYPE="action_type";//业务动作
	// 附属产品实例
	private List servProducts = null;


	// 帐务定制信息
	private List servAccts = null;

	// 主产品实例状态
	private List servStates = null;

	// 主产品实例附加号码信息
	private List servAccNbrs = null;

	// /主产品实例账单定制信息
	private List servBillPosts = null;
	
	//当前产品订单项总数据
	private  OrdItemInfo ordItemInfo = new OrdItemInfo();

	/***************************************************************************
	 * **********************************1.附属产品的操作方法******************************
	 **************************************************************************/
	
	//判断附属产品是否为空
	public boolean isServProductNull()
	{
		return this.servProducts == null;
	}

	
	/**
	 * 
	 * @param product_id
	 * @return
	 * @throws FrameException
	 * @throws FrameException
	 */
	public ServProduct addServProduct(String product_id) throws FrameException {

		ServProduct servProduct = getServProductById(product_id);
		// 根据product_id从servProducts中找到相应的数据并返回

		if (servProduct == null) {
			servProduct = new ServProduct();
			servProduct.set(Keys.SERV_PRODUCT_ID, SeqUtil.getInst().getNext(
					"SERV_PRODUCT", "SERV_PRODUCT_ID"));
			servProduct.set(BusiTables.SERV_PRODUCT.action_type, KeyValues.ACTION_TYPE_A);
			servProduct.set(BusiTables.SERV_PRODUCT.product_id, product_id);
			servProducts.add(servProduct);
		}

		return servProduct;
	}

	public ServProduct addServproduct(ServProduct servproduct)
			throws FrameException {
		servProducts = this.getServProducts();
		servProducts.add(servproduct);
		return servproduct;
	}

	/**
	 * 根据ID删除一个serv_product
	 * 
	 * @param serv_product_id
	 * @return
	 * @throws FrameException
	 */
	public ServProduct delServProductByID(String serv_product_id)
			throws FrameException {
		ServProduct servProduct = getServProductById(serv_product_id);
		if (servProduct != null) {
			this.getServProducts().remove(servProduct);
			return servProduct;
		} else
			return null;
	}

	public ServProduct delServProduct(String product_id) throws FrameException {
		ServProduct servProduct = getServProductById(product_id);
		if (servProduct != null) {
			this.getServProducts().remove(servProduct);
			return servProduct;
		} else
			return null;
	}

	/**
	 * 根据serv_product_id得到serv_product对象
	 * 
	 * @param serv_product_id
	 * @return
	 * @throws FrameException
	 */
	public ServProduct getServProductById(String serv_product_id)
			throws FrameException {
		ServProduct servProduct = null;
		servProducts = this.getServProducts();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servProducts.size(); i++) {
			servProduct = (ServProduct) servProducts.get(i);
			if (serv_product_id.equals(servProduct.get(Keys.PRODUCT_ID))) {

				return servProduct;
			}
		}

		return null;

	}

	public ServProduct getServProduct(String product_id) throws FrameException {
		ServProduct servProduct = null;
		servProducts = this.getServProducts();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servProducts.size(); i++) {
			servProduct = (ServProduct) servProducts.get(i);
			if (product_id.equals(servProduct.get(Keys.PRODUCT_ID))) {
				return servProduct;
			}
		}

		return null;

	}

	/**
	 * 获取serv_product对象列表
	 * 
	 * @return
	 * @throws FrameException
	 */
	public List getServProducts() throws FrameException {
		if (this.servProducts == null) {
			this.servProducts = new ArrayList();
			// 根据serv_id从serv_product表种加载相应的数据并设置到servProducts中。
			ServProductDAO dao = new ServProductDAO();
			List param = new ArrayList();
			param.add(get(BusiTables.SERV.serv_id));
			String sql = " and serv_id=?";
			List result = dao.findByCond(sql, param);
			for (int i = 0; i < result.size(); i++) {
				HashMap map = (HashMap) result.get(i);

				ServProduct serp = new ServProduct();
				serp.loadFromMap(map);
				serp.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_K);
				servProducts.add(serp);
			}
			return servProducts;
		} else
			return servProducts;

	}

	/***************************************************************************
	 * **********************************3帐务定制的操作方法******************************
	 **************************************************************************/
	
	    
	//判断账务关系是否为空
	public boolean isServAcctNull()
	{
		return this.servAccts == null;
	}
	
	public ServAcct addServAcct(String acct_item_group_id, String acct_id)
			throws FrameException {
		// List servAccts = getServAccts();
		ServAcct servAcct = getServAcct(acct_item_group_id);
        
		// 根据product_id从servProducts中找到相应的数据并返回
		if (servAcct == null) {
			servAcct = new ServAcct();
			// 帐户标识
			servAcct.set(SERV_ACCT.acct_id, acct_id);
			servAcct.set(SERV_ACCT.acct_item_group_id, acct_item_group_id);
			servAcct.set(SERV_ACCT.action_type, KeyValues.ACTION_TYPE_A);
			servAcct.set(SERV_ACCT.serv_id, (String)this.attributes.get(this.SERV_ID));
			/*********测试代码 具体取法待定************/
			servAcct.set(SERV_ACCT.invoice_require_id, "11");
			servAcct.set(SERV_ACCT.bill_require_id, "22");
			servAcct.set(SERV_ACCT.self_flag, "0");//是否代表电话 0否 1是
			servAccts.add(servAcct);
		}

		return servAcct;

	}

	public ServAcct addServAcct(ServAcct servAcct) throws FrameException {
		servAccts = this.getServAccts();
		servAccts.add(servAcct);
		return servAcct;
	}

	public ServAcct delServAcct(String acct_item_group_id)
			throws FrameException {
		ServAcct servAcct = getServAcct(acct_item_group_id);
		if (servAcct != null) {
			this.getServAccts().remove(servAcct);
			return servAcct;
		} else
			return null;

	}

	public ServAcct getServAcct(String acct_item_group_id)
			throws FrameException {
		ServAcct servAcct = null;
		servAccts = this.getServAccts();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servAccts.size(); i++) {
			servAcct = (ServAcct) servAccts.get(i);
			if (acct_item_group_id
					.equals(servAcct.get(BusiTables.SERV_ACCT.acct_item_group_id))) {

				return servAcct;
			}
		}

		return null;
	}

	public List getServAccts() throws FrameException {
		if (this.servAccts == null) {
			this.servAccts = new ArrayList();
			// 根据serv_id从serv_product表种加载相应的数据并设置到servProducts中。
			ServAcctDAO dao = new ServAcctDAO();
			List param = new ArrayList();
			param.add(get(BusiTables.SERV.serv_id));
			String sql = " and serv_id=?";
			List result = dao.findByCond(sql, param);
			for (int i = 0; i < result.size(); i++) {
				HashMap map = (HashMap) result.get(i);
				ServAcct serp = new ServAcct();
				serp.loadFromMap(map);
				serp.set(BusiTables.SERV_ACCT.action_type, KeyValues.ACTION_TYPE_K);
				servAccts.add(serp);
			}
			return servAccts;
		} else
			return servAccts;
	}

	/***************************************************************************
	 * **********************************4产品实状态的操作方法******************************
	 **************************************************************************/

	public ServState addServState(String stop_type) throws FrameException {
		// List servStates = getServStates();
		ServState servState = getServState(stop_type);
		// 根据product_id从servProducts中找到相应的数据并返回

		if (servState == null) {
			servState = new ServState();
			// //停机类型
			servState.set(BusiTables.SERV_STATE.stop_type, stop_type);
			servState.set(BusiTables.SERV_STATE.action_type, KeyValues.ACTION_TYPE_A);
            
			servStates.add(servState);
		}

		return servState;
	}

	public ServState addServState(ServState servState) throws FrameException {
		servStates = this.getServStates();
		servStates.add(servState);
		return servState;
	}

	public ServState delServState(String stop_type) throws FrameException {
		ServState servState = getServState(stop_type);
		if (servState != null) {
			this.getServStates().remove(servState);
			return servState;
		} else
			return null;
	}

	public ServState getServState(String stop_type) throws FrameException {
		ServState servState = null;
		servStates = this.getServStates();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servStates.size(); i++) {
			servState = (ServState) servStates.get(i);
			if (stop_type.equals(servState.get(BusiTables.SERV_STATE.stop_type))) {
				return servState;
			}
		}

		return null;
	}

	public List getServStates() throws FrameException {
		if (this.servStates == null) {
			this.servStates = new ArrayList();
			// 根据serv_id从serv_product表种加载相应的数据并设置到servProducts中。
			ServStateDAO dao = new ServStateDAO();
			List param = new ArrayList();
			param.add(get(BusiTables.SERV.serv_id));
			String sql = " and serv_id=?";
			List result = dao.findByCond(sql, param);
			for (int i = 0; i < result.size(); i++) {
				HashMap map = (HashMap) result.get(i);
				ServState serp = new ServState();
				serp.loadFromMap(map);
				servStates.add(serp);
			}
			return servStates;
		} else
			return servStates;
	}

	/***************************************************************************
	 * **********************************5账单投递信息的操作方法******************************
	 **************************************************************************/

	public ServBillPost addServBillPost(String billType) throws FrameException {

		ServBillPost servBillPost = getServBillPost(billType);
		// 根据product_id从servProducts中找到相应的数据并返回

		if (servBillPost == null) {
			servBillPost = new ServBillPost();
			servBillPost.set(BusiTables.BILL_POST.bill_type, billType);
			servBillPost.set(BusiTables.BILL_POST.action_type, KeyValues.ACTION_TYPE_A);
			servBillPosts.add(servBillPost);
		}

		return servBillPost;
	}

	public ServBillPost addServBillPost(ServBillPost servBillPost)
			throws FrameException {
		servBillPosts = this.getServBillPosts();
		servBillPosts.add(servBillPost);
		return servBillPost;
	}

	public ServBillPost delServBillPost(String billType) throws FrameException {
		ServBillPost servBillPost = getServBillPost(billType);
		if (servBillPost != null) {
			this.getServBillPosts().remove(servBillPost);
			return servBillPost;
		} else
			return null;
	}

	public ServBillPost getServBillPost(String billType) throws FrameException {
		ServBillPost servBillPost = null;
		servBillPosts = this.getServBillPosts();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servBillPosts.size(); i++) {
			servBillPost = (ServBillPost) servBillPosts.get(i);
			if (billType.equals(servBillPost.get(BusiTables.BILL_POST.bill_type))) {

				return servBillPost;
			}
		}

		return null;
	}

	public List getServBillPosts() throws FrameException {
		if (this.servBillPosts == null) {
			this.servBillPosts = new ArrayList();
			// 根据serv_id从serv_product表种加载相应的数据并设置到servProducts中。
			BillPostDAO dao = new BillPostDAO();
			List param = new ArrayList();
			param.add(get(BusiTables.SERV.serv_id));
			String sql = " and serv_id=?";
			List result = dao.findByCond(sql, param);
			for (int i = 0; i < result.size(); i++) {
				HashMap map = (HashMap) result.get(i);
				ServBillPost serp = new ServBillPost();
				serp.loadFromMap(map);
				serp.set(BusiTables.BILL_POST.action_type, KeyValues.ACTION_TYPE_K);
				servBillPosts.add(serp);
			}
			return servBillPosts;
		} else
			return servBillPosts;
	}

	/***************************************************************************
	 * **********************************6附加号码的操作方法******************************
	 **************************************************************************/

	public ServAccNbr addServAccNbr(String acc_nbr_type) throws FrameException {
		ServAccNbr servAccNbr = getServAccNbr(acc_nbr_type);
		// 根据product_id从servProducts中找到相应的数据并返回

		if (servAccNbr == null) {
			servAccNbr = new ServAccNbr();
			servAccNbr.set(BusiTables.SERV_ACC_NBR.acc_nbr_type_cd, acc_nbr_type);
			// 得到主产品ID
			// servAccNbr.setServ_id(this.getServ_id());
			servAccNbr.set(BusiTables.SERV_ACC_NBR.action_type, KeyValues.ACTION_TYPE_A);
			servBillPosts.add(servAccNbr);
		}

		return servAccNbr;
	}

	public ServAccNbr addServAccNbr(ServAccNbr servAccNbr)
			throws FrameException {
		servAccNbrs = this.getServAccNbrs();
		servAccNbrs.add(servAccNbr);
		return servAccNbr;
	}

	public ServAccNbr delServAccNbr(String acc_nbr_type) throws FrameException {
		ServAccNbr servAccNbr = getServAccNbr(acc_nbr_type);
		if (servAccNbr != null) {
			this.getServAccNbrs().remove(servAccNbr);
			return servAccNbr;
		} else
			return null;
	}

	public ServAccNbr getServAccNbr(String acc_nbr_type) throws FrameException {
		ServAccNbr servAccNbr = null;
		servAccNbrs = this.getServAccNbrs();
		// 根据serv_product_id从servProducts找到相应的servProduct并返回。
		// 如果找不到，那么返回NULL;
		for (int i = 0; i < servAccNbrs.size(); i++) {
			servAccNbr = (ServAccNbr) servAccNbrs.get(i);
			if (acc_nbr_type.equals(servAccNbr.get(BusiTables.SERV_ACC_NBR.acc_nbr_type_cd))) {

				return servAccNbr;
			}
		}

		return null;
	}

	public List getServAccNbrs() throws FrameException {
		if (this.servAccNbrs == null) {
			this.servAccNbrs = new ArrayList();
			// 根据serv_id从serv_product表种加载相应的数据并设置到servProducts中。
			ServAccNbrDAO dao = new ServAccNbrDAO();
			List param = new ArrayList();
			param.add(get(BusiTables.SERV.serv_id));
			String sql = " and serv_id=?";
			List result = dao.findByCond(sql, param);
			for (int i = 0; i < result.size(); i++) {
				HashMap map = (HashMap) result.get(i);
				ServAccNbr serp = new ServAccNbr();
				serp.loadFromMap(map);
				serp.set(BusiTables.SERV_ACC_NBR.action_type, KeyValues.ACTION_TYPE_K);
				servAccNbrs.add(serp);
			}
			return servAccNbrs;
		} else
			return servAccNbrs;
	}

	/***************************************************************************
	 * ****************************8销售品实例担保人的操作方法******************************
	 **************************************************************************/
	public void setServProducts(List servProducts) {

		this.servProducts = servProducts;
	}
	
	public void setServStates(List servStates) {
		this.servStates = servStates;
	}

	public void setservAccts(List servAccts) {
		this.servAccts = servAccts;
	}

	public void setservAccNbrs(List servAccNbrs) {
		this.servAccNbrs = servAccNbrs;
	}

	public void setservBillPosts(List servBillPosts) {
		this.servBillPosts = servBillPosts;
	}

	public OrdItemInfo getOrdItemInfo() {
		return ordItemInfo;
	}

	public void setOrdItemInfo(OrdItemInfo ordItemInfo) {
		this.ordItemInfo = ordItemInfo;
	}

}
