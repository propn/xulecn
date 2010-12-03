package com.ztesoft.vsop.engine.service.business;

import java.util.Iterator;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.SpOutMsgHelpDao;
import com.ztesoft.vsop.engine.help.SendActiveHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.SpOutMsgVO;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 送激活服务
 * @author cooltan
 *
 */

public class SendActiveService extends AbstractBusinessService {
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码

	public SendActiveService() {
		this.setServiceCode("");
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.判断是否需要送激活； --不做判断。。
		2.创建激活XML信息；
		3.新增激活队列信息；

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String sysId = order.getOrderSystem();
		//为了让crm提供的受理页面能够对传统+增值鉴权不通过，退订时crm送编码为401，鉴权完后送激活转回201
		if(sysId != null && "401".equals(sysId))order.setOrderSystem("201");
		com.ztesoft.vsop.engine.help.SendActiveHelp aSendActiveHelp=new SendActiveHelp();
		String ret=aSendActiveHelp.createSpiXml(order);
		SpOutMsgHelpDao aSpOutMsgHelpDao=new SpOutMsgHelpDao();
		SpOutMsgVO spOutMsg=new SpOutMsgVO();
		spOutMsg.setOrderId(order.getCustOrderId());
		spOutMsg.setProdNo(order.getAccNbr());
		spOutMsg.setMsgId(order.getCustSoNumber());
		//江西本地化处理，通过本地网标识  从缓存取本地网ID
		if (CrmConstants.JX_PROV_CODE.equals(this.provinceCode)) {	
			spOutMsg.setLanId(this.getLanCodeByLanId(order.getLanId()));//本地网id
		}else{
			spOutMsg.setLanId(order.getLanId());//本地网标识
		}
		
		spOutMsg.setMsg(ret);
		spOutMsg.setSys(order.getOrderSystem());
		aSpOutMsgHelpDao.insertSpiQueue(spOutMsg);
		in.put("resultCode", "0");
		in.put("resultMsg", "成功");
		return in;
	}

	
	 private String getLanCodeByLanId(String lanCode){
		 Map map= DcSystemParamManager.getInstance().getLanid2lanCodeMap();
		 String lanId="";
	        Iterator it = map.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();
	            String  tempId = (String) entry.getKey();
	            String  tempCode = (String)entry.getValue();
	            if(tempCode.equals(lanCode)){
	            	lanId=tempId;
	            	break;
	            }
	        }
	        return lanId;
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
