package com.ztesoft.crm.salesres.exception;

import com.ztesoft.common.dao.DAOSystemException;

public class DuplicateException extends LogicInfoException{
  public DuplicateException() {
  }

  public DuplicateException(String msg) {
    super(msg);
  }
}
