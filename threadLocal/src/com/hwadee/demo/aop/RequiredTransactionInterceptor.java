package com.hwadee.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hwadee.demo.TransactionHelper;

/**
 * ���������� �������ִ�нӿڵ����ⷽ�����ᱻ���� �Է����ĵ��ý��ɱ���� invoke ��������
 */
public class RequiredTransactionInterceptor implements InvocationHandler {

	// Ŀ�����
	private Object target;

	// �ڹ��췽���д���Ŀ�����
	public RequiredTransactionInterceptor(Object target) {
		this.target = target;
	}

	/**
	 * �ڴ��������ýӿڷ���ʱ������ᱻ�˷�������
	 * 
	 * @param proxy
	 *            �������
	 * @param method
	 *            Ŀ�����ǰ���õķ���
	 * @param args
	 *            ���ô˷���ʱ���ݵĲ���
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// ��Ŀ�귽��������ǰ֯����߼�,�˴���Required��������Ϊ��
		// �жϵ�ǰ�����񻷾����ǿ�ʼһ���������Ǽ������е�����

		boolean existsTransaction = TransactionHelper.existsTransaction();

		if (existsTransaction == false) {
			TransactionHelper.beginTransaction();
			System.out.println("��ǰ���񻷾���û�����񣬿���һ��������");
		} else {
			System.out.println("��ǰ���񻷾��Ѵ������񣬼�������");
		}

		// Ŀ�귽���ķ���ֵ
		Object result = null;

		// �˴�����������Ŀ�����ķ���
		try {
			result = method.invoke(target, args);
		} catch (InvocationTargetException e) {
			// �������Ŀ���쳣�����Ŀ���쳣������ʱ�쳣�����ûع���־
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				TransactionHelper.setRollbackOnly(true);
				System.out.println("��������ʱ�쳣�����񻷾�������Ϊ����ع�");
			} else {
				System.out.println("���ַ�����ʱ�쳣������");
			}
		}

		// ��Ŀ�귽�������ú�֯����߼�
		System.out.println("�жϵ�ǰ�����񻷾�����Ӧ���ύ�����ǻع�����");
		if (existsTransaction == false
				&& TransactionHelper.isRollbackOnly() == false) {
			TransactionHelper.commit();
			System.out.println("�������ύ");
		} else if (existsTransaction == false
				&& TransactionHelper.isRollbackOnly() == true) {
			TransactionHelper.rollback();
			System.out.println("�����ѻع�");
		} else if (existsTransaction == true) {
			System.out.println("��������������ύ��ع�");
		}

		System.out.println("=============================");

		return result;
	}
}
