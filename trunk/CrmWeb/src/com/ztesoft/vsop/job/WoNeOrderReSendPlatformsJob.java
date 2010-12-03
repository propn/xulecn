package com.ztesoft.vsop.job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.order.vo.OrderInfoResponse;
import com.ztesoft.vsop.ordermonitor.bo.WoNeOrderBO;
import com.ztesoft.vsop.ordermonitor.vo.WoNeOrderVO;
import com.ztesoft.vsop.ordermonitor.webservice.client.ActiveSVClient;

/**
 * 子工单重送业务平台
 * 定时任务
 * @author wendm
 *
 */
public class WoNeOrderReSendPlatformsJob extends JobTransaction {
	protected static Logger log = Logger.getLogger(JobTransaction.class);
	private String threadId = "";
	ActiveSVClient activeSVClient ;
	private String args = "";
	private static JobLoging logger = JobLoging.getLogger();

	public WoNeOrderReSendPlatformsJob() {
		super();
		
	}

	public WoNeOrderReSendPlatformsJob(CrmJobVO crmJobVos) {
		super();
		activeSVClient=ActiveSVClient.getInstance();
		this.args = crmJobVos.getJobArgs();
	    System.out.println("参数:"+this.args+"  a");
	}
	
	public void process() {
		log.info("start WoNeOrderReSendPlatformsJob.");
		this.setLocked(true);
		try{
			long s = System.currentTimeMillis();
			// 获取需要送重送平台的子工单	
			Map argsMap=new HashMap();
			argsMap.put("args", args);
			List woNeOrders = DataTranslate._List(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
							"getReSendWoNeOrderList" ,argsMap)) ;		
			log.info("当前获取的线程号： ->" + getThreadId());
			//如果没有待处理的数据，则等待一定的时候再去获取数据
			if(woNeOrders == null || (woNeOrders != null && woNeOrders.size() <= 0)) {
				log.info("can not find any error woneorder,sleep.");
			}else{ //有需要处理的数据，则处理
				log.info("get error woNeOrders,all have " + woNeOrders.size() + " woNeOrders.");
				// 处理子工单
				String searchStr = "<ResultCode>0</ResultCode>";
				if(woNeOrders != null && woNeOrders.size() > 0){
					for (Iterator itr = woNeOrders.iterator(); itr.hasNext();) {
						s = System.currentTimeMillis();
						WoNeOrderVO woNeOrderVO=(WoNeOrderVO) itr.next();
						String workTimeId=woNeOrderVO.getWorkitem_id();
						int sendTimes=0;
						try{
							sendTimes=new Integer(workTimeId).intValue();//重送业务平台次数默认为1次
							if(sendTimes<=0){
								sendTimes=1;
							}else{
								sendTimes=sendTimes+1;
							}
						}catch (Exception e){
							sendTimes=1;//转换报错 重送业务平台次数默认为1次
						}
						
						String activeType = "1";
	    				String mainMsg =woNeOrderVO.getCmd_content();
	    				String helpMsg = "<ne_order_id>"+woNeOrderVO.getNe_order_id()+"</ne_order_id>";
						try {
							String res="";
							if(null!=activeSVClient){
								res=activeSVClient.active(activeType, mainMsg, helpMsg);
							}
							if(null!=res&&!"".equals(res)){
								int successFlag = res.indexOf(searchStr);
								if (successFlag != 0) {//重送业务平台失败，修改重送次数
								Map map=new HashMap();
								map.put("neOrderId", woNeOrderVO.getNe_order_id());
								map.put("sendTimes",new Integer(sendTimes));
								
								//修改重送次数，
								DataTranslate._boolean(
											ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
													"updateWoNeOrderWhenFail" ,map)) ;
								} 
							}
						} catch (Exception e) {
							log.info(e.getMessage());
							Map map=new HashMap();
							map.put("neOrderId", woNeOrderVO.getNe_order_id());
							map.put("sendTimes",sendTimes+"");
							map.put("stateCode",woNeOrderVO.getState_code());
							
							//修改重送次数，
							DataTranslate._boolean(
										ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
												"updateWoNeOrderWhenFail" ,map)) ;
						}
					}
				}
			}
	
		}catch(Exception e){
			e.printStackTrace();
			log.info("WoNeOrderReSendPlatformsJob Exception:"+e.getMessage());
		}
	}

	public IJobTrans getInstance(JobDataMap jobConext) {	
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		WoNeOrderReSendPlatformsJob job = new WoNeOrderReSendPlatformsJob(crmJobVo);
		return job;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	

}
