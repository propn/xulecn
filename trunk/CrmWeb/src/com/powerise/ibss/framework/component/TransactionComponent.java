package com.powerise.ibss.framework.component;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.LogHelper;
import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.exception.CrmException;

/**
 * 
 * 
 * ����������ع���ActionDispatch
 * @author easonwu 2010-02-10
 *
 */
public class TransactionComponent implements IComponet {
	private static Logger logger = Logger.getLogger(TransactionComponent.class);

	//����ģʽ
	private TransactionComponent() {

	}

	public static IComponet getInstance() {
		return SingletonHolder.instance;
	}

	static class SingletonHolder {
		static IComponet instance = new TransactionComponent();
	}

	/**
	 * 	   
	 * ֧�ֶ�����ݿ����ӵĴ��� 
	 * �����XA,�����JTA��ʽ������ͨ��ֱ�ӵ�commit��rollback������
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DynamicDict execute(IComponet component, DynamicDict dto)
			throws Exception {
		boolean commit = true;
		boolean bXAType = false;// �����Ƿ����XA

		UserTransaction usTran = null;
		Exception error = null;
		try {
			TfmServicesVO service = ComponentUtil.getTfmServicesVO(dto);

			//��Ҫ�ж��Ƿ����XA
			bXAType = SysSet.isXAType(service.getEnv_id());
			if (bXAType) {
				usTran = SysSet.getUserTransaction();
				SysSet.tpBegin(usTran);
			}
			//ִ�з���
			component.execute(component, dto);
		} catch (FrameException fe) {
			commit = false;
			error = fe;
		} catch (CrmException ce) {
			commit = false;
			error = ce;
		} catch (Exception th) {
			commit = false;
			error = th;
		} finally {
			try {
				//ͳһ�ر����ݿ�����
				if (commit) {
					//һ��ִ������
					if (bXAType)
						SysSet.tpCommit(usTran);
					else
						ConnectionContext.getContext().allCommit();
				} else {
					//�����쳣
					if (bXAType)
						SysSet.tpRollback(usTran);
					else
						ConnectionContext.getContext().allRollback();
				}

				ConnectionContext.getContext().allCloseConnection();

				if (usTran != null) {
					logger.debug("�ر�UserTransaction");
					usTran = null;
				}
			} catch (Exception e) {
				logger.error("�ر����ݿ�����ʱ�������쳣" + LogHelper.getStackMsg(e));
//				dto.flag = -5001;
//				dto.msg = "�ر����ݿ�����ʱ�������쳣";
//				dto.exception = e.getMessage();
				commit = false;
			}

			logger.debug(dto.m_ActionId + " ����ִ�д���:" + dto.flag);
			if (commit == false) {
				if (GlobalNames.ERR_TO_DB) {
					long lErrSeq = LogHelper
							.writeLog(dto.m_ActionId, dto.flag, dto.msg,
									dto.exception == null ? "" : dto.exception);
				}
				throw error;
			}
		}
		return dto;
	}
}
