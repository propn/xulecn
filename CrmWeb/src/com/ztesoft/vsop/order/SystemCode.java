package com.ztesoft.vsop.order;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.vsop.system.service.SystemInfoService;

public class SystemCode {
	public static final String GPROP_VSOP = "100";  //集团VSOP系统
	public static final String GPROP_CRM = "101";  //集团CRM系统
	public static final String GPROP_PPM = "102";  //集团PPM系统
	public static final String GPROP_ISMP_B = "103";  //集团ISMP_B系统
	public static final String GPROP_ISMP_M = "104";  //NMSC（集团ISMP_M）系统
	public static final String GPROP_ISMP_W = "105";  //集团ISMP_W系统
	
	public static final String VSOP = "200";  //省VSOP系统
	public static final String CRM = "201";  //省CRM系统
	public static final String PPM = "202";  //202：省级PPM系统
	public static final String ISMP_B = "203";  //203：ISMP_B系统
	public static final String ISMP_M = "204";  //204：ISMP_M系统
	public static final String ISMP_W = "205";  //205：ISMP_W系统
	public static final String PF = "206";  //206：服务开通系统（PF）
	public static final String ISPP = "207";  //207：综合激活系统（ISPP）
	public static final String OCS = "208";  //208：OCS系统
	public static final String HB = "209";  //209：计费系统（HB）
	public static final String ODS = "210";  //210：ODS系统
	public static final String CT10000 = "211";  //211：网厅系统
	public static final String SEAT_10000 = "212";  //212：10000号座席
	public static final String VOICE_10000 = "213";  //213：10000号语音
	public static final String SMS = "214";  //214：短信营业厅
	public static final String WAP = "215";  //215：WAP营业厅
	public static final String RBT="303";//303：彩铃平台
	
	public static Map systemCodeMapName = new HashMap();
	
	public static String getSystemName(String channelPlayerID) {
		String name = (String)systemCodeMapName.get(channelPlayerID);
		if(null == name) {
			try {
				name = (String)new SystemInfoService().getSystemInfoById(channelPlayerID).get("system_name");
				systemCodeMapName.put(channelPlayerID, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return name;
	}
	
	
	
}
