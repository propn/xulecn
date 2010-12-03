package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.engine.dao.SpOutMsgFeedbackHelpDao;
import com.ztesoft.vsop.engine.help.FeedbackHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.SpOutMsgFeedbackVO;

/**
 * �����ص�����
 * @author panshaohua
 *
 */
public class SpOutMsgFeedbackService extends AbstractBusinessService{
	protected static Logger logger = Logger.getLogger(SpOutMsgFeedbackService.class);
	public SpOutMsgFeedbackService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.�ж��Ƿ���Ҫ�ص�������ʱû��ʵ�֣�
	 * 2.���������ص�XML��Ϣ��
	 * 3.���������ص�������Ϣ��
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			try {
				FeedbackHelp feedback = new FeedbackHelp();
				String Msg = feedback.createFeedbackXml(customerOrder);
				SpOutMsgFeedbackHelpDao feedbackDao = new SpOutMsgFeedbackHelpDao();
				
				SpOutMsgFeedbackVO spOutMsgFeedback = feedbackDao.createFeedbackByOrder(customerOrder);
				spOutMsgFeedback.setMsg(Msg);
				feedbackDao.insertFeedbackQueue(spOutMsgFeedback);
				in.put("resultCode", "0");
				in.put("resultMsg", "�ɹ�");
			} catch (Exception e) {
				in.put("resultCode", "-1");
				in.put("resultMsg", "ʧ��");
				//logger.error("UserInfoSynMsgService "+e.getMessage());
				throw e;
			}
		return in;
	}
}
