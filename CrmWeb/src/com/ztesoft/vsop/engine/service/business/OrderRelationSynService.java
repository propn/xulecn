package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ������ϵͬ������
 * @author panshaohua
 *
 */
public class OrderRelationSynService extends AbstractBusinessService{
	public OrderRelationSynService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 *  1.����������
		2.���ݶ����������������
		3.���ݶ������������޸Ķ�����ϵʵ����
		4.ȫ��������ϵͬ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			
			// �������ػ�����
			String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
//			if (CrmConstants.GX_PROV_CODE.equals(provinceCode)) {
//				// ������ORDER_RELATION��������ͬACC_NBR,PRODUCT_ID,STATE�ļ�¼
//				// �����ֵ��Ʒ�Ѿ���VSOP�����˴�ͳ+��ֵ�ײ��򶩹���ϵ��IT��Ϊ׼����������
//				List productOfferInfoList = customerOrder.getProductOfferInfoList();
//				ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferInfoList.get(0);
//				VproductInfo vproductInfo = (VproductInfo) productOfferInfo.getVproductInfoList().get(0);
//				String productId = vproductInfo.getVProductId();
//				String accNbr = customerOrder.getAccNbr();
////				String state = customerOrder.getProdInst().getStateCd();
//				String ppid = orderRelationDao.isExistsOrderRelation(accNbr, productId, "00A");
//				if (ppid != null && !"".equals(ppid)) {
//					in.put("resultCode", "0");
//					in.put("resultMsg", "�ɹ�");
//					return in;
//				}
//			}
			
			//******
			//�������ػ�
			//**********
			if (CrmConstants.GX_PROV_CODE.equals(provinceCode)) {
				List productOfferInfoList = customerOrder.getProductOfferInfoList();
				ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferInfoList.get(0);
				VproductInfo vproductInfo = (VproductInfo) productOfferInfo.getVproductInfoList().get(0);
				String productId = vproductInfo.getVProductId();
				String accNbr = customerOrder.getAccNbr();
				String orderType=customerOrder.getCustOrderType();
				//values����һ����','�ָ�Ĵ�����һ��������������ʾ�ǲ��Ǵ��ڣ��ڶ�����pprodofferId����ʾ�ǲ��Ǵ��ڴ�ͳ+��ֵ
				String values=orderRelationDao.isExistsOrderRelationOnOrderRelatID(accNbr, productId, "00A");
				
				//-1��ʾ���û���Ϣ��δͬ����VSOP,�����м��order_relation_middle�����û��Ķ�����ϵʵ��
				if(null!=customerOrder.getProdInstId() && "-1".equals(customerOrder.getProdInstId())){
					values=orderRelationDao.isExistsOrderRelationMiddleOnOrderRelatID(accNbr, productId, "00A");
				}
				
				if(values != null){
					String[] vals = values.split(",");
					String ppid="";
					if(vals.length>1){
						ppid=vals[1];
					}
//					����������Ƕ�����ϵͬ�������ģ���ʹ����ֵ��Ʒ�ڴ�ͳ+��ֵ�Ҳ���˶�������ͬ����CRM,GX_PPROD_TYPEΪ��־
					if(null!=ppid && !"".equals(ppid)){
						in.put("GX_PPROD_TYPE", "1");
					}

//					��Ч(������ORDER_RELATION��������ͬACC_NBR,PRODUCT_ID,STATE�ļ�¼, �����ֵ��Ʒ�Ѿ���VSOP�����˴�ͳ+��ֵ�ײ��򶩹���ϵ��IT��Ϊ׼����������)
//					if (ppid != null && !"".equals(ppid)) {
//						in.put("resultCode", "0");
//						in.put("resultMsg", "�ɹ�");
//						return in;
//					}
					//�����ɲ����ISMP�����Ķ�����ϵͬ��ʱ����accNbr, productId, "00A"��������ڣ��ҹ����Ķ����Ƿ�ʧЧ�ģ���ͬ����VSOP�⣬����ֱ�ӷ��سɹ�
					if(!"1".equals(orderType)){//������ת�ڲ���������. 1:�˶�����
						in.put("resultCode", "0");
						in.put("resultMsg", "�ɹ�");
						return in;
					}
				//valuesΪnull�Ļ����������������ڣ���ʱ���������������˶��ģ�������ϵͬ��ֱ�ӷ��سɹ�
				}else if("1".equals(orderType)){//����ת�ڲ���������. 1:�˶�����
					in.put("resultCode", "0");
					in.put("resultMsg", "�ɹ�");
					return in;
				}
			}
			
			//1.����������
			CustOrderHelpDao custOrderHelpDao=new CustOrderHelpDao();
			custOrderHelpDao.insertOrderHis(customerOrder);
			//2.����������ֵ��Ʒ���������
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsHisByOrder(customerOrder);
			//3.���ݶ������������޸Ķ�����ϵʵ����
			customerOrder.setSendActiveFlag(false);//ismpͬ�����ͼ���
			//*****
			//�������ػ�,���������ϵͬ������ʱ��VSOP�����յ�����ͬ���������û���Ϣ����Ѷ�����ϵд��ORDER_RELATION_MIDDLE��,������ʱ������
			//*****
			if (provinceCode.equals(CrmConstants.GX_PROV_CODE)
					&& !customerOrder.isExistProdInst()) {
				orderRelationDao.modifyORSMIDDByCustomerOrder(customerOrder);
			} else {
				orderRelationDao.modifyORSByCustomerOrder(customerOrder);
			}
			//�������ػ���һ�׶ι����ݲ���Ҫͬ��ȫ��ҵ�������VSOP liuyuming 2010-09-08 start
			if(!CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				//4.ȫ��������ϵͬ������
				OrderRelationSynWholeService orderRelationSynWholeService = new OrderRelationSynWholeService();
				orderRelationSynWholeService.service(in);
			}
			//�������ػ���һ�׶ι����ݲ���Ҫͬ��ȫ��ҵ�������VSOP liuyuming 2010-09-08 end
			in.put("resultCode", "0");
			in.put("resultMsg", "�ɹ�");
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}

}
