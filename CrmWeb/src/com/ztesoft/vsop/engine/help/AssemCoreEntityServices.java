package com.ztesoft.vsop.engine.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdOffVO;
import com.ztesoft.vsop.web.vo.ProdVO;

/**
 * 用来装配和转换核心对象
 * @author yuanyang
 *
 */
public class AssemCoreEntityServices {
	public static AssemCoreEntityServices instance = new AssemCoreEntityServices();
	
	
	public AssemCoreEntityServices() {
	}
	public static AssemCoreEntityServices getInstance(){
		return instance;
	}
	/**
	 * 根据销售品ID获得其对应订单增值产品信息
	 * 此订单的增值产品动作OrderConstant.orderTypeOfAdd
	 * 此订单的增值产品状态为OrderConstant.orderStateOfCreated
	 * @param prodOfferIds
	 *            销售品ID数组
	 * @return HashMap key--销售品ID,value--与此销售品ID对应订单增值产品列表
	 */
	public Map getProductVOByProdOfferIds(String[] prodofferIdsArry) {
		Map retMap = new HashMap();
		List prodOfferVOList = new ArrayList();
		List vProductIds = new ArrayList();
		List subVProducts = null;
		for (int i = 0; i < prodofferIdsArry.length; i++) {
			prodOfferVOList.add(DcSystemParamManager.getInstance().getProdOffVOById(prodofferIdsArry[i]));
		}
		
		SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
		// 把销售品下面的所有产品加进来
		for (Iterator iterator = prodOfferVOList.iterator(); iterator.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iterator.next();
			vProductIds = DcSystemParamManager.getInstance().getVproductIdsByOfferIdWithAllState(prodOffVO.getProdOffId());
			subVProducts = new ArrayList();
			if(vProductIds!=null){
				//把增值产品转换成订单增值产品加进来，同时,新增订单增值产品里的订购关系实例标识
				for (Iterator iterator1 = vProductIds.iterator(); iterator1.hasNext();) {
					String seq = aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
					ProdVO prodVO=DcSystemParamManager.getInstance().getProductVOByid((String) iterator1.next());
					subVProducts.add(prodVO.toVproductInfo(prodOffVO,OrderConstant.orderTypeOfAdd,OrderConstant.orderStateOfCreated,seq));
				}
			}
			retMap.put(prodOffVO.getProdOffId(), subVProducts);
		}
		return retMap;
	}
	/**
	 * 江西：网厅、10000坐席，短厅、10000语言系统入参是产品大类，需要转换
	 * 江西号码传递规则：除了手机，号码字段传送4位区号＋号码
	 * @param order
	 * @throws SQLException
	 */
	public void resetProdInstInfo(CustomerOrder order) throws SQLException {
		String origProdSpecCode = order.getProdId(); //原来的产品类型，产品大类
		String systemCode = order.getOrderSystem();  //外系统编码
		String lanCodeTmp = null;
		String productNoTmp = null;
		ProdInstVO prodInstvo = null;
		if(SystemCode.CT10000.equals(systemCode) || SystemCode.SEAT_10000.equals(systemCode) ||
				SystemCode.SMS.equals(systemCode) || SystemCode.VOICE_10000.equals(systemCode) || SystemCode.WAP.equals(systemCode) ){
			
			if(OrderConstant.PROD_SPEC_CODE_TELEPHONE.equals(origProdSpecCode) || 
					OrderConstant.PROD_SPEC_CODE_PHS.equals(origProdSpecCode) ||
					OrderConstant.PROD_SPEC_CODE_WAN.equals(origProdSpecCode)||
					OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
				
				if(OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
					productNoTmp =order.getAccNbr();
				}else{
					lanCodeTmp = order.getAccNbr().substring(0,4);
					productNoTmp = order.getAccNbr();
				}
				ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
				prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndLanCode(productNoTmp, lanCodeTmp);
				
			}
		}else if(SystemCode.RBT.equals(systemCode) ){//彩铃平台同步，只送ProductNo，不送ProdSpecCode
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndLanCode(order.getAccNbr(), lanCodeTmp);
		}else if(systemCode==null||"null".equals(systemCode)||"".equals(systemCode)){
			if(OrderConstant.PROD_SPEC_CODE_TELEPHONE.equals(origProdSpecCode) || 
					OrderConstant.PROD_SPEC_CODE_PHS.equals(origProdSpecCode) ||
					OrderConstant.PROD_SPEC_CODE_WAN.equals(origProdSpecCode)||
					OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
				
				if(OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
					productNoTmp =order.getAccNbr();
				}else{
					lanCodeTmp = order.getAccNbr().substring(0,4);
					productNoTmp = order.getAccNbr();
				}
				ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
				prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndLanCode(productNoTmp, lanCodeTmp);
				
			}
		}
		else{
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndProductNbr(order.getAccNbr(), order.getProdId());
			
		}
		if(null != prodInstvo){
			order.setProdInstId(prodInstvo.getProdInstId());
			order.setProdId(prodInstvo.getProdId());
			order.setAccNbr(prodInstvo.getAccNbr());
			order.setLanId(prodInstvo.getLanId());
		}
	}

}
