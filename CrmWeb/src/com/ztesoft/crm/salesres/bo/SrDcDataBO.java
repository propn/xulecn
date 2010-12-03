package com.ztesoft.crm.salesres.bo;

import java.util.HashMap;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.DcDataInfoDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.DcDataInfoVO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;

/**
 * <p>
 * 积分消费日志查询
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: zteSoft
 * </p>
 *
 * @author 陈海荣
 * @version 1.0
 */
public class SrDcDataBO extends DictAction{

	/**
	 * 查询业务数据提取表
	 *
	 * @param map
	 *            HashMap
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	public PageModel qrydcDataInfo(HashMap map, int pageIndex, int pageSize)
			throws DAOSystemException {

		StringBuffer cond = new StringBuffer();

		String glanId = (String) map.get("glanId");
		String isAllProvince = (String) map.get("isAllProvince");
		if(glanId==null ||glanId.equals("") || glanId.equalsIgnoreCase("undefined")){
			return new PageModel();
		}
		
		cond.append(" 1=1 ");
		if (!"".equals(map.get("data_info_id"))) {
			cond.append(" AND info_id = ").append(map.get("data_info_id"));
		}

		if (!"".equals(map.get("data_info_nam"))) {
			cond.append(" AND  info_name	 like '%").append(map.get("data_info_nam")).append("%'");
		}
		
		String sql = cond.toString();
		if( !"true".equalsIgnoreCase(isAllProvince) ) {
			cond.append( " and (lan_id is null or lan_id='-1' or ','||lan_id||',' like '%,'||")
			.append(glanId).append("||',%')");
			sql += " and (lan_id is null or lan_id='-1' or ','||lan_id||',' like '%,'||"
				+ glanId + "||',%' )";
		}

		DcDataInfoDAO dcDataInfoDAO = SrDAOFactory.getInstance()
				.getDcDataInfoDAO();

		return PageHelper.popupPageModel(dcDataInfoDAO, sql ,
				pageIndex, pageSize);
	}

	/**
	 * 保存业务数据提取表
	 *
	 * @param BonusRetRuleExtVO
	 *            bonusRetRuleExtVO
	 * @throws DAOSystemException
	 * @return String
	 */
	public String savedcDataInfo(DcDataInfoVO dcDataInfoVO)
			throws DAOSystemException {

		DcDataInfoDAO dcDataInfoDAO = SrDAOFactory.getInstance()
				.getDcDataInfoDAO();

		SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
		SequenceManageDAO sequenceManageDAO = seqDAOFactory
				.getSequenceManageDAO();
		String InfoId = sequenceManageDAO.getNextSequence("DC_DATA_INFO",
				"INFO_ID");
		dcDataInfoVO.setInfoId(InfoId);
		dcDataInfoDAO.insert(dcDataInfoVO);
		return InfoId;

	}

	/**
	 * 修改业务数据提取表
	 *
	 * @param BonusRetRuleExtVO
	 *            bonusRetRuleExtVO
	 * @throws DAOSystemException
	 */
	public void updatedcDataInfo(DcDataInfoVO dcDataInfoVO)
			throws DAOSystemException {
		DcDataInfoDAO dcDataInfoDAO = SrDAOFactory.getInstance()
				.getDcDataInfoDAO();

		dcDataInfoDAO.update(" " + dcDataInfoVO.getInfoId(),
				dcDataInfoVO);
	}

	/**
	 * 删除业务数据提取表
	 *
	 * @param DcDataInfoVO
	 *            dcDataInfoVO
	 * @throws DAOSystemException
	 */
	public void removedcDataInfo(DynamicDict dto) throws FrameException {
		Map map = (Map)dto.getValueByName("parameter") ;
		DcDataInfoVO dcDataInfoVO = (DcDataInfoVO )map.get("dcDataInfoVO");
		DcDataInfoDAO dcDataInfoDAO = SrDAOFactory.getInstance()
				.getDcDataInfoDAO();
		dcDataInfoDAO.deleteByCond("  info_id	=" + dcDataInfoVO.getInfoId());

	}

	/**
	 * 查询本地网信息
	 *
	 *
	 *
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	public PageModel getRrLan(DynamicDict dto) throws FrameException {
		Map map = (Map)dto.getValueByName("parameter") ;
		int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();
		RrLanDAO rrLanDAO = SrDAOFactory.getInstance()
				.getRrLanDAO();

		return PageHelper.popupPageModel(rrLanDAO, " 1=1 ", pageIndex,
				pageSize);

	}


}
