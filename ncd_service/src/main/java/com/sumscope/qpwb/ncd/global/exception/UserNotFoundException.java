package com.sumscope.qpwb.ncd.global.exception;

import com.sumscope.service.webbond.common.web.response.ErrorNum;

import static com.sumscope.qpwb.ncd.global.constants.NcdConstants.IAM_ERRNUM_USER_NOT_FOUND;

/**
 * @author mengyang.sun
 */
public class UserNotFoundException extends NcdExcption {

    private final static ErrorNum errorNum = new ErrorNum(IAM_ERRNUM_USER_NOT_FOUND, "用户不存在");

    public UserNotFoundException(String message) {
        super(errorNum, message);
    }
}
