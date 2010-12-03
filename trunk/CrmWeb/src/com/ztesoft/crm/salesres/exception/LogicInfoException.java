package com.ztesoft.crm.salesres.exception;

import com.ztesoft.common.dao.DAOSystemException;

public class LogicInfoException extends DAOSystemException{
  public LogicInfoException() {
  }

  public LogicInfoException(String msg) {
    super(msg);
  }
  
  public LogicInfoException(String msg, Throwable cause) {
      super(msg+"\n  nest exception:"+cause.getMessage(), cause);
  }
  
}
