/**
 * 
 */
package org.leixu.iwap.aop.cglib;

/**
 * @author lei
 * 
 */
public class Client {
	public static void main(String[] args) {
		Client c = new Client();
		c.selectivityAuthManager();
	}

	/**
	 * 模拟：没有权限的会员，可以作查询操作
	 */
	public void selectivityAuthManager() {
		System.out
				.println(" the loginer's name is not maurice,so have no permits do manager except do query operator ");
		InfoManager authManager = InfoManagerFactory
				.getSelectivityAuthInstance(new AuthProxy(" maurice1 "));

		doCRUD(authManager);

		separatorLine();

		System.out
				.println(" the loginer's name is  maurice,so have no permits do manager except do query operator ");
		InfoManager authManage2r = InfoManagerFactory
				.getSelectivityAuthInstance(new AuthProxy("maurice"));
		doCRUD(authManage2r);
		separatorLine();
	}

	/**
	 * 对Info做增加／更新／删除／查询操作
	 * 
	 * @param manager
	 */
	private void doCRUD(InfoManager manager) {
		manager.create();
		manager.update();
		manager.delete();
		manager.query();
	}

	/**
	 * 加一个分隔行，用于区分
	 */
	private void separatorLine() {
		System.out.println(" ################################ ");
	}

}
