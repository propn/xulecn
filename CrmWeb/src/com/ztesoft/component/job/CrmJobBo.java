package com.ztesoft.component.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.job.dao.CrmJobClustorDAO;
import com.ztesoft.component.job.dao.CrmJobClustorStateDAO;
import com.ztesoft.component.job.dao.CrmJobDAO;
import com.ztesoft.component.job.dao.CrmJobRunLogDAO;
import com.ztesoft.component.job.dao.JobDAOFactory;
import com.ztesoft.component.job.vo.CrmJobClustorStateVO;
import com.ztesoft.component.job.vo.CrmJobClustorVO;
import com.ztesoft.component.job.vo.CrmJobRunLogVO;
import com.ztesoft.component.job.vo.CrmJobVO;

public class CrmJobBo {
	private static JobLoging logger = JobLoging.getLogger();

	/**
	 * ע��Ⱥ����������
	 * 
	 * @param clustorId
	 * @return
	 */
	public static synchronized int clustorRegistor(String clustorId) {
		clustorDestroy(clustorId);
		CrmJobClustorVO crmJobClustorVO = new CrmJobClustorVO();
		crmJobClustorVO.setClustorId(clustorId);
		crmJobClustorVO.setItime(DateFormatUtils.getFormatedDateTime());
		crmJobClustorVO.setOtime("2030-01-01 00:00:00");
		CrmJobClustorDAO crmJobClustorDAO = JobDAOFactory.getCrmJobClustorDAO();
		int i = 0;
		try {
			crmJobClustorDAO.insert(crmJobClustorVO);
			i = 1;
		} catch (Exception e) {
			logger.loging("Ⱥ��ע��ʧ�ܣ�" + e.getMessage());
			i = -1;
		}
		return i;
	}

	/**
	 * ��Ⱥ����������ɾ��
	 * 
	 * @param clustorId
	 * @return
	 */
	public static synchronized int clustorDestroy(String clustorId) {
		int i = 0;
		try {
			CrmJobClustorDAO crmJobClustorDAO = JobDAOFactory.getCrmJobClustorDAO();
			crmJobClustorDAO.delete(clustorId);
			CrmJobClustorStateDAO CrmJobClustorStateDAO = JobDAOFactory.getCrmJobClustorStateDAO();
			CrmJobClustorStateDAO.deleteByCond("clustor_id='" + clustorId + "'");
			i = 1;
		} catch (Exception e) {
			i = -1;
			logger.loging("Ⱥ��ע��ʧ�ܣ�" + e.getMessage());
		}
		return i;
	}

	/**
	 * ��Ⱥ����������ɾ��ָ�������������Ϣ
	 * 
	 * @param clustorId
	 * @return
	 */
	public static synchronized int jobDestroy(String clustorId, String jobId) {
		int i = 0;
		try {
			CrmJobClustorDAO crmJobClustorDAO = JobDAOFactory.getCrmJobClustorDAO();
			crmJobClustorDAO.delete(clustorId);
			CrmJobClustorStateDAO CrmJobClustorStateDAO = JobDAOFactory.getCrmJobClustorStateDAO();
			CrmJobClustorStateDAO.deleteByCond("clustor_id='" + clustorId + "' and job_id =" + jobId);
			i = 1;
		} catch (Exception e) {
			i = -1;
			logger.loging("Ⱥ��ע��ʧ�ܣ�" + e.getMessage());
		}
		return i;
	}

	/**
	 * ���Ⱥ����Ч��Ϣ
	 */
	public static synchronized void clearClustor() {
		try {
			CrmJobClustorDAO crmJobClustorDAO = JobDAOFactory.getCrmJobClustorDAO();
			crmJobClustorDAO.deleteByCond(" clustor_id not in(select clustor_id from crm_job_clustor_state)");
		} catch (Exception e) {
			logger.loging("���Ⱥ����Ч��Ϣʧ�ܣ�" + e.getMessage());
		}
	}

	public static synchronized List getRuningInfo() {
		try {
			return JobDAOFactory.getCrmJobDAO().findRuningByCond();
		} catch (Exception e) {
			logger.loging("��ѯ��ǰ��������ʧ�ܣ�" + e.getMessage());
		}
		return new ArrayList();
	}

	public static synchronized void jobLoging(CrmJobVO crmJobVo, String message) {
		CrmJobClustorStateDAO CrmJobClustorStateDAO = JobDAOFactory.getCrmJobClustorStateDAO();
		try {
			List runinfo = CrmJobClustorStateDAO.findByCond(" job_id =" + crmJobVo.getJobId() + " and clustor_id='" + CrmJobInstanceManager.clustorId + "'");
			if (runinfo.size() > 0) {
				CrmJobClustorStateVO vo = (CrmJobClustorStateVO) runinfo.get(0);
				vo.setLastRunmsg(crmJobVo.getLastExeuteReSult());
				vo.setRunState("��ʼ����");
				vo.setLastRunEndtime(crmJobVo.getLastExeuteEndTime());
				vo.setLastRuntime(crmJobVo.getLastExeuteBeginTime());
				CrmJobClustorStateDAO.updateRuningMessage(CrmJobInstanceManager.clustorId, crmJobVo.getJobId(),vo);
			} else {
				CrmJobClustorStateVO crmJobClustorStateVO = new CrmJobClustorStateVO();
				crmJobClustorStateVO.setClustorId(CrmJobInstanceManager.clustorId);
				crmJobClustorStateVO.setLastRunEndtime(crmJobVo.getLastExeuteEndTime());
				crmJobClustorStateVO.setLastRuntime(crmJobVo.getLastExeuteBeginTime());
				crmJobClustorStateVO.setLastRunmsg(crmJobVo.getLastExeuteReSult());
				crmJobClustorStateVO.setJobId(crmJobVo.getJobId());
				CrmJobClustorStateDAO.insert(crmJobClustorStateVO);

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.loging("ϵͳ��־��¼����:" + e.getMessage());
		}
	}

	/**
	 * �жϵ��������Ƿ��������
	 * 
	 * @param clustorId
	 * @return
	 */
	public static synchronized boolean jobisCanRun(String clustorId,CrmJobVO crmJobVo, String LastRuntime) {
		CrmJobClustorStateDAO CrmJobClustorStateDAO = JobDAOFactory.getCrmJobClustorStateDAO();
		try {
			// ���ΪȺ����ʽ
			if (crmJobVo.getJobClustored().equalsIgnoreCase("1")) {
				List runinfo = CrmJobClustorStateDAO.findByCond(" job_id =" + crmJobVo.getJobId());
				if (runinfo.size() > 0) {
					CrmJobClustorStateVO vo = (CrmJobClustorStateVO) runinfo.get(0);

					/* ֻ������һ������ */
					for (int i = 1, cnt = runinfo.size(); i < cnt; i++) {
						CrmJobClustorStateVO vo1 = (CrmJobClustorStateVO) runinfo.get(i);
						CrmJobClustorStateDAO.delete(vo1.getClustorId(), vo1.getJobId());
					}
					// ������Ǳ�����ģ����˳�ϵͳ
					if (!vo.getClustorId().equalsIgnoreCase(clustorId)) {
						return false;
					}
					// if (vo.getJobChecktime().compareToIgnoreCase(LastRuntime)
					// > 0) {
					// vo.setJobChecktime(LastRuntime);
					// CrmJobClustorStateDAO.updateCheckTime(clustorId,
					// crmJobVo.getJobId(), vo);
					// return false;
					// }
					vo.setJobChecktime(LastRuntime);
					CrmJobClustorStateDAO.updateCheckTime(clustorId, crmJobVo.getJobId(), vo);

				} else {
					CrmJobClustorStateVO crmJobClustorStateVO = new CrmJobClustorStateVO();
					crmJobClustorStateVO.setClustorId(clustorId);
					crmJobClustorStateVO.setJobChecktime(LastRuntime);
					crmJobClustorStateVO.setJobId(crmJobVo.getJobId());
					CrmJobClustorStateDAO.insert(crmJobClustorStateVO);
				}

			} else {
				List runinfo = CrmJobClustorStateDAO.findByCond(" job_id ="	+ crmJobVo.getJobId() + " and clustor_id='" + clustorId + "'");

				if (runinfo.size() > 0) {

					CrmJobClustorStateVO vo = (CrmJobClustorStateVO) runinfo.get(0);
					// if (vo.getJobChecktime().compareToIgnoreCase(LastRuntime)
					// > 0) {
					// vo.setJobChecktime(LastRuntime);
					// CrmJobClustorStateDAO.updateCheckTime(clustorId,
					// crmJobVo.getJobId(), vo);
					// return false;
					// }
					vo.setJobChecktime(LastRuntime);
					CrmJobClustorStateDAO.updateCheckTime(clustorId, crmJobVo.getJobId(), vo);
				} else {
					CrmJobClustorStateVO crmJobClustorStateVO = new CrmJobClustorStateVO();
					crmJobClustorStateVO.setClustorId(clustorId);
					crmJobClustorStateVO.setJobChecktime(LastRuntime);
					crmJobClustorStateVO.setJobId(crmJobVo.getJobId());
					CrmJobClustorStateDAO.insert(crmJobClustorStateVO);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.loging("�ж��������´���:" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * ��ȡ��������б�
	 * 
	 * @return
	 */
	public List getJobList() {
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		/* ȡ��ǰ��Ч�Ϸ��ĵ��ݽ���ע�� */
		String condInformix = " job_state = 1 and job_start_day <= extend(current,year to day) and  job_terminate_day >= extend(current,year to day) and (clustor_id='"+ CrmJobInstanceManager.clustorId + "' or job_clustored =0)";
		String condOracle = " job_state = 1 and (job_start_day <= sysdate or job_start_run=1) and  job_terminate_day >= sysdate and (clustor_id='" + CrmJobInstanceManager.clustorId + "' or job_clustored =0)";
		List ret = new ArrayList();
		try {
			List current = new ArrayList();
			if (CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equalsIgnoreCase(CrmConstants.DB_TYPE_ORACLE)) {
				current = crmJobDAO.findByCond(condOracle);
			} else {
				current = crmJobDAO.findByCond(condInformix);
			}
			for (int i = 0, cnt = current.size(); i < cnt; i++) {
				CrmJobVO crmJobVo = (CrmJobVO) current.get(i);
				Map ctx = new HashMap();
				ctx.put(CrmJob.JOB_ID_KEY, crmJobVo);
				ret.add(ctx);
			}
			return ret;
		} catch (Exception e) {
			return new ArrayList();
		}
	}

	public void insert(CrmJobVO crmJobVo) {
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		try {
			crmJobDAO.insert(crmJobVo);
		} catch (Exception e) {
			logger.loging(crmJobVo, e.getMessage());
		}
	}

	/**
	 * дϵͳ������־
	 * 
	 * @param crmJobRunLogVO
	 */
	public synchronized void loging(CrmJobVO crmJobVo, int state, String msg) {
		CrmJobRunLogDAO crmJobLogDAO = JobDAOFactory.getCrmJobRunLogDAO();
		CrmJobRunLogVO crmJobRunLogVO = new CrmJobRunLogVO();
		crmJobRunLogVO.setJobId(crmJobVo.getJobId());
		crmJobRunLogVO.setJobLogId(System.currentTimeMillis() + "");
		crmJobRunLogVO.setJobLogTime(DateFormatUtils.getFormatedDateTime());
		crmJobRunLogVO.setJobRunState("1");
		crmJobRunLogVO.setJobRunMsg(msg);
		crmJobRunLogVO.setJobLogId(""+ (System.currentTimeMillis()+ Long.parseLong(crmJobVo.getJobId()) + msg.hashCode()));
		try {
			crmJobLogDAO.insert(crmJobRunLogVO);
		} catch (Exception e) {
			// logger.debug("д��־ʧ�ܣ�", e);
		}
	}

	/**
	 * ��ѯ��������
	 * 
	 * @param conditionMap
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public synchronized PageModel getJobPageModal(Map conditionMap,int pageIndex, int pageSize) {
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		StringBuffer cond = new StringBuffer("");
		if ((conditionMap.get("jobtype") != null) && (conditionMap.get("jobtype").toString().trim().length() > 0.)) {
			if (!conditionMap.get("jobtype").toString().equalsIgnoreCase("-1")) {
				cond.append(" job_type=" + conditionMap.get("jobtype"));
			}
		}

		if ((conditionMap.get("jobState") != null)&& (conditionMap.get("jobState").toString().trim().length() > 0.)) {
			if (!conditionMap.get("jobState").toString().equalsIgnoreCase("-1")) {
				if (cond.length() == 0) {
					cond.append(" job_state=" + conditionMap.get("jobState"));
				} else {
					cond.append(" and job_state="+ conditionMap.get("jobState"));
				}
			}
		}
		if ((conditionMap.get("endBeginTime") != null) && (conditionMap.get("endBeginTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" job_terminate_day >=DatabaseFunction.to_date('" + conditionMap.get("endBeginTime").toString().trim()+ " 00:00:00");

			} else {
				cond.append(" and job_terminate_day >=DatabaseFunction.to_date('" + conditionMap.get("endBeginTime").toString().trim() + " 00:00:00");

			}
		}
		if ((conditionMap.get("endEndTime") != null)&& (conditionMap.get("endEndTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" job_terminate_day <= DatabaseFunction.to_date('"+ conditionMap.get("endEndTime").toString().trim() + " 23:59:59");

			} else {
				cond.append(" and job_terminate_day <= DatabaseFunction.to_date('"+ conditionMap.get("endEndTime").toString().trim() + " 23:59:59");
			}
		}
		if (cond.length() == 0) {
			cond.append("1=1");
		}
		PageModel pageModel = PageHelper.popupPageModel(crmJobDAO, cond.toString(), pageIndex, pageSize);
		return pageModel;
	}

	public synchronized PageModel getJobLogingPageModal(Map conditionMap,int pageIndex, int pageSize) {
		CrmJobRunLogDAO crmJobDAO = JobDAOFactory.getCrmJobRunLogDAO();
		StringBuffer cond = new StringBuffer("");
		if ((conditionMap.get("jobid") != null)&& (conditionMap.get("jobid").toString().trim().length() > 0)) {
			String[] offers = (conditionMap.get("jobid").toString()).split(",");
			StringBuffer offerC = new StringBuffer("");
			for (int i = 0, cnt = offers.length; i < cnt; i++) {
				if (offers[i].trim().length() > 0) {
					if (offerC.length() > 0) {
						offerC.append("," + offers[i]);
					} else {
						offerC.append(offers[i]);
					}
				}
			}

			if (offerC.length() > 0) {
				cond.append(" a.job_id in(" + offerC.toString() + ")");
			}
		}
		if ((conditionMap.get("endBeginTime") != null) && (conditionMap.get("endBeginTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" a.job_log_time >=DatabaseFunction.to_date('" + conditionMap.get("endBeginTime").toString().trim() + " 00:00:00");

			} else {
				cond.append(" and a.job_log_time >=DatabaseFunction.to_date('" + conditionMap.get("endBeginTime").toString().trim() + " 00:00:00");
			}
		}
		if ((conditionMap.get("endEndTime") != null)&& (conditionMap.get("endEndTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" a.job_log_time <= DatabaseFunction.to_date('" + conditionMap.get("endEndTime").toString().trim()+ " 23:59:59");

			} else {
				cond.append(" and a.job_log_time <= DatabaseFunction.to_date('" + conditionMap.get("endEndTime").toString().trim()+ " 23:59:59");
			}
		}
		if (cond.length() > 0) {
			cond.insert(0, " and ");
		}
		PageModel pageModel = PageHelper.popupPageModel(crmJobDAO, cond.toString(), pageIndex, pageSize);
		return pageModel;
	}

	public synchronized int deleteJobLoging(Map conditionMap) {
		CrmJobRunLogDAO crmJobDAO = JobDAOFactory.getCrmJobRunLogDAO();
		StringBuffer cond = new StringBuffer("");
		if ((conditionMap.get("jobid") != null)&& (conditionMap.get("jobid").toString().trim().length() > 0)) {
			String[] offers = conditionMap.get("jobid").toString().split(",");
			StringBuffer offerC = new StringBuffer("");
			for (int i = 0, cnt = offers.length; i < cnt; i++) {
				if (offers[i].trim().length() > 0) {
					if (offerC.length() > 0) {
						offerC.append("," + offers[i]);
					} else {
						offerC.append(offers[i]);
					}
				}
			}

			if (offerC.length() > 0) {
				cond.append(" job_id in(" + offerC.toString() + ")");
			}
		}
		if ((conditionMap.get("endBeginTime") != null)&& (conditionMap.get("endBeginTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" job_log_time >=DatabaseFunction.to_date('" + conditionMap.get("endBeginTime").toString().trim()+ " 00:00:00");

			} else {
				cond.append(" and job_log_time >=DatabaseFunction.to_date('"+ conditionMap.get("endBeginTime").toString().trim()+ " 00:00:00");

			}
		}
		if ((conditionMap.get("endEndTime") != null)&& (conditionMap.get("endEndTime").toString().trim().length() > 0.)) {
			if (cond.length() == 0) {
				cond.append(" job_log_time <= DatabaseFunction.to_date('"+ conditionMap.get("endEndTime").toString().trim()+ " 23:59:59");

			} else {
				cond.append(" and job_log_time <= DatabaseFunction.to_date('"+ conditionMap.get("endEndTime").toString().trim()+ " 23:59:59");
			}
		}
		if (cond.length() == 0) {
			return 0;
		}
		crmJobDAO.deleteByCond(cond.toString());
		return 1;
	}

	public synchronized String saveCrmJob(CrmJobVO crmJobVO, int saveType) {
		String valid = validJOb(crmJobVO);
		if ((valid != null) && (valid.length() > 0)) {
			return valid;
		}
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		if ((crmJobVO.getJobId() == null)|| (crmJobVO.getJobId().trim().equalsIgnoreCase(""))) {
			crmJobVO.setJobId(System.currentTimeMillis() + "");
			crmJobDAO.insert(crmJobVO);
		} else {
			crmJobDAO.update(crmJobVO.getJobId(), crmJobVO);
		}
		return "����ɹ�";
	}

	public synchronized String validJOb(CrmJobVO crmJobVO) {

		if (crmJobVO.getJobType().equalsIgnoreCase("0")) {
			try {
				if (Integer.parseInt(crmJobVO.getJobInterval()) <= 0) {
					return "ִ�м��ֻ��Ϊ���֣���ֵ�������0";
				}
			} catch (Exception e) {
				return "ִ�м��ֻ��Ϊ�������͵ģ���λΪ��";
			}
		}
		try {
			DateFormatUtils.parseStrToDateTime(crmJobVO.getJobStartDay());
		} catch (Exception e) {
			return "��Ч���ڸ�ʽ����ȷ";
		}
		try {
			DateFormatUtils.parseStrToDateTime(crmJobVO.getJobStartDay());
		} catch (Exception e) {
			return "ʧЧ���ڸ�ʽ����ȷ";
		}
		if (crmJobVO.getJobType().equalsIgnoreCase("1")) {
			try {
				DateFormatUtils.parseStrToDateTime("2005-01-01 " + crmJobVO.getJobRuntime().trim());
			} catch (Exception e) {
				return "ִ��ʱ���ʽ����ȷ����ʽӦ��ΪHH:MM:SS";
			}
			/*
			 * try { String temp = crmJobVO.getJobRule(); temp.replace(',','1');
			 * Long.parseLong(temp); } catch (Exception e) { return "�����ʽ����ȡ"; }
			 */
		}
		// �ж��������Ƿ�Ϊ��
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		String[] param = new String[2];
		if ((crmJobVO.getJobId() != null)&& (crmJobVO.getJobId().trim().length() > 0)) {
			param[0] = crmJobVO.getJobId();
		} else {
			param[0] = "0";
		}
		param[1] = crmJobVO.getJobName();
		List ret = crmJobDAO.findBySql("SELECT job_id,job_name,job_group_name,job_class_name,job_type,job_interval,job_method,job_rule,job_runtime,job_start_day,job_terminate_day,job_desc,job_state FROM CRM_JOB where job_id != ? and job_name=?",param);
		if (ret.size() > 0) {
			return "�������Ʋ����ظ�";
		}
		return "";
	}

	/**
	 * ����״̬
	 * 
	 * @param jobId
	 * @param state
	 */
	public synchronized void modifyState(String jobId, String state) {
		CrmJobDAO crmJobDAO = JobDAOFactory.getCrmJobDAO();
		CrmJobVO crmJobVO = crmJobDAO.findByPrimaryKey(jobId);
		crmJobVO.setJobState(state);
		crmJobDAO.update(jobId, crmJobVO);
	}

}
