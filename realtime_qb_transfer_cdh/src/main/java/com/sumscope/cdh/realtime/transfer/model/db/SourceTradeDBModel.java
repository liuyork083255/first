package com.sumscope.cdh.realtime.transfer.model.db;

/**
 * Created by liu.yang on 2017/9/27.
 */
public class SourceTradeDBModel {
    /**
     * 由 ct 字段转换而来
     */
    private String createTime;//(index)
    /**
     *  由mt 字段转换而来
     */
    private String modifyTime;//(index)
    /**
     *  操作类型 0-新增 1-修改
     */
    private String MS_OPERATE;
    /**
     *  债券代码
     */
    private String MS_GOODS_CODE;
    /**
     *
     */
    private String MS_COMPANY_NAME;
    /**
     *  bondkey+listmarket
     */
    private String MS_BOND_KEY_LISTED_MARKET;
    /**
     *
     */
    private String MS_TYPE;
    /**
     *  债券编码
     */
    private String MS_BOND_KEY;
    /**
     *
     */
    private String MS_id;
    /**
     *
     */
    private String MS_price;
    /**
     *
     */
    private String MS_fullPrice;
    /**
     *
     */
    private String MS_goodsShortName;
    /**
     *
     */
    private String MS_dealStatus;
    /**
     *  净价
     */
    private String MS_netPrice;
    /**
     *
     */
    private String MS_rebate;
    /**
     *  债券代码
     */
    private String MS_goodsCode;
    /**
     *  创建时间
     */
    private long MS_createTime;
    /**
     *  收益率
     */
    private String MS_yield;
    /**
     *  broker ID
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

    private String MS_GOODS_ID;
    private String MS_goodsID;
    private String MS_volume;
    private String MS_exercise;
    private String MS_return_point;
    private String MS_pre_close_price;
    private String businessCode;
    private String internally;
    private String status;

    public String getMS_GOODS_ID() {
        return MS_GOODS_ID;
    }

    public void setMS_GOODS_ID(String MS_GOODS_ID) {
        this.MS_GOODS_ID = MS_GOODS_ID;
    }

    public String getMS_goodsID() {
        return MS_goodsID;
    }

    public void setMS_goodsID(String MS_goodsID) {
        this.MS_goodsID = MS_goodsID;
    }

    public String getMS_volume() {
        return MS_volume;
    }

    public void setMS_volume(String MS_volume) {
        this.MS_volume = MS_volume;
    }

    public String getMS_exercise() {
        return MS_exercise;
    }

    public void setMS_exercise(String MS_exercise) {
        this.MS_exercise = MS_exercise;
    }

    public String getMS_return_point() {
        return MS_return_point;
    }

    public void setMS_return_point(String MS_return_point) {
        this.MS_return_point = MS_return_point;
    }

    public String getMS_pre_close_price() {
        return MS_pre_close_price;
    }

    public void setMS_pre_close_price(String MS_pre_close_price) {
        this.MS_pre_close_price = MS_pre_close_price;
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

    public String getMS_COMPANY_NAME() {
        return MS_COMPANY_NAME;
    }

    public void setMS_COMPANY_NAME(String MS_COMPANY_NAME) {
        this.MS_COMPANY_NAME = MS_COMPANY_NAME;
    }

    public String getMS_BOND_KEY_LISTED_MARKET() {
        return MS_BOND_KEY_LISTED_MARKET;
    }

    public void setMS_BOND_KEY_LISTED_MARKET(String MS_BOND_KEY_LISTED_MARKET) {
        this.MS_BOND_KEY_LISTED_MARKET = MS_BOND_KEY_LISTED_MARKET;
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

    public String getMS_id() {
        return MS_id;
    }

    public void setMS_id(String MS_id) {
        this.MS_id = MS_id;
    }

    public String getMS_price() {
        return MS_price;
    }

    public void setMS_price(String MS_price) {
        this.MS_price = MS_price;
    }

    public String getMS_fullPrice() {
        return MS_fullPrice;
    }

    public void setMS_fullPrice(String MS_fullPrice) {
        this.MS_fullPrice = MS_fullPrice;
    }

    public String getMS_goodsShortName() {
        return MS_goodsShortName;
    }

    public void setMS_goodsShortName(String MS_goodsShortName) {
        this.MS_goodsShortName = MS_goodsShortName;
    }

    public String getMS_dealStatus() {
        return MS_dealStatus;
    }

    public void setMS_dealStatus(String MS_dealStatus) {
        this.MS_dealStatus = MS_dealStatus;
    }

    public String getMS_netPrice() {
        return MS_netPrice;
    }

    public void setMS_netPrice(String MS_netPrice) {
        this.MS_netPrice = MS_netPrice;
    }

    public String getMS_rebate() {
        return MS_rebate;
    }

    public void setMS_rebate(String MS_rebate) {
        this.MS_rebate = MS_rebate;
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

    public String getMS_yield() {
        return MS_yield;
    }

    public void setMS_yield(String MS_yield) {
        this.MS_yield = MS_yield;
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

    public String getInternally() {
        return internally;
    }

    public void setInternally(String internally) {
        this.internally = internally;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
