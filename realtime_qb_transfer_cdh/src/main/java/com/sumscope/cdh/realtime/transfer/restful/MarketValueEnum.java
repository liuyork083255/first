package com.sumscope.cdh.realtime.transfer.restful;

/**
 * Created by liu.yang on 2017/10/31.
 */
public enum MarketValueEnum {
    CIB(1 << 0), SZE(1 << 1), SSE(1 << 2), SHFE(1 << 3), DCE(1 << 4), CZCE(1 << 5), CFFEX(1 << 6), SZSE(1 << 7);

    private final int value;

    MarketValueEnum(int value) {
        this.value = value;
    }

    public static MarketValueEnum fromString(String marketType) {
        switch (marketType) {
            case "CIB":
                return CIB;
            case "SSE":
                return SSE;
            case "SZE":
                return SZE;
            case "SHFE":
                return SHFE;
            case "DCE":
                return DCE;
            case "CZCE":
                return CZCE;
            case "CFFEX":
                return CFFEX;
            case "SZSE":
                return SZSE;
            default:
                throw new IllegalStateException("Unknown market type: " + marketType);
        }
    }

    public static int toInt(String marketType) {
        return fromString(marketType).getValue();
    }

    public int getValue() {
        return value;
    }
}
