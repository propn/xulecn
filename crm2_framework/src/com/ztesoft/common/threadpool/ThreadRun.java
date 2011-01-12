package com.ztesoft.common.threadpool;

/*
 * @Author RyoUehara 090712
 * */
public class ThreadRun implements Runnable {
	private ServiceExe obj;

	private Object syncObject = "";// ͬ������

	private CallBack callBack;

	private String runFlag = "0";

	// private int interTime=200;//����һ���߳�ɨ��ʱ����
	private int[] threadstatus = null;

	private int idxStatus = 0;

	private Object mainThread = null;

	public void setOjbect(ServiceExe inObj, CallBack inCallBack, int[] mainStatus, int pIdxStatus, Object inThreadOwner) {
		synchronized (this) {// ���ͬʱ��,��ô��Ҫ�ȴ�
			if (inObj == null) {
				return;
			}
			if (runFlag.equals("0")) {
				this.notifyAll(); // ֪ͨ�ȴ����߳�ִ��

				runFlag = "1";

				obj = inObj;
				callBack = inCallBack;
				threadstatus = mainStatus;
				idxStatus = pIdxStatus;
				mainThread = inThreadOwner;

			}
		}
	}

	public void run() {

		synchronized (this) {// ���ͬʱ��,��ô��Ҫ�ȴ�
			while (true) {
				try {
					// System.out.println("\nwhileѭ��"+System.currentTimeMillis());
					if (runFlag.equals("1")) {
						runFlag = "2";
						Object _obj = obj.execute(idxStatus);
						runFlag = "3";
						callBack.dealReturnVal(_obj);// �ص�ִ�����߳���ķ���
						synchronized (threadstatus) {// ��֤�������ֻ����һ���ڵ��ã���������Ҫ�ȴ�
							threadstatus[idxStatus] = 0;// ����������������
							
						}
						runFlag = "0";
					}
					this.wait();
				} catch (Throwable ex) {
					runFlag = "0";
					try {
						callBack.dealReturnVal(ex.getMessage());
					} catch (Throwable e) {// �ص�ִ�����߳���ķ���

						e.printStackTrace();
					}
					ex.printStackTrace();
				}

			}
		}
	}
}
