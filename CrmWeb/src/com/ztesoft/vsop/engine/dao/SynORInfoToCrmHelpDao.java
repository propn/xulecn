package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ����������ϵʵ���������ʱ�跴��ͬ����CRM
 * @author panshaohua
 *
 */
public class SynORInfoToCrmHelpDao {
	protected static Logger logger =Logger.getLogger(SynORInfoToCrmHelpDao.class);
	private String SEQ_INF_ORDER_RELATION_ID = "SEQ_INF_ORDER_RELATION_ID";
	
	private String SQL_INSERT = "insert into inf_order_relation(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,"+DatabaseFunction.current()+","+DatabaseFunction.current()+",?,?)";
	/**
	 * ���ݶ�����������������ϵͬ������,ͬʱֻ�з���ӳ������ֵ��Ʒ������
	 * @param order
	 * @throws Exception
	 */
	public void createChangeORQueueByCustomerOrder(CustomerOrder order)
			throws Exception {
		SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
		for (Iterator productOfferItr = order.getProductOfferInfoList()
				.iterator(); productOfferItr.hasNext();) {
			ProductOfferInfo productOffer = (ProductOfferInfo) productOfferItr
					.next();
			for (Iterator vSubProdInfos = productOffer.getVproductInfoList()
					.iterator(); vSubProdInfos.hasNext();) {
				VproductInfo vSubProdInfo = (VproductInfo) vSubProdInfos.next();
				String productNbr = vSubProdInfo.getVProductNbr();
				if (null != productNbr && !"".equals(productNbr)
						&& DcSystemParamManager.getInstance().isExistInCrmCode(productNbr)) {//����д��ͬ��crm�ӿڱ�Ĺ���
					Connection con = null;
					PreparedStatement ps = null;
					int i = 1;
					//������������Ʒʱ��ֻ�������������Ʒ���ӿڱ�packageId�У�����Ҫ����������Ʒ�µ�������ֵ��Ʒ�����룬�����߼�����crm����
					String offerType = productOffer.getOfferType();
					try {
						con = ConnectionContext.getContext().getConnection(
								JNDINames.DEFAULT_DATASOURCE);
						ps = con.prepareStatement(SQL_INSERT);
						ps.setLong(i++, Long.parseLong(aSequenceManageDAOImpl
								.getNextSequence(SEQ_INF_ORDER_RELATION_ID)));
						ProdInstVO prodInstVo=order.getProdInst();//�û�VO
						String userId=prodInstVo.getAccNbr();//�û�����
						String userIdType=prodInstVo.getProdId();//�û���Ʒ����
						ps.setString(i++, userId);
						ps.setString(i++, userIdType);
						if("1".equals(offerType)){
							//������Ʒ�����������ʱ��ά���ӿڱ�packageId,product_id��ֵ��Ʒ������ֻ�����һ����¼��
							ps.setString(i++, "");
							ps.setString(i++, productOffer.getOfferId());
						}else{
							ps.setString(i++, vSubProdInfo.getVProductId());
							ps.setString(i++, "");
						}
						String opType=order.getCustOrderType();
						if("1".equals(opType)){
							//����ʱ��crmҪ���˶���������3
							opType="3";
						}
						if("2".equals(opType)){//������ȫ���˶�ʱ����crmʱת��3
							//����ʱ��crmҪ���˶���������3
							opType="3";
						}
						ps.setString(i++, opType);
						ps.setString(i++, "U");
						ps.setString(i++, "1");
						ps.setInt(i++, DAOUtils.getCurrentMonth());
						ps.executeUpdate();
					} catch (Exception e) {
						logger.error("��д���INF_ORDER_RELATIONʧ��!", e);
						throw e;
					} finally {
						DAOUtils.closeStatement(ps);
					}
					//������Ʒ����������ʱ������Ҫ��β�����ֵ��Ʒ������������ƷΪά��
					if("1".equals(offerType))break;
				}
			}
		}
	}
}
