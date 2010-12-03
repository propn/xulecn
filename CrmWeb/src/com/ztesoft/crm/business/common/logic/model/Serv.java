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

	public final static String COMP_INST_ID="comp_inst_id";//������Ʒʵ��ID

	public final static String ORD_ID="ord_id";//����ID
	
	public final static String PRODUCT_ID="product_id";//���
	
	public final static String SERV_ID="serv_id";//ʵ��
	
	public final static String ACTION_TYPE="action_type";//ҵ����
	// ������Ʒʵ��
	private List servProducts = null;


	// ��������Ϣ
	private List servAccts = null;

	// ����Ʒʵ��״̬
	private List servStates = null;

	// ����Ʒʵ�����Ӻ�����Ϣ
	private List servAccNbrs = null;

	// /����Ʒʵ���˵�������Ϣ
	private List servBillPosts = null;
	
	//��ǰ��Ʒ������������
	private  OrdItemInfo ordItemInfo = new OrdItemInfo();

	/***************************************************************************
	 * **********************************1.������Ʒ�Ĳ�������******************************
	 **************************************************************************/
	
	//�жϸ�����Ʒ�Ƿ�Ϊ��
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
		// ����product_id��servProducts���ҵ���Ӧ�����ݲ�����

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
	 * ����IDɾ��һ��serv_product
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
	 * ����serv_product_id�õ�serv_product����
	 * 
	 * @param serv_product_id
	 * @return
	 * @throws FrameException
	 */
	public ServProduct getServProductById(String serv_product_id)
			throws FrameException {
		ServProduct servProduct = null;
		servProducts = this.getServProducts();
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
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
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
		for (int i = 0; i < servProducts.size(); i++) {
			servProduct = (ServProduct) servProducts.get(i);
			if (product_id.equals(servProduct.get(Keys.PRODUCT_ID))) {
				return servProduct;
			}
		}

		return null;

	}

	/**
	 * ��ȡserv_product�����б�
	 * 
	 * @return
	 * @throws FrameException
	 */
	public List getServProducts() throws FrameException {
		if (this.servProducts == null) {
			this.servProducts = new ArrayList();
			// ����serv_id��serv_product���ּ�����Ӧ�����ݲ����õ�servProducts�С�
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
	 * **********************************3�����ƵĲ�������******************************
	 **************************************************************************/
	
	    
	//�ж������ϵ�Ƿ�Ϊ��
	public boolean isServAcctNull()
	{
		return this.servAccts == null;
	}
	
	public ServAcct addServAcct(String acct_item_group_id, String acct_id)
			throws FrameException {
		// List servAccts = getServAccts();
		ServAcct servAcct = getServAcct(acct_item_group_id);
        
		// ����product_id��servProducts���ҵ���Ӧ�����ݲ�����
		if (servAcct == null) {
			servAcct = new ServAcct();
			// �ʻ���ʶ
			servAcct.set(SERV_ACCT.acct_id, acct_id);
			servAcct.set(SERV_ACCT.acct_item_group_id, acct_item_group_id);
			servAcct.set(SERV_ACCT.action_type, KeyValues.ACTION_TYPE_A);
			servAcct.set(SERV_ACCT.serv_id, (String)this.attributes.get(this.SERV_ID));
			/*********���Դ��� ����ȡ������************/
			servAcct.set(SERV_ACCT.invoice_require_id, "11");
			servAcct.set(SERV_ACCT.bill_require_id, "22");
			servAcct.set(SERV_ACCT.self_flag, "0");//�Ƿ����绰 0�� 1��
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
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
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
			// ����serv_id��serv_product���ּ�����Ӧ�����ݲ����õ�servProducts�С�
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
	 * **********************************4��Ʒʵ״̬�Ĳ�������******************************
	 **************************************************************************/

	public ServState addServState(String stop_type) throws FrameException {
		// List servStates = getServStates();
		ServState servState = getServState(stop_type);
		// ����product_id��servProducts���ҵ���Ӧ�����ݲ�����

		if (servState == null) {
			servState = new ServState();
			// //ͣ������
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
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
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
			// ����serv_id��serv_product���ּ�����Ӧ�����ݲ����õ�servProducts�С�
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
	 * **********************************5�˵�Ͷ����Ϣ�Ĳ�������******************************
	 **************************************************************************/

	public ServBillPost addServBillPost(String billType) throws FrameException {

		ServBillPost servBillPost = getServBillPost(billType);
		// ����product_id��servProducts���ҵ���Ӧ�����ݲ�����

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
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
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
			// ����serv_id��serv_product���ּ�����Ӧ�����ݲ����õ�servProducts�С�
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
	 * **********************************6���Ӻ���Ĳ�������******************************
	 **************************************************************************/

	public ServAccNbr addServAccNbr(String acc_nbr_type) throws FrameException {
		ServAccNbr servAccNbr = getServAccNbr(acc_nbr_type);
		// ����product_id��servProducts���ҵ���Ӧ�����ݲ�����

		if (servAccNbr == null) {
			servAccNbr = new ServAccNbr();
			servAccNbr.set(BusiTables.SERV_ACC_NBR.acc_nbr_type_cd, acc_nbr_type);
			// �õ�����ƷID
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
		// ����serv_product_id��servProducts�ҵ���Ӧ��servProduct�����ء�
		// ����Ҳ�������ô����NULL;
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
			// ����serv_id��serv_product���ּ�����Ӧ�����ݲ����õ�servProducts�С�
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
	 * ****************************8����Ʒʵ�������˵Ĳ�������******************************
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
