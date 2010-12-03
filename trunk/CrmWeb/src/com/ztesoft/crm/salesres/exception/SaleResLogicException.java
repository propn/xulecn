package com.ztesoft.crm.salesres.exception;

import com.ztesoft.common.dao.DAOSystemException;

/**
 *
 * @Classname     : SaleResLogicException
 * @Description   : 营销资源接口用到的异常，用于回滚标志
 * @Copyright     : 2006 ZTEsoft.
 * @Author        : peter
 * @Create Date   : Mar 3, 2008
 *
 * @Last Modified :
 * @Modified by   :
 * @Version       : 1.0
 */
public class SaleResLogicException extends DAOSystemException {
	public SaleResLogicException(String msg) { super(msg); }
}
