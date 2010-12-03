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
 * �ͼ������
 * @author cooltan
 *
 */

public class SendActiveService extends AbstractBusinessService {
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���

	public SendActiveService() {
		this.setServiceCode("");
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.�ж��Ƿ���Ҫ�ͼ�� --�����жϡ���
		2.��������XML��Ϣ��
		3.�������������Ϣ��

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String sysId = order.getOrderSystem();
		//Ϊ����crm�ṩ������ҳ���ܹ��Դ�ͳ+��ֵ��Ȩ��ͨ�����˶�ʱcrm�ͱ���Ϊ401����Ȩ����ͼ���ת��201
		if(sysId != null && "401".equals(sysId))order.setOrderSystem("201");
		com.ztesoft.vsop.engine.help.SendActiveHelp aSendActiveHelp=new SendActiveHelp();
		String ret=aSendActiveHelp.createSpiXml(order);
		SpOutMsgHelpDao aSpOutMsgHelpDao=new SpOutMsgHelpDao();
		SpOutMsgVO spOutMsg=new SpOutMsgVO();
		spOutMsg.setOrderId(order.getCustOrderId());
		spOutMsg.setProdNo(order.getAccNbr());
		spOutMsg.setMsgId(order.getCustSoNumber());
		//�������ػ�����ͨ����������ʶ  �ӻ���ȡ������ID
		if (CrmConstants.JX_PROV_CODE.equals(this.provinceCode)) {	
			spOutMsg.setLanId(this.getLanCodeByLanId(order.getLanId()));//������id
		}else{
			spOutMsg.setLanId(order.getLanId());//��������ʶ
		}
		
		spOutMsg.setMsg(ret);
		spOutMsg.setSys(order.getOrderSystem());
		aSpOutMsgHelpDao.insertSpiQueue(spOutMsg);
		in.put("resultCode", "0");
		in.put("resultMsg", "�ɹ�");
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
