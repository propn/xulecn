package com.ztesoft.component.job;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

/**
 * 任务调度事务控制
 * 
 * @author Administrator
 * 
 */
public abstract class JobTransaction implements IJobTrans {
	private static JobLoging logger = JobLoging.getLogger();

	private UserTransaction userTransaction = null;

	/**
	 * 用于控制调度并发
	 */
	private boolean isLocked = false;

	private int perQueryMaxRow = 30;

	private int redoTimes = 3;

	public int getRedoTimes() {
		return redoTimes;
	}

	public synchronized void setRedoTimes(int redoTimes) {
		this.redoTimes = redoTimes;
	}

	/**
	 * 启动事务
	 * 
	 * @throws Exception
	 */
	public synchronized void begionTrans() throws Exception {
		if (userTransaction == null) {
			Context contex = new InitialContext();
			this.userTransaction = (UserTransaction) contex.lookup("java:comp/UserTransaction");
		}
		this.userTransaction.begin();
		logger.loging("系统启动全局事务控制");
	}

	/**
	 * 提交事务
	 * 
	 * @throws Exception
	 */
	public synchronized void commitTrans() throws Exception {
		if (this.userTransaction != null) {
			this.userTransaction.commit();
			logger.loging("系统提交启动的全局事务");
		} else {
			logger.loging("系统提交未带全局事务控制");
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @throws Exception
	 */
	public synchronized void rollbackTrans() {
		if (this.userTransaction != null) {
			try {
				this.userTransaction.rollback();
			} catch (Exception e) {
				logger.loging("系统回滚全局事务控制" + e.getMessage());
			}
		}

	}

	public synchronized boolean isLocked() {
		return isLocked;
	}

	public synchronized void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * 类继承执行方法
	 * 
	 */
	public  abstract void process();

	public synchronized int getPerQueryMaxRow() {
		return perQueryMaxRow;
	}

	public synchronized void setPerQueryMaxRow(int perQueryMaxRow) {
		this.perQueryMaxRow = perQueryMaxRow;
	}
}
