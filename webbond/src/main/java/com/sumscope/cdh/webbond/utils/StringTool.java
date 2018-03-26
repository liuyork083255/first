package com.sumscope.cdh.webbond.utils;

import java.util.Collection;
import java.util.List;

public class StringTool
{
    private StringTool()
    {
    }

    public static boolean contains(Collection<String> list, String value)
    {
        return list.stream().anyMatch(str -> str.equals(value));
    }

    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static boolean isEmptyList(List<?> items)
    {
        return items == null || items.isEmpty();
    }

    public static String right(String str, int size)
    {
        if (isEmpty(str) || str.length() <= size)
            return str;
        return str.substring(str.length() - size, str.length());
    }
}