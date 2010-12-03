package com.ztesoft.common.threadpool;
/*
 * @Author RyoUehara 090712
 * */
public class ThreadRun implements Runnable {
	private ServiceExe obj;
	private Object syncObject="";//同步对象
	private CallBack callBack;
	private String runFlag="0";
//	private int interTime=200;//定义一个线程扫描时间间隔
	private int []threadstatus=null;
	private int idxStatus=0;
	private Object mainThread=null;
//	private int[] threadPoolStaus;
	
//	public void setInterTime(int pInterTime){
//		interTime=pInterTime;
//	}
	public void setOjbect(ServiceExe inObj,CallBack inCallBack,int[]mainStatus,int pIdxStatus,Object inThreadOwner)
	{
		synchronized(this){//如果同时改,那么需要等待
			if (inObj==null)
			{return;}	
			if(runFlag.equals("0")){
				this.notifyAll();
				runFlag="1";
//				interTime=pInterTime;
				obj=inObj;
				callBack=inCallBack;
				threadstatus=mainStatus;
				idxStatus=pIdxStatus;
				mainThread=inThreadOwner;
//				threadPoolStaus=pThreadPoolStaus;
			}
		}
	}


	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){//如果同时改,那么需要等待
			while(true){
				try
				{
//					System.out.println("\nwhile循环"+System.currentTimeMillis());
					if(runFlag.equals("1")){
						runFlag="2";
						Object _obj=obj.execute();
						runFlag="3";
						callBack.dealReturnVal(_obj);//回调执行主线程里的方法
						synchronized(threadstatus){//保证这个方法只能有一个在调用，其他的需要等待
							threadstatus[idxStatus]=0;//重置主函数的引用
//							int i=threadPoolStaus[0]-1;
//							threadPoolStaus[0]=i;
						}
					    runFlag="0";
					}
					this.wait();
				}
				catch(Throwable ex)
				{
					runFlag="0";
					try {
						callBack.dealReturnVal(ex.getMessage());
					} catch (Throwable e) {//回调执行主线程里的方法
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ex.printStackTrace();
				}
		//sleepThread(interTime);//每次循环停顿0.01秒
			}
		}
	}
	
//	public void sleepThread(long timeSeg)
//	{
//		try {
////			System.out.println(timeSeg+"sleeping"+System.currentTimeMillis());
//			Thread.currentThread().sleep(timeSeg);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		}
//	}
}
