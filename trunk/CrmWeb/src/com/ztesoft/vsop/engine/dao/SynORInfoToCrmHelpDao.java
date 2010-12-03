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
 * 广西订购关系实例发生变更时需反向同步给CRM
 * @author panshaohua
 *
 */
public class SynORInfoToCrmHelpDao {
	protected static Logger logger =Logger.getLogger(SynORInfoToCrmHelpDao.class);
	private String SEQ_INF_ORDER_RELATION_ID = "SEQ_INF_ORDER_RELATION_ID";
	
	private String SQL_INSERT = "insert into inf_order_relation(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,"+DatabaseFunction.current()+","+DatabaseFunction.current()+",?,?)";
	/**
	 * 根据订单对象新增订购关系同步队列,同时只有符合映射表的增值产品才新增
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
						&& DcSystemParamManager.getInstance().isExistInCrmCode(productNbr)) {//增加写入同步crm接口表的过滤
					Connection con = null;
					PreparedStatement ps = null;
					int i = 1;
					//当是捆绑销售品时，只需插入捆绑销售品到接口表packageId中，不需要将捆绑销售品下的所有增值产品都插入，具体逻辑交由crm处理
					String offerType = productOffer.getOfferType();
					try {
						con = ConnectionContext.getContext().getConnection(
								JNDINames.DEFAULT_DATASOURCE);
						ps = con.prepareStatement(SQL_INSERT);
						ps.setLong(i++, Long.parseLong(aSequenceManageDAOImpl
								.getNextSequence(SEQ_INF_ORDER_RELATION_ID)));
						ProdInstVO prodInstVo=order.getProdInst();//用户VO
						String userId=prodInstVo.getAccNbr();//用户号码
						String userIdType=prodInstVo.getProdId();//用户产品类型
						ps.setString(i++, userId);
						ps.setString(i++, userIdType);
						if("1".equals(offerType)){
							//当销售品类型是捆绑的时候维护接口表packageId,product_id增值产品留空且只需插入一条记录。
							ps.setString(i++, "");
							ps.setString(i++, productOffer.getOfferId());
						}else{
							ps.setString(i++, vSubProdInfo.getVProductId());
							ps.setString(i++, "");
						}
						String opType=order.getCustOrderType();
						if("1".equals(opType)){
							//联调时，crm要求退订的类型送3
							opType="3";
						}
						if("2".equals(opType)){//过来的全部退订时，送crm时转送3
							//联调时，crm要求退订的类型送3
							opType="3";
						}
						ps.setString(i++, opType);
						ps.setString(i++, "U");
						ps.setString(i++, "1");
						ps.setInt(i++, DAOUtils.getCurrentMonth());
						ps.executeUpdate();
					} catch (Exception e) {
						logger.error("填写库表INF_ORDER_RELATION失败!", e);
						throw e;
					} finally {
						DAOUtils.closeStatement(ps);
					}
					//当销售品类型是捆绑时，不需要多次插入增值产品，以捆绑销售品为维度
					if("1".equals(offerType))break;
				}
			}
		}
	}
}
