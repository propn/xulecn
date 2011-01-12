package com.ztesoft.common.threadpool;

/*
 * @Author RyoUehara 090712
 * */
public class ThreadPool {
	private static Thread[] threadpool;// ����һ������̵߳�����

	private static ThreadRun[] servicePool;// ����һ������̵߳�����

	private static int[] threadstatus;// ����һ������߳�����״̬������

	private static int threadRunedIdx;// ����һ���Ѿ����е����̵߳�����

	private static boolean isInit = false;// �����ʼ��״̬

	private static int threadLimit = 0;// ����һ���̳߳ص��߳�������

	private static Object syncObject = "";// ͬ������

	private static final String SUCCESS_FLAG = "0";// ����ɹ���־

	private static final String FAILURE_FLAG = "0";// ����ʧ�ܱ�־

	private static int interTime = 200;// ����һ���߳�ɨ��ʱ����

	// private static int[] threadPoolStaus={0};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static int getThreadLimit() {
		return threadLimit;
	}

	public static boolean getInitFlag() {
		return isInit;
	}

	// ��ʼ���̳߳ض���Ĳ���
	public static String init(int initThreadCount, int pInterTime) {
		String isSuccess = FAILURE_FLAG;
		try {
			// ��ʼ���߳���Ĳ���
			initParam(initThreadCount, pInterTime);
			isSuccess = SUCCESS_FLAG;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}

	public static boolean isFull() {// �ж��Ƿ��Ѿ��������̳߳�
		// ��ȡ�ϴμ�¼��������¼���Ա����ɨ���Ч��
		int i = getThreadStatusIdx();
		int k = 0;
		while (i < threadLimit) {// ���������������߳������ڵģ���ô����
			if (k > threadLimit) {// ���ѭ������һȦ,��ôҲ����true;
				return true;
			}
			k++;

			// ��ȡ��ǰ����������߳�״̬
			int j = getThreadStatus(i, false);
			if (j == 0) {// �������״̬����
				return false;
			}
			// ��������ã���ô�����߳������ÿ���̣߳�ֱ��ȡ��һ��״̬�ǿ��õ�
			i = getThreadStatusIdx();
		}
		return true;
	}

	public static void seThreadLimit(int initThreadCount) {
		threadLimit = initThreadCount;
	}

	private static void initParam(int initThreadCount, int pInterTime) {
		synchronized (syncObject) {// ��֤�������ֻ����һ���ڵ��ã���������Ҫ�ȴ�
			if (!isInit) {// �ж��Ƿ��Ѿ��ڳ�ʼ���������ڳ�ʼ��
				System.out.println("threadpool is starting......................\n");
				// �����̳߳ص��߳������������������������Ķ���Ҫ�ȴ�
				threadLimit = initThreadCount;
				threadpool = new Thread[threadLimit];// ��ʼ���߳�����
				threadstatus = new int[threadLimit];// ��ʼ���߳�״̬����
				threadRunedIdx = 0;// ��ʼ���Ѿ����е��߳���
				initNioRunPool(threadLimit);// ��ʼ���߳�������ÿ���̶߳���
				interTime = pInterTime;// ��ʼ���߳�ɨ����
				isInit = true;// ��־�Ѿ���ʼ�����
				System.out.println("all threads has bean start up......................\n");
			}
		}
	}

	// �����̶߳���
	public void desposeParam() {
		System.out.println("threadpool is shuting down......................\n");
		isInit = false;
		threadLimit = 0;
		threadpool = null;
		threadstatus = null;
		threadRunedIdx = 0;
		System.out.println("all threads has bean shut down......................\n");
	}

	// ��ʼ���߳������ÿ���̶߳���
	private static void initNioRunPool(int initThreadCount) {
		servicePool = new ThreadRun[initThreadCount];
		for (int i = 0; i < initThreadCount; i++) {
			ThreadRun _t = new ThreadRun();// �½�һ�������ж���
			servicePool[i] = _t;// �ѿ�ִ�еĶ���Ž���ִ�ж���������
			Thread currentThread = new Thread(_t);// �½�һ���̰߳�װ�����ж���
			currentThread.start();
			threadpool[i] = currentThread;// ������½��̷߳ŵ��߳�������
			threadstatus[i] = 0;// 0��ʾwait״̬,��������notify֮��ִ��
		}
	}

	// public static void setThreadStatus(int i,int j) throws InterruptedException//������ȡ�������������˲���ȡ,����Ϊ�˱���ȡ��һ��������
	// {
	// sync(threadstatus);
	// threadstatus[i]=j;
	// }

	// ��ȡ�߳�״̬���������������û�������ȡ��ʱ����Ҫ�ȴ�
	public static int getThreadStatus(int i) {
		synchronized (threadstatus) {// ��֤�������ֻ����һ���ڵ��ã���������Ҫ�ȴ�
			if (threadstatus[i] == 0)// һȡ��״̬����,���ϰ�״̬��Ϊ������
			{
				threadstatus[i] = 1;
				return 0;
			} else
				return 1;
		}
	}

	// ��ȡ�߳�״̬���������������û�������ȡ��ʱ����Ҫ�ȴ�
	public static int getThreadStatus(int i, boolean isSetVal) {
		synchronized (threadstatus) {// ��֤�������ֻ����һ���ڵ��ã���������Ҫ�ȴ�
			if (threadstatus[i] == 0)// һȡ��״̬����,���ϰ�״̬��Ϊ������
			{
				if (isSetVal) {
					threadstatus[i] = 1;
				}
				return 0;
			} else
				return 1;
		}
	}

	public static synchronized int getThreadStatusIdx() {// ��ȡ��ǰ�߳�λ��,�������������û�������ȡ��ʱ����Ҫ�ȴ�
		int rec = threadRunedIdx;// ��ȡ�Ѿ�ִ�е���������
		int next = rec + 1;// ȡ��һ������
		if (next == threadLimit) {// �ж������������һ���Ƿ�����߳���������ޣ�����ǣ���ô������Ϊ0
			threadRunedIdx = 0;
			return 0;
		} else {// �������ʣ�࣬��ôֱ��ȡnext
			threadRunedIdx++;
			return next;
		}
	}

	// ��ȡһ������ִ�е��߳�����,ִ�д���Ķ������s
	public static int getThreadIdx() {// ������ȡ�������������˲���ȡ,����Ϊ�˱���ȡ��һ��������
		// ��ȡ�ϴμ�¼��������¼���Ա����ɨ���Ч��
		int i = getThreadStatusIdx();
		while (i < threadLimit) {// ���������������߳������ڵģ���ô����
			// ��ȡ��ǰ����������߳�״̬
			int j = getThreadStatus(i);
			if (j == 0) {// �������״̬����
				return i;
			} else {
				// ��������ã���ô�����߳������ÿ���̣߳�ֱ��ȡ��һ��״̬�ǿ��õ�
				i = getThreadStatusIdx();
			}
			sleepThread(interTime);// ȡ��������ͣ100����
		}
		return -1;
	}

	// ����һ������ִ�е��̵߳�ҵ����
	public static void setThreadService(int index, ServiceExe runableInst, CallBack callback) {
		synchronized (servicePool) {
			ThreadRun b = servicePool[index];
			b.setOjbect(runableInst, callback, threadstatus, index, Thread.currentThread());
		}
	}

	// ִ�д���Ķ������s
	public static boolean exeRunableService(ServiceExe inNio, CallBack callback) {

		int i = getThreadIdx();// ��ȡһ������ִ�е��ֳ��߳�����
		//		
		try {
			if (i != -1) {
				setThreadService(i, inNio, callback);// ����������Ӧ����Ĳ���
			}
			return true;
		} catch (Throwable ex) {
			ex.printStackTrace();

			threadstatus[i] = 0;// //ִ��ʧ��,���ö�Ӧ������״̬Ϊ0,�Ա���һ�μ�������
			return false;
		}

	}

	public static void sleepThread(long timeSeg) {
		try {
			Thread.currentThread().sleep(timeSeg);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
