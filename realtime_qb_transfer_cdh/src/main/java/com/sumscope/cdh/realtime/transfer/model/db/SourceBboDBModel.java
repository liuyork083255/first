package com.sumscope.cdh.realtime.transfer.model.db;

/**
 * Created by liu.yang on 2017/9/27.
 */
public class SourceBboDBModel {
    /**
     *  UUID
     *  唯一标识
     */
    private String uuid;
    /**
     * 由 ct 字段转换而来
     */
    private String createTime;//(index)
    /**
     *  由mt 字段转换而来
     */
    private String modifyTime;//(index)
    /**
     *  Mysql 分区字段
     */
    private int bboMonth;//(primary key)
    /**
     *  最新成交价（收益率）
     */
    private String MS_lastDealPrice;
    /**
     *  操作类型 0-新增 1-修改
     */
    private String MS_OPERATE;
    /**
     *  债券代码
     */
    private String MS_GOODS_CODE;
    /**
     *  bondkey+listmarket
     */
    private String MS_BOND_KEY_LISTED_MARKET;
    /**
     *  报价的最新版本
     */
    private long MS_BestVersion;
    /**
     *  最新成交价（净价）
     */
    private String MS_lastDealNetPrice;
    /**
     *
     */
    private String MS_TYPE;
    /**
     *  债券编码
     */
    private String MS_BOND_KEY;
    /**
     *  bid  price
     */
    private String MS_bidPrice;
    /**
     *  ofr  收益率
     */
    private String MS_ofrYield;
    /**
     *  ofr ID
     */
    private String MS_ofrOfferId;
    /**
     *  bid ID
     */
    private String MS_bidOfferId;
    /**
     *  bid 收益率
     */
    private String MS_bidYield;
    /**
     *  ofr 净价
     */
    private String MS_ofrNetPrice;
    /**
     *  ofr price
     */
    private String MS_ofrPrice;
    /**
     *  ofr 量
     */
    private String MS_ofrVolume;
    /**
     *  bid 净价
     */
    private String MS_bidNetPrice;
    /**
     *  债券代码
     */
    private String MS_goodsCode;
    /**
     *  创建时间
     */
    private long MS_createTime;
    /**
     *  bid 量
     */
    private String MS_bidVolume;
    /**
     *  broker Id
     */
    private String MS_COMPANY_ID;
    /**
     *  创建时间
     */
    private long MS_CREATE_TIME;
    /**
     *  行情版本
     */
    private long MS_Version;
    /**
     *  修改时间
     */
    private long MS_MODIFY_TIME;
    /**
     *  流通场所  SSE上交所  CIB银行间  SZE深交所
     */
    private String MS_LISTED_MARKET;


    private String MS_bidFlagRelation;
    private String MS_bidRebate;
    private String MS_ofrPriceDescription;
    private String MS_bidPriceDescription;
    private String MS_ofrFlagBargain;
    private String MS_ofrRebate;
    private String MS_bidExercise;
    private String MS_bidFlagBargain;
    private String MS_ofrFlagRelation;
    private String MS_ofrExercise;
    private String businessCode;


    public String getMS_bidFlagRelation() {
        return MS_bidFlagRelation;
    }

    public void setMS_bidFlagRelation(String MS_bidFlagRelation) {
        this.MS_bidFlagRelation = MS_bidFlagRelation;
    }

    public String getMS_bidRebate() {
        return MS_bidRebate;
    }

    public void setMS_bidRebate(String MS_bidRebate) {
        this.MS_bidRebate = MS_bidRebate;
    }

    public String getMS_ofrPriceDescription() {
        return MS_ofrPriceDescription;
    }

    public void setMS_ofrPriceDescription(String MS_ofrPriceDescription) {
        this.MS_ofrPriceDescription = MS_ofrPriceDescription;
    }

    public String getMS_bidPriceDescription() {
        return MS_bidPriceDescription;
    }

    public void setMS_bidPriceDescription(String MS_bidPriceDescription) {
        this.MS_bidPriceDescription = MS_bidPriceDescription;
    }

    public String getMS_ofrFlagBargain() {
        return MS_ofrFlagBargain;
    }

    public void setMS_ofrFlagBargain(String MS_ofrFlagBargain) {
        this.MS_ofrFlagBargain = MS_ofrFlagBargain;
    }

    public String getMS_ofrRebate() {
        return MS_ofrRebate;
    }

    public void setMS_ofrRebate(String MS_ofrRebate) {
        this.MS_ofrRebate = MS_ofrRebate;
    }

    public String getMS_bidExercise() {
        return MS_bidExercise;
    }

    public void setMS_bidExercise(String MS_bidExercise) {
        this.MS_bidExercise = MS_bidExercise;
    }

    public String getMS_bidFlagBargain() {
        return MS_bidFlagBargain;
    }

    public void setMS_bidFlagBargain(String MS_bidFlagBargain) {
        this.MS_bidFlagBargain = MS_bidFlagBargain;
    }

    public String getMS_ofrFlagRelation() {
        return MS_ofrFlagRelation;
    }

    public void setMS_ofrFlagRelation(String MS_ofrFlagRelation) {
        this.MS_ofrFlagRelation = MS_ofrFlagRelation;
    }

    public String getMS_ofrExercise() {
        return MS_ofrExercise;
    }

    public void setMS_ofrExercise(String MS_ofrExercise) {
        this.MS_ofrExercise = MS_ofrExercise;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getBboMonth() {
        return bboMonth;
    }

    public void setBboMonth(int bboMonth) {
        this.bboMonth = bboMonth;
    }

    public String getMS_lastDealPrice() {
        return MS_lastDealPrice;
    }

    public void setMS_lastDealPrice(String MS_lastDealPrice) {
        this.MS_lastDealPrice = MS_lastDealPrice;
    }

    public String getMS_OPERATE() {
        return MS_OPERATE;
    }

    public void setMS_OPERATE(String MS_OPERATE) {
        this.MS_OPERATE = MS_OPERATE;
    }

    public String getMS_GOODS_CODE() {
        return MS_GOODS_CODE;
    }

    public void setMS_GOODS_CODE(String MS_GOODS_CODE) {
        this.MS_GOODS_CODE = MS_GOODS_CODE;
    }

    public String getMS_BOND_KEY_LISTED_MARKET() {
        return MS_BOND_KEY_LISTED_MARKET;
    }

    public void setMS_BOND_KEY_LISTED_MARKET(String MS_BOND_KEY_LISTED_MARKET) {
        this.MS_BOND_KEY_LISTED_MARKET = MS_BOND_KEY_LISTED_MARKET;
    }

    public long getMS_BestVersion() {
        return MS_BestVersion;
    }

    public void setMS_BestVersion(long MS_BestVersion) {
        this.MS_BestVersion = MS_BestVersion;
    }

    public String getMS_lastDealNetPrice() {
        return MS_lastDealNetPrice;
    }

    public void setMS_lastDealNetPrice(String MS_lastDealNetPrice) {
        this.MS_lastDealNetPrice = MS_lastDealNetPrice;
    }

    public String getMS_TYPE() {
        return MS_TYPE;
    }

    public void setMS_TYPE(String MS_TYPE) {
        this.MS_TYPE = MS_TYPE;
    }

    public String getMS_BOND_KEY() {
        return MS_BOND_KEY;
    }

    public void setMS_BOND_KEY(String MS_BOND_KEY) {
        this.MS_BOND_KEY = MS_BOND_KEY;
    }

    public String getMS_bidPrice() {
        return MS_bidPrice;
    }

    public void setMS_bidPrice(String MS_bidPrice) {
        this.MS_bidPrice = MS_bidPrice;
    }

    public String getMS_ofrYield() {
        return MS_ofrYield;
    }

    public void setMS_ofrYield(String MS_ofrYield) {
        this.MS_ofrYield = MS_ofrYield;
    }

    public String getMS_ofrOfferId() {
        return MS_ofrOfferId;
    }

    public void setMS_ofrOfferId(String MS_ofrOfferId) {
        this.MS_ofrOfferId = MS_ofrOfferId;
    }

    public String getMS_bidOfferId() {
        return MS_bidOfferId;
    }

    public void setMS_bidOfferId(String MS_bidOfferId) {
        this.MS_bidOfferId = MS_bidOfferId;
    }

    public String getMS_bidYield() {
        return MS_bidYield;
    }

    public void setMS_bidYield(String MS_bidYield) {
        this.MS_bidYield = MS_bidYield;
    }

    public String getMS_ofrNetPrice() {
        return MS_ofrNetPrice;
    }

    public void setMS_ofrNetPrice(String MS_ofrNetPrice) {
        this.MS_ofrNetPrice = MS_ofrNetPrice;
    }

    public String getMS_ofrPrice() {
        return MS_ofrPrice;
    }

    public void setMS_ofrPrice(String MS_ofrPrice) {
        this.MS_ofrPrice = MS_ofrPrice;
    }

    public String getMS_ofrVolume() {
        return MS_ofrVolume;
    }

    public void setMS_ofrVolume(String MS_ofrVolume) {
        this.MS_ofrVolume = MS_ofrVolume;
    }

    public String getMS_bidNetPrice() {
        return MS_bidNetPrice;
    }

    public void setMS_bidNetPrice(String MS_bidNetPrice) {
        this.MS_bidNetPrice = MS_bidNetPrice;
    }

    public String getMS_goodsCode() {
        return MS_goodsCode;
    }

    public void setMS_goodsCode(String MS_goodsCode) {
        this.MS_goodsCode = MS_goodsCode;
    }

    public long getMS_createTime() {
        return MS_createTime;
    }

    public void setMS_createTime(long MS_createTime) {
        this.MS_createTime = MS_createTime;
    }

    public String getMS_bidVolume() {
        return MS_bidVolume;
    }

    public void setMS_bidVolume(String MS_bidVolume) {
        this.MS_bidVolume = MS_bidVolume;
    }

    public String getMS_COMPANY_ID() {
        return MS_COMPANY_ID;
    }

    public void setMS_COMPANY_ID(String MS_COMPANY_ID) {
        this.MS_COMPANY_ID = MS_COMPANY_ID;
    }

    public long getMS_CREATE_TIME() {
        return MS_CREATE_TIME;
    }

    public void setMS_CREATE_TIME(long MS_CREATE_TIME) {
        this.MS_CREATE_TIME = MS_CREATE_TIME;
    }

    public long getMS_Version() {
        return MS_Version;
    }

    public void setMS_Version(long MS_Version) {
        this.MS_Version = MS_Version;
    }

    public long getMS_MODIFY_TIME() {
        return MS_MODIFY_TIME;
    }

    public void setMS_MODIFY_TIME(long MS_MODIFY_TIME) {
        this.MS_MODIFY_TIME = MS_MODIFY_TIME;
    }

    public String getMS_LISTED_MARKET() {
        return MS_LISTED_MARKET;
    }

    public void setMS_LISTED_MARKET(String MS_LISTED_MARKET) {
        this.MS_LISTED_MARKET = MS_LISTED_MARKET;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
