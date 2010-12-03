package com.ztesoft.vsop.ordermonitor.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.ordermonitor.dao.OrderProcTimeDao;
/**
 * <pre>
 * Title:
 * Description: 程序功能的描述  订购关系比对数据持久化操作
 * </pre>
 * @author 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：caozj  修改日期:20100920     修改内容: SQL语法增加informix数据库语法
 * </pre>
 */
public class OrderProcTimeBo extends DictAction {
	public PageModel searchOrderProcTime(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		String beginTime = Const.getStrValue(param, "begin_time");
		String finishTime = Const.getStrValue(param, "finish_time");
		List partition = this.getPartition(beginTime, finishTime);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "acc_nbr")) {
			whereCond.append(" and acc_nbr=? ");
			para.add(Const.getStrValue(param, "acc_nbr"));
		}
		// 存在产品主键序列串时
		if (Const.containStrValue(param, "product_ids")) {
			String[] ids = Const.getStrValue(param, "product_ids").split(",");
			whereCond.append(" and product_id in( ");
			for (int i = 0; i < ids.length; i++) {
				whereCond.append(" ? ");
				if ((i + 1) != ids.length) {
					whereCond.append(",");
				}
				para.add(ids[i]);
			}
			whereCond.append(" )");
		}
		if (Const.containStrValue(param, "system_code")) {
			whereCond.append(" and system_code=? ");
			para.add(Const.getStrValue(param, "system_code"));
		}
		if (Const.containStrValue(param, "vsop_n_time")) {
			whereCond.append(" and vsop_proc_time>? ");
			para.add(Const.getStrValue(param, "vsop_n_time"));
		}
		if (Const.containStrValue(param, "platform_n_time")) {
			whereCond.append(" and platform_proc_time>? ");
			para.add(Const.getStrValue(param, "platform_n_time"));
		}
		if (Const.containStrValue(param, "begin_time") && Const.containStrValue(param, "finish_time") ) {
			String begin_time=Const.getStrValue(param, "begin_time");
			begin_time +=" 00:00:00";
			String finish_time=Const.getStrValue(param, "finish_time");
			finish_time +=" 00:00:00";
			//判断数据库类型 修改日期格式
		
				whereCond.append(" and handle_time between to_date('"+begin_time+"',"+DatabaseFunction.getDataFormat(2)+")");
				whereCond.append(" and to_date('"+finish_time+"',"+DatabaseFunction.getDataFormat(2)+")");
			
		}
		// 加入分区
		if (null != partition && partition.size() > 0) {
			int size = partition.size();
			if (size == 1) {
				whereCond.append(" and partition_id=? ");
				para.add((String) partition.get(0));
			}
			if (size == 2) {
				whereCond.append(" and partition_id in(?,?) ");
				for (Iterator iter = partition.iterator(); iter.hasNext();) {
					para.add(iter.next());
				}
			}
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		OrderProcTimeDao dao = new OrderProcTimeDao();
		PageModel result = dao.searchByCond(whereCond.toString(), para, pageIndex, pageSize);
		return result;

	}

	// 判断partition
	private List getPartition(String beginTime, String finishTime) {
		String partB = null;
		String partF = null;
		if (null != beginTime && !"".equals(beginTime)) {
			String[] beg = beginTime.split("-");
			if (beg.length > 1) {
				Integer b=Integer.valueOf(beg[1]);
				partB = b.toString();
			}
		}
		if (null != finishTime && !"".equals(finishTime)) {
			String[] fin = finishTime.split("-");
			if (fin.length > 1) {
				Integer f=Integer.valueOf(fin[1]);
				partF = f.toString();
			}
		}
		List partition = new ArrayList();
		
		if(null!=partB && null!=partF && partB.equals(partF)){
			partition.add(partB);
			return partition;
		}
		if (null != partB) {
			partition.add(partB);
		}
		if (null != partF) {
			partition.add(partF);
		}
		return partition;
	}

}
