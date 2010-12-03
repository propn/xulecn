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
			//�޸� ��ȡ�������ݵ��ڴ��ͬʱ���¶�ȡ����״̬Ϊ������ liuyuming
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
	 * ������״̬�ĳ�D������
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
	 * ���ݲ�ƷIDȥ��ȡ��Ʒ�����
	 * @param productId
	 * @return
	 */
	public String getProductNbr(String productId) {
		return infOrderRelationDao.getProductNbr(productId);//ע�⣺�����õ������productId��ʵ�Ƕ��������й�������ֵ��Ʒ��������
	}
	
	/**
	 * ��ѯ���CRM_ISMP_CODE_MAP
	 * �жϸ���ֵ��Ʒ�Ƿ��ڿ����(��ѯ��ʱ����Ҫ�ҵ���ֵ��Ʒ������ȥƥ�����ֶ�ISMP_CODE)
	 * @param infOrderRelationVo
	 * @return
	 */
	public boolean isExistCRMMap(String productNbr) {
		return infOrderRelationDao.isExistCRMMap(productNbr);
	}
	
	/**
	 * �� InfOrderRelationVo �鵵����ʷ�� inf_order_relation_his
	 * @param vo
	 */
	public void writeInfOrderRelationHis(InfOrderRelationVo vo) {
		infOrderRelationDao.writeInfOrderRelationHis(vo);
	}
	
	/**
	 * ��������ɾ�� InfOrderRelationVo
	 * @param infOrderRelationId
	 */
	public void delInfOrderRelation(String infOrderRelationId) {
		infOrderRelationDao.delInfOrderRelation(infOrderRelationId);
	}
	
	/**
	 * ��״̬�ĳ�F,���ʹ�����1
	 * @param list
	 * @return
	 */
	public void updateInfOrderRelationWhenFail(InfOrderRelationVo vo) {
		int num = 1 + Integer.parseInt(vo.getSendTimes());
		vo.setSendTimes("" + num);
		infOrderRelationDao.updateInfOrderRelationWhenFail(vo);
	}
}




