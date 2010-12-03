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
 * ����װ���ת�����Ķ���
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
	 * ��������ƷID������Ӧ������ֵ��Ʒ��Ϣ
	 * �˶�������ֵ��Ʒ����OrderConstant.orderTypeOfAdd
	 * �˶�������ֵ��Ʒ״̬ΪOrderConstant.orderStateOfCreated
	 * @param prodOfferIds
	 *            ����ƷID����
	 * @return HashMap key--����ƷID,value--�������ƷID��Ӧ������ֵ��Ʒ�б�
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
		// ������Ʒ��������в�Ʒ�ӽ���
		for (Iterator iterator = prodOfferVOList.iterator(); iterator.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iterator.next();
			vProductIds = DcSystemParamManager.getInstance().getVproductIdsByOfferIdWithAllState(prodOffVO.getProdOffId());
			subVProducts = new ArrayList();
			if(vProductIds!=null){
				//����ֵ��Ʒת���ɶ�����ֵ��Ʒ�ӽ�����ͬʱ,����������ֵ��Ʒ��Ķ�����ϵʵ����ʶ
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
	 * ������������10000��ϯ��������10000����ϵͳ����ǲ�Ʒ���࣬��Ҫת��
	 * �������봫�ݹ��򣺳����ֻ��������ֶδ���4λ���ţ�����
	 * @param order
	 * @throws SQLException
	 */
	public void resetProdInstInfo(CustomerOrder order) throws SQLException {
		String origProdSpecCode = order.getProdId(); //ԭ���Ĳ�Ʒ���ͣ���Ʒ����
		String systemCode = order.getOrderSystem();  //��ϵͳ����
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
		}else if(SystemCode.RBT.equals(systemCode) ){//����ƽ̨ͬ����ֻ��ProductNo������ProdSpecCode
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
