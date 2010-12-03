package com.ztesoft.crm.salesres.exception;
import com.ztesoft.common.dao.DAOSystemException;

public class BizLogicException extends Exception {
  public BizLogicException() {
  }

  public BizLogicException(String msg) {
    super(msg);
  }

}
