package com.ztesoft.common.threadpool;

/*
 * @Author RyoUehara 090712
 * */
public class ThreadPool {
	private static Thread[] threadpool;// 定义一个存放线程的数组

	private static ThreadRun[] servicePool;// 定义一个存放线程的数组

	private static int[] threadstatus;// 定义一个存放线程运行状态的数组

	private static int threadRunedIdx;// 定义一个已经运行到的线程的索引

	private static boolean isInit = false;// 定义初始化状态

	private static int threadLimit = 0;// 定义一个线程池的线程上限数

	private static Object syncObject = "";// 同步对象

	private static final String SUCCESS_FLAG = "0";// 定义成功标志

	private static final String FAILURE_FLAG = "0";// 定义失败标志

	private static int interTime = 200;// 定义一个线程扫描时间间隔

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

	// 初始化线程池对象的参数
	public static String init(int initThreadCount, int pInterTime) {
		String isSuccess = FAILURE_FLAG;
		try {
			// 初始化线程类的参数
			initParam(initThreadCount, pInterTime);
			isSuccess = SUCCESS_FLAG;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}

	public static boolean isFull() {// 判断是否已经用满了线程池
		// 获取上次记录的索引记录，以便提高扫描的效率
		int i = getThreadStatusIdx();
		int k = 0;
		while (i < threadLimit) {// 如果这个索引是在线程数组内的，那么继续
			if (k > threadLimit) {// 如果循环超过一圈,那么也返回true;
				return true;
			}
			k++;

			// 获取当前这个索引的线程状态
			int j = getThreadStatus(i, false);
			if (j == 0) {// 如果索引状态可用
				return false;
			}
			// 如果不可用，那么遍历线程数组的每个线程，直到取到一个状态是可用的
			i = getThreadStatusIdx();
		}
		return true;
	}

	public static void seThreadLimit(int initThreadCount) {
		threadLimit = initThreadCount;
	}

	private static void initParam(int initThreadCount, int pInterTime) {
		synchronized (syncObject) {// 保证这个方法只能有一个在调用，其他的需要等待
			if (!isInit) {// 判断是否已经在初始化或者正在初始化
				System.out.println("threadpool is starting......................\n");
				// 定义线程池的线程上限数，超过这个数，后面的都需要等待
				threadLimit = initThreadCount;
				threadpool = new Thread[threadLimit];// 初始化线程数组
				threadstatus = new int[threadLimit];// 初始化线程状态数组
				threadRunedIdx = 0;// 初始化已经运行的线程数
				initNioRunPool(threadLimit);// 初始化线程数组里每个线程对象
				interTime = pInterTime;// 初始化线程扫描间隔
				isInit = true;// 标志已经初始化完成
				System.out.println("all threads has bean start up......................\n");
			}
		}
	}

	// 销毁线程对象
	public void desposeParam() {
		System.out.println("threadpool is shuting down......................\n");
		isInit = false;
		threadLimit = 0;
		threadpool = null;
		threadstatus = null;
		threadRunedIdx = 0;
		System.out.println("all threads has bean shut down......................\n");
	}

	// 初始化线程数组的每个线程对象
	private static void initNioRunPool(int initThreadCount) {
		servicePool = new ThreadRun[initThreadCount];
		for (int i = 0; i < initThreadCount; i++) {
			ThreadRun _t = new ThreadRun();// 新建一个可运行对象
			servicePool[i] = _t;// 把可执行的对象放进可执行对象数组里
			Thread currentThread = new Thread(_t);// 新建一个线程包装可运行对象
			currentThread.start();
			threadpool[i] = currentThread;// 把这个新建线程放到线程数组里
			threadstatus[i] = 0;// 0表示wait状态,可以拿来notify之后执行
		}
	}

	// public static void setThreadStatus(int i,int j) throws InterruptedException//当别人取完了索引其他人才能取,这是为了避免取到一样的索引
	// {
	// sync(threadstatus);
	// threadstatus[i]=j;
	// }

	// 获取线程状态，当别人正在设置或者正在取得时候，需要等待
	public static int getThreadStatus(int i) {
		synchronized (threadstatus) {// 保证这个方法只能有一个在调用，其他的需要等待
			if (threadstatus[i] == 0)// 一取到状态索引,马上把状态置为不可用
			{
				threadstatus[i] = 1;
				return 0;
			} else
				return 1;
		}
	}

	// 获取线程状态，当别人正在设置或者正在取得时候，需要等待
	public static int getThreadStatus(int i, boolean isSetVal) {
		synchronized (threadstatus) {// 保证这个方法只能有一个在调用，其他的需要等待
			if (threadstatus[i] == 0)// 一取到状态索引,马上把状态置为不可用
			{
				if (isSetVal) {
					threadstatus[i] = 1;
				}
				return 0;
			} else
				return 1;
		}
	}

	public static synchronized int getThreadStatusIdx() {// 获取当前线程位置,当别人正在设置或者正在取得时候，需要等待
		int rec = threadRunedIdx;// 获取已经执行到的索引数
		int next = rec + 1;// 取下一个索引
		if (next == threadLimit) {// 判断这个索引的下一个是否等于线程数组的上限，如果是，那么重新置为0
			threadRunedIdx = 0;
			return 0;
		} else {// 如果还有剩余，那么直接取next
			threadRunedIdx++;
			return next;
		}
	}

	// 获取一个可以执行的线程索引,执行传入的对象参数s
	public static int getThreadIdx() {// 当别人取完了索引其他人才能取,这是为了避免取到一样的索引
		// 获取上次记录的索引记录，以便提高扫描的效率
		int i = getThreadStatusIdx();
		while (i < threadLimit) {// 如果这个索引是在线程数组内的，那么继续
			// 获取当前这个索引的线程状态
			int j = getThreadStatus(i);
			if (j == 0) {// 如果索引状态可用
				return i;
			} else {
				// 如果不可用，那么遍历线程数组的每个线程，直到取到一个状态是可用的
				i = getThreadStatusIdx();
			}
			sleepThread(interTime);// 取不到就暂停100毫秒
		}
		return -1;
	}

	// 设置一个可以执行的线程的业务类
	public static void setThreadService(int index, ServiceExe runableInst, CallBack callback) {
		synchronized (servicePool) {
			ThreadRun b = servicePool[index];
			b.setOjbect(runableInst, callback, threadstatus, index, Thread.currentThread());
		}
	}

	// 执行传入的对象参数s
	public static boolean exeRunableService(ServiceExe inNio, CallBack callback) {

		int i = getThreadIdx();// 获取一个可以执行的现成线程索引
		//		
		try {
			if (i != -1) {
				setThreadService(i, inNio, callback);// 设置索引对应对象的参数
			}
			return true;
		} catch (Throwable ex) {
			ex.printStackTrace();

			threadstatus[i] = 0;// //执行失败,设置对应索引的状态为0,以便下一次继续调用
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
