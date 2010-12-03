/**
 * 
 */
package com.ztesoft.vsop.engine.vo;

import java.awt.List;
import java.util.ArrayList;

/**
 * <pre>
 * Title:签约信息能力同步VO
 * Description: 签约信息能力同步VO 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SPSignInfoVO {
	/**
	 * streamingNo  流水号
	 */
	public String  streamingNo;
	/**
	 * TimeStamp   时间戳
	 */
	public String  TimeStamp;
	/**
	 * OPFlag   操作类型  1：增加  2：修改  3：删除 
	 */
	public String  OPFlag;
	/**
	 * 业务签约能力列表
	 */
	public ArrayList    SPSignInfo = new ArrayList();
	
	
	
	/**
	 * @return 返回 oPFlag。
	 */
	public String getOPFlag() {
		return OPFlag;
	}
	/**
	 * @param flag 设置 oPFlag。
	 */
	public void setOPFlag(String flag) {
		OPFlag = flag;
	}
	/**
	 * @return 返回 sPSignInfo。
	 */
	public ArrayList getSPSignInfo() {
		return SPSignInfo;
	}
	/**
	 * @param signInfo 设置 sPSignInfo。
	 */
	public void setSPSignInfo(ArrayList signInfo) {
		SPSignInfo = signInfo;
	}
	/**
	 * @return 返回 streamingNo。
	 */
	public String getStreamingNo() {
		return streamingNo;
	}
	/**
	 * @param streamingNo 设置 streamingNo。
	 */
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	/**
	 * @return 返回 timeStamp。
	 */
	public String getTimeStamp() {
		return TimeStamp;
	}
	/**
	 * @param timeStamp 设置 timeStamp。
	 */
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
}
