/**
 * 
 */
package com.ztesoft.vsop.engine.vo;

import java.awt.List;
import java.util.ArrayList;

/**
 * <pre>
 * Title:ǩԼ��Ϣ����ͬ��VO
 * Description: ǩԼ��Ϣ����ͬ��VO 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPSignInfoVO {
	/**
	 * streamingNo  ��ˮ��
	 */
	public String  streamingNo;
	/**
	 * TimeStamp   ʱ���
	 */
	public String  TimeStamp;
	/**
	 * OPFlag   ��������  1������  2���޸�  3��ɾ�� 
	 */
	public String  OPFlag;
	/**
	 * ҵ��ǩԼ�����б�
	 */
	public ArrayList    SPSignInfo = new ArrayList();
	
	
	
	/**
	 * @return ���� oPFlag��
	 */
	public String getOPFlag() {
		return OPFlag;
	}
	/**
	 * @param flag ���� oPFlag��
	 */
	public void setOPFlag(String flag) {
		OPFlag = flag;
	}
	/**
	 * @return ���� sPSignInfo��
	 */
	public ArrayList getSPSignInfo() {
		return SPSignInfo;
	}
	/**
	 * @param signInfo ���� sPSignInfo��
	 */
	public void setSPSignInfo(ArrayList signInfo) {
		SPSignInfo = signInfo;
	}
	/**
	 * @return ���� streamingNo��
	 */
	public String getStreamingNo() {
		return streamingNo;
	}
	/**
	 * @param streamingNo ���� streamingNo��
	 */
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	/**
	 * @return ���� timeStamp��
	 */
	public String getTimeStamp() {
		return TimeStamp;
	}
	/**
	 * @param timeStamp ���� timeStamp��
	 */
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
}
