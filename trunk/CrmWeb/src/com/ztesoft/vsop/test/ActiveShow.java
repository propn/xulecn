package com.ztesoft.vsop.test;

import org.apache.axis2.AxisFault;

import com.ztesoft.vsop.ordermonitor.webservice.client.ActiveSVClient;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class ActiveShow {
	public static void main(String[] args) throws Exception {
		if (null != DcSystemParamManager.getInstance().getParameter(
				"VSOP_ACTIVESV_URL")) {
			ActiveSVClient client1 = new ActiveSVClient();
			try {
				System.out.println(client1.active("0", "",
						"<order_id>fdsa</order_id>"));
			} catch (AxisFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("请先启动VSOP的CrmWeb工程");
		}

	}
}
