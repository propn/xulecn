package com.ztesoft.vsop.product.bo;

import java.util.List;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.product.dao.ProductSyncDAO;
import com.ztesoft.vsop.product.vo.InfProdToOCSVO;
/**
 * @desc
 * @author qin.guoquan
 * @date Oct 11, 2010 1:58:01 PM
 */
public class ProductSyncBO {

	//private static final Logger log = Logger.getLogger(ProductSyncBO.class);
	private ProductSyncDAO productSyncDao;
	
	public ProductSyncBO() {
		productSyncDao = new ProductSyncDAO();
	}
	
	/**
	 * ��������˳��ȡ��ǰN������״̬ͬʱΪU�����ݣ����д���ͬʱ����״̬��ΪD
	 * @param prodSubType
	 * @return
	 * @throws Exception
	 */
	public List getInfProdToOCSInfo(String prodSubType) throws Exception {
		
		List list = null;
		try {
			list = productSyncDao.getInfProdToOCSInfo(prodSubType);
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
 		return list;
	}
	
	/**
	 * ÿ����һ�Σ����ʹ�����1,���ʹ������ڴ���4���ٷ��ͣ�����¼״̬��F
	 * @param vo
	 * @throws Exception
	 */
	public void updateInfProdToOCS(InfProdToOCSVO vo) throws Exception {
		
		try {
			productSyncDao.updateInfProdToOCS(vo);
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}
	
	/**
	 * д��ʷ�����
	 * @param vo
	 * @throws Exception
	 */
	public void witeInfProdToOCSHis(InfProdToOCSVO vo) throws Exception {
		
		try {
			productSyncDao.witeInfProdToOCSHis(vo);
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}
	
}
