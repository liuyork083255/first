package com.sumscope.cdh.webbond.utils;

/**
 * Created by chengzhang.wang on 2017/2/14.
 */
public interface PrimaryKeyGenerator<T,S>
{
    T generatePrimaryKey(S arg);
}
