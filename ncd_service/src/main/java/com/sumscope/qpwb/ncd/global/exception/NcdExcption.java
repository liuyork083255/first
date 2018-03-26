package com.sumscope.qpwb.ncd.global.exception;

import com.sumscope.service.webbond.common.web.response.ErrorNum;

public abstract class NcdExcption extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorNum errorNum = ErrorNum.SUCCESS;

    public ErrorNum getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(ErrorNum errorNum) {
        this.errorNum = errorNum;
    }

    public NcdExcption() {
        super();
    }

    public NcdExcption(String message) {
        super(message);
    }

    public NcdExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public NcdExcption(Throwable cause) {
        super(cause);
    }

    public NcdExcption(ErrorNum errorNum, String message) {
        super(message);
        this.errorNum = errorNum;
    }

    public NcdExcption(ErrorNum errorNum, String message, Throwable cause) {
        super(message, cause);
        this.errorNum = errorNum;
    }
}
