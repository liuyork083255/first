package com.sumscope.qpwb.ncd.utils.restful;

import com.sumscope.qpwb.ncd.domain.NullDTO;
import com.sumscope.service.webbond.common.web.response.MetaData;
import com.sumscope.service.webbond.common.web.response.ResponseData;
import com.sumscope.service.webbond.common.web.response.VoidData;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class ResponseUtil {
    public static ResponseData<VoidData> error(int errNum, String errMsg, HttpServletRequest request) {
        return new ResponseData(new MetaData(errNum, errMsg, request.getMethod(), request.getRequestURI()), VoidData.VOID);
    }

    public static NullDTO nullDTO(String msg){
        return new NullDTO(msg);
    }

    public static NullDTO nullDTO(){
        return new NullDTO("success");
    }
}
