package org.leixu.ioc.factories;

/**
 * 用于标记线程数
 * 
 * @creator cydric
 * @create-time 2010-11-28 上午01:20:31
 * @revision $Id
 **/
public class ThreadCounter {
	/***
	 * 用于计算线程数目
	 */
	private static int initThreadSize = 0;

	/**
	 * 剩余的执行线程
	 */
	private static int currentThreadSize = 0;

	/**
	 * 线程是否全部执行完
	 * @return
	 */
	public synchronized static boolean isFinished() {
		return 0 == (initThreadSize - currentThreadSize);
	}

	/**
	 * 设置initThreadSize
	 * @param size
	 */
	public static void setInitThreadSize(final int size) {
		initThreadSize = size;
	}

	/**
	 * 增加currentThreadSize
	 */
	public synchronized static void incCurrentThread() {
		currentThreadSize++;
	}
}
