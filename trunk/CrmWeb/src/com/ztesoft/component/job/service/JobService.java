package com.ztesoft.component.job.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.buffalo.BaseService;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.job.CrmJobBo;
import com.ztesoft.component.job.CrmJobInstanceCache;
import com.ztesoft.component.job.CrmJobInstanceManager;
import com.ztesoft.component.job.vo.CrmJobVO;

public class JobService extends BaseService {
	/**
	 * ��ѯ�����б�
	 * 
	 * @param jobtype
	 * @param endBeginTime
	 * @param endEndTime
	 * @param jobState
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel getJobPageModal(String jobtype, String endBeginTime, String endEndTime, String jobState, int pageIndex, int pageSize) {
		Map param = new HashMap();
		param.put("jobtype", jobtype);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		param.put("jobState", jobState);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.getJobPageModal(param, pageIndex, pageSize);
	}

	/**
	 * ��ѯ��־
	 * 
	 * @param jobid
	 * @param endBeginTime
	 * @param endEndTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel getJobLoging(String jobid, String endBeginTime, String endEndTime, int pageIndex, int pageSize) {
		Map param = new HashMap();
		param.put("jobid", jobid);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.getJobLogingPageModal(param, pageIndex, pageSize);
	}

	/**
	 * �����־
	 * 
	 * @param jobid
	 * @param endBeginTime
	 * @param endEndTime
	 * @return
	 */
	public int clearLog(String jobid, String endBeginTime, String endEndTime) {
		Map param = new HashMap();
		param.put("jobid", jobid);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.deleteJobLoging(param);
	}

	public String saveCrmJobVO(CrmJobVO crmJobVO, int saveType) {
		CrmJobBo crmJobBo = new CrmJobBo();

		return crmJobBo.saveCrmJob(crmJobVO, saveType);
	}

	/**
	 * ��������
	 * 
	 * @param jobList
	 * @return
	 */
	public int activeJob(String jobList) {
		String[] offers = jobList.split(",");
		CrmJobBo crmJobBo = new CrmJobBo();
		int count = 0;
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "1");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * ʧЧ
	 * 
	 * @param jobList
	 * @return
	 */
	public int invalidJob(String jobList) {
		String[] offers = jobList.split(",");
		int count = 0;
		CrmJobBo crmJobBo = new CrmJobBo();
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "2");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * ������Ч
	 * 
	 * @param jobList
	 * @return
	 */
	public int revalidJob(String jobList) {
		String[] offers = jobList.split(",");
		int count = 0;
		CrmJobBo crmJobBo = new CrmJobBo();
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "0");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * ��ȡ��ǰ���е�����
	 * 
	 * @return
	 */
	public List getCurrentRuningJob(String state) {

		return CrmJobInstanceCache.getRunningJob();
	}

	/**
	 * �����������
	 * 
	 * @return 0 ������������1���������ɹ� 2��������ֹͣ��
	 */
	public int startup() {
//		int port =  this.getRequest().getServerPort();
		//����Ϊbuffalo 2.0�ķ���
		int port = RequestContext.getContext().getHttpRequest().getServerPort();
		String ipAddress = "127.0.0.1";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ipAddress = inet.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		CrmJobInstanceManager.clustorId = ipAddress + ":" + port;

		if ((CrmJobInstanceManager.isShutDonw) && (!CrmJobInstanceManager.isStopped)) {
			return 2;
		}

		if (CrmJobInstanceManager.isRunning) {
			return 0;
		} else {
			CrmJobInstanceManager.startup();
		}
		return 1;
	}

	/**
	 * ֹͣ�������
	 * 
	 * @return 0������ֹͣ 1����ֹͣ�ɹ�
	 */
	public int shutdown() {

		if (CrmJobInstanceManager.isRunning) {
			CrmJobInstanceManager.shutdown();
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * ��ȡϵͳ����״̬
	 * 
	 * @return 1������,0������ֹͣ
	 */
	public int getCurrentState() {

		if ((CrmJobInstanceManager.isShutDonw) && (!CrmJobInstanceManager.isStopped)) {
			return 2;
		}

		if (CrmJobInstanceManager.isRunning) {
			return 1;
		} else {
			return 0;
		}
	}
}
