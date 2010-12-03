package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.crm.business.common.consts.BusiTables;
/**
 * �ͻ�������
 * @author sunny
 *
 */
public class OrderItem extends Ord {
	private List OrderItemDetails = new ArrayList();

	public List getOrderItemDetails() {
		return OrderItemDetails;
	}

	public void setOrderItemDetails(List orderItemDetails) {
		OrderItemDetails = orderItemDetails;
	}
    
    //��map�еĶ�����ID�Ϳͻ���ʶд��List��
    public void initOrderItemDetailsInfo() {
        for (int i = 0; i < this.OrderItemDetails.size(); i++) {
            OrderItemDetail ordItemDetail = (OrderItemDetail)OrderItemDetails.get(i);
            
            if (null == ordItemDetail) {
                continue;
            }
            
            ordItemDetail.set(BusiTables.ORD_ASK_LOG.ord_item_id, get(BusiTables.ORD_ASK_LOG.ord_item_id));
            ordItemDetail.set(BusiTables.ORD_ASK_LOG.cust_ord_id, get(BusiTables.ORD_ASK_LOG.cust_ord_id));
            
        }
    }
	
}
