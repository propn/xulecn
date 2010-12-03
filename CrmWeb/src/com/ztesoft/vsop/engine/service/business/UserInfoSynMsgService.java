package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.UserInfoSynMsgHelpDao;
import com.ztesoft.vsop.engine.help.UserInfoSynHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * X平台用户状态同步服务
 * @author panshaohua
 *
 */
public class UserInfoSynMsgService extends AbstractBusinessService{
	public UserInfoSynMsgService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.通过产品实例查询所有的有效订购关系实例；(暂时不用)
		2.加载业务平台用户状态同步配置信息；
		3.根据订单对象、产品实例对象创建用户状态同步XML信息；
		4.根据平台列表新增用户状态同步队列；
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			String openFlag=DcSystemParamManager.getInstance().getParameter("OPEN_USER_INFOSYNC");
			if (openFlag != null && "true".equalsIgnoreCase(openFlag)) {
				CustomerOrder customerOrder = (CustomerOrder) in
						.get("busiObject");
				// 2.加载业务平台用户状态同步配置信息；
				UserInfoSynMsgHelpDao userInfoSynDao = new UserInfoSynMsgHelpDao();
				String prodInstId = customerOrder.getProdInstId();
				List systemInfo = userInfoSynDao
						.getXplatformServiceUrl(prodInstId);
				// 3.根据订单对象、产品实例对象创建用户状态同步XML信息；
				UserInfoSynHelp userInfoHelp = new UserInfoSynHelp();
				String requestXml = userInfoHelp.createUserSynXml(
						customerOrder, customerOrder.getProdInst());
				// 4.根据平台列表新增用户状态同步队列；
				userInfoSynDao.batchinsertUserInfoSynMsg(systemInfo,
						requestXml, customerOrder.getCustOrderId(),
						customerOrder.getAccNbr());
				in.put("resultCode", "0");
				in.put("resultMsg", "成功");
			}else{
				in.put("resultCode", "0");
				in.put("resultMsg", "成功");
			}
		} catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
		return in;
	}

}
