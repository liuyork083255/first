package com.sumscope.cdh.webbond.model;

public class ResponseBase<T>
{
    private int code;
    private String message;
    private T result;

    public ResponseBase(){

    }

    public ResponseBase(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public ResponseBase(int code, T result, String message)
    {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getResult()
    {
        return result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }
}
