package com.ztesoft.component.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;

import com.ztesoft.component.job.vo.CrmJobVO;

/**
 * 任务调度类初始化工具
 *
 * @author nieenp
 *
 */
public class CrmJobUtilies {
	private final static Class[] PARAMETERS_FOR_JOB = new Class[] { JobDataMap.class };

	private final static Class[] PARAMETERS_FOR_CREATE = new Class[] { Scheduler.class, Map.class };

	// job实现类保存方法：
	protected static final Map JOB_INSTANCE_MAP = new HashMap();

	private static JobLoging logger = JobLoging.getLogger();

	/**
	 * 判断当前任务时候在内存中存在
	 *
	 * @param jobContext
	 * @return
	 */
	public static synchronized boolean isRuning(JobDataMap jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		return JOB_INSTANCE_MAP.containsKey(crmJobVo.getJobId());
	}

	/**
	 * 清除上次运行在内存中的运行情况
	 *
	 * @param jobContext
	 */
	public static synchronized void clear(JobDataMap jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		JOB_INSTANCE_MAP.remove(crmJobVo.getJobId());
	}

	/**
	 * 注册任务调度
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
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob类不存在");
		} catch (SecurityException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance方法不符合java安全调用" + e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance方法不存在" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance参数正确" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance没有访问权限" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance不存在" + e.getMessage());
		} catch (InstantiationException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance实例华失败" + e.getMessage());
		}
	}

	/**
	 * 创建任务运行实例
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
			logger.loging(crmJobVo, "调度初始化类：" + className + "不存在：" + e.getMessage());
			throw new Exception(e);
		} catch (SecurityException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance方法不符合java安全调用" + e.getMessage());
			throw new Exception(e);
		} catch (NoSuchMethodException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance方法不存在" + e.getMessage());
			throw new Exception(e);
		} catch (IllegalArgumentException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance参数正确" + e.getMessage());
			throw new Exception(e);
		} catch (IllegalAccessException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance没有访问权限" + e.getMessage());
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance不存在" + e.getMessage());
			throw new Exception(e);
		} catch (InstantiationException e) {
			logger.loging(crmJobVo, "调度初始化类：CrmDefaultJob.createInstance实例华失败" + e.getMessage());
			throw new Exception(e);
		}
		return iJobTrans;
	}
}
