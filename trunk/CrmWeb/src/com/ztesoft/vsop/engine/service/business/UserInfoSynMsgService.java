package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.UserInfoSynMsgHelpDao;
import com.ztesoft.vsop.engine.help.UserInfoSynHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * Xƽ̨�û�״̬ͬ������
 * @author panshaohua
 *
 */
public class UserInfoSynMsgService extends AbstractBusinessService{
	public UserInfoSynMsgService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.ͨ����Ʒʵ����ѯ���е���Ч������ϵʵ����(��ʱ����)
		2.����ҵ��ƽ̨�û�״̬ͬ��������Ϣ��
		3.���ݶ������󡢲�Ʒʵ�����󴴽��û�״̬ͬ��XML��Ϣ��
		4.����ƽ̨�б������û�״̬ͬ�����У�
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			String openFlag=DcSystemParamManager.getInstance().getParameter("OPEN_USER_INFOSYNC");
			if (openFlag != null && "true".equalsIgnoreCase(openFlag)) {
				CustomerOrder customerOrder = (CustomerOrder) in
						.get("busiObject");
				// 2.����ҵ��ƽ̨�û�״̬ͬ��������Ϣ��
				UserInfoSynMsgHelpDao userInfoSynDao = new UserInfoSynMsgHelpDao();
				String prodInstId = customerOrder.getProdInstId();
				List systemInfo = userInfoSynDao
						.getXplatformServiceUrl(prodInstId);
				// 3.���ݶ������󡢲�Ʒʵ�����󴴽��û�״̬ͬ��XML��Ϣ��
				UserInfoSynHelp userInfoHelp = new UserInfoSynHelp();
				String requestXml = userInfoHelp.createUserSynXml(
						customerOrder, customerOrder.getProdInst());
				// 4.����ƽ̨�б������û�״̬ͬ�����У�
				userInfoSynDao.batchinsertUserInfoSynMsg(systemInfo,
						requestXml, customerOrder.getCustOrderId(),
						customerOrder.getAccNbr());
				in.put("resultCode", "0");
				in.put("resultMsg", "�ɹ�");
			}else{
				in.put("resultCode", "0");
				in.put("resultMsg", "�ɹ�");
			}
		} catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
		return in;
	}

}
