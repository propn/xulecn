package callback;

public class TestTool {
	
	public void testTime(ICallBack callBack) {
		long begin = System.currentTimeMillis();// ������ʼʱ��
		callBack.execute();// /���лص�����
		long end = System.currentTimeMillis();// ���Խ���ʱ��
		System.out.println("[use time]:" + (end - begin));// ��ӡʹ��ʱ��
	}
	
}
