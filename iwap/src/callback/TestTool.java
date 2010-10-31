package callback;

public class TestTool {
	
	public void testTime(ICallBack callBack) {
		long begin = System.currentTimeMillis();// 测试起始时间
		callBack.execute();// /进行回调操作
		long end = System.currentTimeMillis();// 测试结束时间
		System.out.println("[use time]:" + (end - begin));// 打印使用时间
	}
	
}
