package com.sumscope.qpwb.ncd.data.model.iam;

public class EmUserAuthz {
    private String functionCode;
    private String modifyTime;
    private String permValue;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPermValue() {
        return permValue;
    }

    public void setPermValue(String permValue) {
        this.permValue = permValue;
    }
}
