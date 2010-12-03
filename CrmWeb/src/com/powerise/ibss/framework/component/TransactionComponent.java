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
 * 事务组件：重构自ActionDispatch
 * @author easonwu 2010-02-10
 *
 */
public class TransactionComponent implements IComponet {
	private static Logger logger = Logger.getLogger(TransactionComponent.class);

	//单例模式
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
	 * 支持多个数据库连接的处理 
	 * 如果是XA,则采用JTA方式，否则通过直接的commit和rollback来操作
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DynamicDict execute(IComponet component, DynamicDict dto)
			throws Exception {
		boolean commit = true;
		boolean bXAType = false;// 根据是否采用XA

		UserTransaction usTran = null;
		Exception error = null;
		try {
			TfmServicesVO service = ComponentUtil.getTfmServicesVO(dto);

			//需要判断是否采用XA
			bXAType = SysSet.isXAType(service.getEnv_id());
			if (bXAType) {
				usTran = SysSet.getUserTransaction();
				SysSet.tpBegin(usTran);
			}
			//执行服务
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
				//统一关闭数据库连接
				if (commit) {
					//一切执行正常
					if (bXAType)
						SysSet.tpCommit(usTran);
					else
						ConnectionContext.getContext().allCommit();
				} else {
					//出现异常
					if (bXAType)
						SysSet.tpRollback(usTran);
					else
						ConnectionContext.getContext().allRollback();
				}

				ConnectionContext.getContext().allCloseConnection();

				if (usTran != null) {
					logger.debug("关闭UserTransaction");
					usTran = null;
				}
			} catch (Exception e) {
				logger.error("关闭数据库连接时，出现异常" + LogHelper.getStackMsg(e));
//				dto.flag = -5001;
//				dto.msg = "关闭数据库连接时，出现异常";
//				dto.exception = e.getMessage();
				commit = false;
			}

			logger.debug(dto.m_ActionId + " 返回执行代码:" + dto.flag);
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
