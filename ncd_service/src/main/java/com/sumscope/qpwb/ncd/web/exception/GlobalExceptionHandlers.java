package com.sumscope.qpwb.ncd.web.exception;

import com.sumscope.qpwb.ncd.global.exception.NcdExcption;
import com.sumscope.service.webbond.common.web.response.ErrorNum;
import com.sumscope.service.webbond.common.web.response.ResponseData;
import com.sumscope.service.webbond.common.web.response.ResponseUtil;
import com.sumscope.service.webbond.common.web.response.VoidData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

//@ControllerAdvice
//public class GlobalExceptionHandlers {
//    public final static Logger logger = LoggerFactory.getLogger("ExceptionHandler");
//
//    @ExceptionHandler(NumberFormatException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public final static ResponseData handleNumberFormatException(NumberFormatException exception, HttpServletRequest request) {
//        logException(ErrorNum.SERVICE_ERROR, exception, request);
//
//        return ResponseUtil.error(ErrorNum.SERVICE_ERROR, exception.getMessage() + "," + exception.getCause(), request);
//    }
//
//    @SuppressWarnings("rawtypes")
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public final static ResponseData handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
//        logInfo(exception.getMessage(), request);
//
//        return ResponseUtil.error(ErrorNum.INVALID_ARGUMENT, exception.getMessage(), request);
//    }
//
//    @ExceptionHandler(HttpMessageConversionException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public final static ResponseData handleHttpMessageConversionException(HttpMessageConversionException exception, HttpServletRequest request) {
//        logInfo(exception.getMessage(), request);
//
//        return ResponseUtil.error(ErrorNum.INVALID_ARGUMENT, exception.getMessage(), request);
//    }
//
//    @ExceptionHandler(TypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public final static ResponseData handleTypeMismatchException(TypeMismatchException exception, HttpServletRequest request) {
//        logInfo(exception.getMessage(), request);
//
//        return ResponseUtil.error(ErrorNum.INVALID_ARGUMENT, exception.getMessage(), request);
//    }
//
//    @SuppressWarnings("rawtypes")
//    @ExceptionHandler(NcdExcption.class)
//    @ResponseBody
//    public final static ResponseData<VoidData> handleBiiException(NcdExcption exception, HttpServletRequest request) {
//        logInfo(exception.getMessage(), request);
//        return ResponseUtil.error(exception.getErrorNum(), exception.getMessage(), request);
//    }
//
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public final static ResponseData handleAllException(RuntimeException exception, HttpServletRequest request) {
//        logException(ErrorNum.SERVICE_ERROR, exception, request);
//
//        return ResponseUtil.error(ErrorNum.SERVICE_ERROR, exception.getMessage() + "," + exception.getCause(), request);
//    }
//
//    private static void logInfo(String infoMsg, HttpServletRequest request) {
//        StringBuilder buffer = new StringBuilder();
//        buffer.append(", parameters:{");
//
//        Map<String, String[]> params = request.getParameterMap();
//        boolean firstParam = true;
//        for (String key : params.keySet()) {
//            if (firstParam) {
//                firstParam = false;
//            } else {
//                buffer.append(", ");
//            }
//
//            buffer.append(key + ":" + Arrays.toString(params.get(key)));
//        }
//        buffer.append("}");
//        String paramsString = buffer.toString();
//
//        String msg = String.format("Request[%s %s%s]: %s", request.getMethod(), request.getRequestURI(), paramsString, infoMsg);
//        logger.info(msg);
//    }
//
//    private static void logException(ErrorNum errorNum, Exception exception, HttpServletRequest request) {
//        String msg = String.format("Error(num=%s) occurred on request[%s %s]", errorNum.toString(), request.getMethod(), request.getRequestURI());
//        logger.error(msg, exception);
//    }
//}
