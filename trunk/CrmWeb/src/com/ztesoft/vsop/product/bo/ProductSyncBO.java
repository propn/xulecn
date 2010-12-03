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
	 * 按主键的顺序取出前N条数据状态同时为U的数据，进行处理，同时把其状态置为D
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
	 * 每发送一次，重送次数加1,重送次数等于大于4则不再发送，将记录状态置F
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
	 * 写历史表操作
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
