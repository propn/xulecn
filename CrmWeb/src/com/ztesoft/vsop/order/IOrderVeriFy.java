package com.ztesoft.vsop.order;

import java.sql.SQLException;

import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;
import com.ztesoft.vsop.order.vo.response.SubscribeToVSOPResp;

public interface IOrderVeriFy {

	boolean auth(SubscribeToVSOPRequest order, SubscribeToVSOPResp subResp) throws Exception;

}
