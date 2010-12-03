package com.ztesoft.component.job;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

/**
 * ��������������
 * 
 * @author Administrator
 * 
 */
public abstract class JobTransaction implements IJobTrans {
	private static JobLoging logger = JobLoging.getLogger();

	private UserTransaction userTransaction = null;

	/**
	 * ���ڿ��Ƶ��Ȳ���
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
	 * ��������
	 * 
	 * @throws Exception
	 */
	public synchronized void begionTrans() throws Exception {
		if (userTransaction == null) {
			Context contex = new InitialContext();
			this.userTransaction = (UserTransaction) contex.lookup("java:comp/UserTransaction");
		}
		this.userTransaction.begin();
		logger.loging("ϵͳ����ȫ���������");
	}

	/**
	 * �ύ����
	 * 
	 * @throws Exception
	 */
	public synchronized void commitTrans() throws Exception {
		if (this.userTransaction != null) {
			this.userTransaction.commit();
			logger.loging("ϵͳ�ύ������ȫ������");
		} else {
			logger.loging("ϵͳ�ύδ��ȫ���������");
		}
	}

	/**
	 * �ع�����
	 * 
	 * @throws Exception
	 */
	public synchronized void rollbackTrans() {
		if (this.userTransaction != null) {
			try {
				this.userTransaction.rollback();
			} catch (Exception e) {
				logger.loging("ϵͳ�ع�ȫ���������" + e.getMessage());
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
	 * ��̳�ִ�з���
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
