package com.ztesoft.component.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;

import com.ztesoft.component.job.vo.CrmJobVO;

/**
 * ����������ʼ������
 *
 * @author nieenp
 *
 */
public class CrmJobUtilies {
	private final static Class[] PARAMETERS_FOR_JOB = new Class[] { JobDataMap.class };

	private final static Class[] PARAMETERS_FOR_CREATE = new Class[] { Scheduler.class, Map.class };

	// jobʵ���ౣ�淽����
	protected static final Map JOB_INSTANCE_MAP = new HashMap();

	private static JobLoging logger = JobLoging.getLogger();

	/**
	 * �жϵ�ǰ����ʱ�����ڴ��д���
	 *
	 * @param jobContext
	 * @return
	 */
	public static synchronized boolean isRuning(JobDataMap jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		return JOB_INSTANCE_MAP.containsKey(crmJobVo.getJobId());
	}

	/**
	 * ����ϴ��������ڴ��е��������
	 *
	 * @param jobContext
	 */
	public static synchronized void clear(JobDataMap jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		JOB_INSTANCE_MAP.remove(crmJobVo.getJobId());
	}

	/**
	 * ע���������
	 *
	 * @param crmScheduler
	 * @param jobContext
	 */

	public static synchronized void registor(Scheduler crmScheduler, Map jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		try {
			Class theClass = Class.forName("com.ztesoft.component.job.CrmDefaultJob");
			Object[] arg = new Object[2];
			arg[0] = crmScheduler;
			arg[1] = jobContext;
			Method method = theClass.getMethod("createInstance", PARAMETERS_FOR_CREATE);
			method.invoke(theClass.newInstance(), arg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob�಻����");
		} catch (SecurityException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance����������java��ȫ����" + e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance����������" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance������ȷ" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstanceû�з���Ȩ��" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance������" + e.getMessage());
		} catch (InstantiationException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstanceʵ����ʧ��" + e.getMessage());
		}
	}

	/**
	 * ������������ʵ��
	 *
	 * @param jobContext
	 * @return
	 * @throws Exception
	 */
	public static synchronized IJobTrans createJobProcessInstance(JobDataMap jobContext) throws Exception {

		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		String className = crmJobVo.getJobClassName();
		IJobTrans iJobTrans = null;
		if (JOB_INSTANCE_MAP.get(crmJobVo.getJobId()) != null) {
			iJobTrans = (IJobTrans) JOB_INSTANCE_MAP.get(crmJobVo.getJobId());
			return iJobTrans;
		}
		try {
			Class theClass = Class.forName(className);
			Object theObject = theClass.newInstance();
			Object[] arg = new Object[1];
			arg[0] = jobContext;
			Method method = theObject.getClass().getMethod("getInstance", PARAMETERS_FOR_JOB);
			iJobTrans = (IJobTrans) method.invoke(theObject, arg);
			JOB_INSTANCE_MAP.put(crmJobVo.getJobId(), iJobTrans);
		} catch (ClassNotFoundException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺ" + className + "�����ڣ�" + e.getMessage());
			throw new Exception(e);
		} catch (SecurityException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance����������java��ȫ����" + e.getMessage());
			throw new Exception(e);
		} catch (NoSuchMethodException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance����������" + e.getMessage());
			throw new Exception(e);
		} catch (IllegalArgumentException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance������ȷ" + e.getMessage());
			throw new Exception(e);
		} catch (IllegalAccessException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstanceû�з���Ȩ��" + e.getMessage());
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstance������" + e.getMessage());
			throw new Exception(e);
		} catch (InstantiationException e) {
			logger.loging(crmJobVo, "���ȳ�ʼ���ࣺCrmDefaultJob.createInstanceʵ����ʧ��" + e.getMessage());
			throw new Exception(e);
		}
		return iJobTrans;
	}
}
