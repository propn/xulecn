package com.ztesoft.vsop.order;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.vsop.system.service.SystemInfoService;

public class SystemCode {
	public static final String GPROP_VSOP = "100";  //����VSOPϵͳ
	public static final String GPROP_CRM = "101";  //����CRMϵͳ
	public static final String GPROP_PPM = "102";  //����PPMϵͳ
	public static final String GPROP_ISMP_B = "103";  //����ISMP_Bϵͳ
	public static final String GPROP_ISMP_M = "104";  //NMSC������ISMP_M��ϵͳ
	public static final String GPROP_ISMP_W = "105";  //����ISMP_Wϵͳ
	
	public static final String VSOP = "200";  //ʡVSOPϵͳ
	public static final String CRM = "201";  //ʡCRMϵͳ
	public static final String PPM = "202";  //202��ʡ��PPMϵͳ
	public static final String ISMP_B = "203";  //203��ISMP_Bϵͳ
	public static final String ISMP_M = "204";  //204��ISMP_Mϵͳ
	public static final String ISMP_W = "205";  //205��ISMP_Wϵͳ
	public static final String PF = "206";  //206������ͨϵͳ��PF��
	public static final String ISPP = "207";  //207���ۺϼ���ϵͳ��ISPP��
	public static final String OCS = "208";  //208��OCSϵͳ
	public static final String HB = "209";  //209���Ʒ�ϵͳ��HB��
	public static final String ODS = "210";  //210��ODSϵͳ
	public static final String CT10000 = "211";  //211������ϵͳ
	public static final String SEAT_10000 = "212";  //212��10000����ϯ
	public static final String VOICE_10000 = "213";  //213��10000������
	public static final String SMS = "214";  //214������Ӫҵ��
	public static final String WAP = "215";  //215��WAPӪҵ��
	public static final String RBT="303";//303������ƽ̨
	
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
