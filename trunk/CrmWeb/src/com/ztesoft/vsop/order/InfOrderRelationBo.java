package com.ztesoft.vsop.order;

import java.sql.SQLException;
import java.util.List;

import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.dao.InfOrderRelationDao;

public class InfOrderRelationBo {

	private InfOrderRelationDao infOrderRelationDao;
	
	public InfOrderRelationBo() {
		infOrderRelationDao = new InfOrderRelationDao();
	}
	
	public List getInfOrderRelation() throws Exception {
		List ret = null;
		try {
			//修改 读取处理数据到内存的同时更新读取数据状态为处理中 liuyuming
			//		return infOrderRelationDao.getInfOrderRelation();
			ret = infOrderRelationDao.getInfOrderRelaAndDeal();
			
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}
	
	/**
	 * 将数据状态改成D处理中
	 * @param list
	 * @return
	 */
	public List handleInfOrderRelation(List list) {
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				InfOrderRelationVo vo = (InfOrderRelationVo) list.get(i);
				infOrderRelationDao.updateInfOrderRelation(vo);
			}
		}
		return list;
	}
	
	/**
	 * 根据产品ID去获取产品外编码
	 * @param productId
	 * @return
	 */
	public String getProductNbr(String productId) {
		return infOrderRelationDao.getProductNbr(productId);//注意：现在拿到的这个productId其实是订购申请中过来的增值产品编码来的
	}
	
	/**
	 * 查询库表CRM_ISMP_CODE_MAP
	 * 判断该增值产品是否在库表中(查询的时候需要找到增值产品的外码去匹配库表字段ISMP_CODE)
	 * @param infOrderRelationVo
	 * @return
	 */
	public boolean isExistCRMMap(String productNbr) {
		return infOrderRelationDao.isExistCRMMap(productNbr);
	}
	
	/**
	 * 把 InfOrderRelationVo 归档到历史表 inf_order_relation_his
	 * @param vo
	 */
	public void writeInfOrderRelationHis(InfOrderRelationVo vo) {
		infOrderRelationDao.writeInfOrderRelationHis(vo);
	}
	
	/**
	 * 根据主键删除 InfOrderRelationVo
	 * @param infOrderRelationId
	 */
	public void delInfOrderRelation(String infOrderRelationId) {
		infOrderRelationDao.delInfOrderRelation(infOrderRelationId);
	}
	
	/**
	 * 将状态改成F,发送次数加1
	 * @param list
	 * @return
	 */
	public void updateInfOrderRelationWhenFail(InfOrderRelationVo vo) {
		int num = 1 + Integer.parseInt(vo.getSendTimes());
		vo.setSendTimes("" + num);
		infOrderRelationDao.updateInfOrderRelationWhenFail(vo);
	}
}




