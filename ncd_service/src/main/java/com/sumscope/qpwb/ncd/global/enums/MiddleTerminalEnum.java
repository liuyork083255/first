package com.sumscope.qpwb.ncd.global.enums;

import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

public enum  MiddleTerminalEnum {
    M1("1",NcdConstants.LIMIT_1M),
    M3("2", NcdConstants.LIMIT_3M),
    M6("3", NcdConstants.LIMIT_6M),
    M9("4", NcdConstants.LIMIT_9M),
    Y1("5", NcdConstants.LIMIT_1Y),;

    private MiddleTerminalEnum(String terminalValue, String terminalDesc){
        this.terminalValue = terminalValue;
        this.terminalDesc = terminalDesc;
    }

    private String terminalValue; //中间件terminal值
    private String terminalDesc; //期限
    public String getTerminalValue() {
        return terminalValue;
    }

    public void setTerminalValue(String terminalValue) {
        this.terminalValue = terminalValue;
    }

    public String getTerminal() {
        return terminalDesc;
    }

    public void setTerminal(String terminal) {
        this.terminalDesc = terminal;
    }

    public static MiddleTerminalEnum terminalEnum(String termValue) {
        if (!StringUtils.hasText(termValue)) {
            return null;
        }

        MiddleTerminalEnum[] terminalEnums = MiddleTerminalEnum.values();
        if (ArrayUtils.isEmpty(terminalEnums)) {
            return null;
        }

        for (MiddleTerminalEnum termEnum : terminalEnums) {
            if (termValue.equals(termEnum.getTerminalValue())) {
                return termEnum;
            }
        }
        return null;
    }
}
