package com.sumscope.cdh.webbond.request;

import com.sumscope.cdh.webbond.WebBondException;
import com.sumscope.cdh.webbond.model.RequestBase;

/**
 * Created by chengzhang.wang on 2017/4/7.
 */
public class PageableRequest extends RequestBase
{
    public int bboPageNumber = 0;
    public int tradePageNumber = 0;
    public int tradePageSize = 100;
    public int bboPageSize = 100;

    public void check() throws Exception
    {
        if (bboPageNumber < 0)
        {
            throw WebBondException.PredefinedExceptions.StartPageMustNotLessThanZero;
        }
        if (tradePageNumber < 0)
        {
            throw WebBondException.PredefinedExceptions.StartPageMustNotLessThanZero;
        }
        if (bboPageSize < 0)
        {
            throw WebBondException.PredefinedExceptions.PageSizeMustNotLessThanZero;
        }
        if (tradePageSize < 0)
        {
            throw WebBondException.PredefinedExceptions.PageSizeMustNotLessThanZero;
        }
    }
}
