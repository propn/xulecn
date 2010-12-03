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
	 * 广西本地化，将非手机的业务号码追加区号。
	 * @param accNbr 业务号码
	 * @param prodType 产品类型
	 * @param lanId 本地网ID
	 * @return
	 * @throws SQLException
	 */
	public String appendAccNbrLan(String accNbr, String prodType, String lanId) throws SQLException{
		//广西本地化：除手机其他产品类型的用户号码前面都需要加区号再填入产品实例表的acc_nbr中
		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
		logger.info("---------provinceCode:"+provinceCode);
		//不需要加区号以区分唯一性的产品类型
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
