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
	 * ģ�⣺û��Ȩ�޵Ļ�Ա����������ѯ����
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
	 * ��Info�����ӣ����£�ɾ������ѯ����
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
	 * ��һ���ָ��У���������
	 */
	private void separatorLine() {
		System.out.println(" ################################ ");
	}

}
