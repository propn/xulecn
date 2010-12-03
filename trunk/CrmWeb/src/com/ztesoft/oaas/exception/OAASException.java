package com.ztesoft.oaas.exception;

import com.ztesoft.common.exception.CrmException;

public class OAASException extends CrmException {
    public OAASException(String errorCode) {
        super(new OAASError(errorCode));
    }

    public OAASException(String errorCode, Throwable cause) {
        super(new OAASError(errorCode), cause);
    }

    public OAASException(OAASError error) {
        super(error);
    }

    public OAASException(OAASError error, Exception e) {
        super(error, e.getMessage());
    }
	
}
