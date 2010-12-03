package com.ztesoft.vsop.provinceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class AppendLanCode {
	private static Logger logger = Logger.getLogger(OrderDao.class);
	private static AppendLanCode lan = null;
	private AppendLanCode(){}
	public static AppendLanCode getInstance(){
		if(lan == null){
			lan = new AppendLanCode();
		}
		return lan;
	}
	/**
	 * �������ػ��������ֻ���ҵ�����׷�����š�
	 * @param accNbr ҵ�����
	 * @param prodType ��Ʒ����
	 * @param lanId ������ID
	 * @return
	 * @throws SQLException
	 */
	public String appendAccNbrLan(String accNbr, String prodType, String lanId) throws SQLException{
		//�������ػ������ֻ�������Ʒ���͵��û�����ǰ�涼��Ҫ�������������Ʒʵ�����acc_nbr��
		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
		logger.info("---------provinceCode:"+provinceCode);
		//����Ҫ������������Ψһ�ԵĲ�Ʒ����
		String noAppendLan = CrmParamsConfig.getInstance().getParamValue("NoAppendLanCode");
		if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
			if(noAppendLan != null && noAppendLan.indexOf(prodType) == -1){
				Map lanMap = DcSystemParamManager.getInstance().getLanid2lanCodeMap();
				String lanCode = Const.getStrValue(lanMap, lanId);
				accNbr = lanCode+accNbr;	
			}
		}
		return accNbr;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
