package com.sumscope.cdh.realtime.transfer.model.db;

/**
 * Created by liu.yang on 2017/12/6.
 */
public class TargetSingleBboDBModel {

    private Long autoId;
    private int quoteMonth;
    private String updateDateTime;

    private String transType;              //
    private String bidOrAsk;               // 表示方向 和字段 side 保持一致，对应qb中的 sym 字段
    private Long locMsgCrtAt;              // System.currentTimeMillis()
    private String uniDataID;              // brokerId + "," + market + "," + bondKey + "," + side> 0 ? "B" : "O" + "," + quoteId;
    private String messageType = "quote";  // 固定
    private String id;                     // ----> inbound message field: id   报价id
    private Long createTime;               // 对应qb中的 ct 字段
    private Long modifyTime;               // 对应qb中的 mt 字段
    private String bondKey;                // 对应qb中的 bk 字段
    private String listedMarket;           // 对应qb中的 lm 字段
    private String code;                   // 对应qb中的 gc 字段
    private String shortName;              // 对应qb中的 gsn 字段
    private Byte side;                     // 对应qb中的 sym 字段         -1 / 1
    private String quoteInstitution;       // 解释为 financialCompanyId                       ?
    private String trader;                 // 解释为 traderId             这个两个字段是来自broker，属于保密部分
    private String brokerId;               // 对应qb中的 cid 字段          这个两个字段是来自broker，属于保密部分
    private String quoteType;              // 对应qb中的 qt 字段
    private Integer internally;            // 解释为 内外部报价: 1-external 2-internal         ?
    private Integer status;                // 对应qb中的 sts 字段
    private Integer dealStatus;            // 对应qb中的 ds 字段
    private Byte isExercise;               // 行权/到期                                       ？
    private Byte bargainFlag;              // 对应qb中的 fbar 字段
    private Byte relationFlag;             // 对应qb中的 fr 字段
    private Double rebate;                 // 对应qb中的 reb 字段
    private Double volume;                 // 对应qb中的 vol 字段           这个字段转变蛮大的
    private Double ytm;                    // 对应qb中的 yd 字段
    private Double price;                  // 对应qb中的 pri 字段
    private String priceDescription;       // 对应qb中的 pdesc 字段
    private Double cleanPrice;             // 对应qb中的 np 字段
    private Double dirtyPrice;             // 这是fullPrice                                      ?
    private String priceStr;               // price Str 类型       保留两位小数
    private String volumeStr;              // volume Str 类型
    private String volumeDesc;             // ？

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public int getQuoteMonth() {
        return quoteMonth;
    }

    public void setQuoteMonth(int quoteMonth) {
        this.quoteMonth = quoteMonth;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getBidOrAsk() {
        return bidOrAsk;
    }

    public void setBidOrAsk(String bidOrAsk) {
        this.bidOrAsk = bidOrAsk;
    }

    public Long getLocMsgCrtAt() {
        return locMsgCrtAt;
    }

    public void setLocMsgCrtAt(Long locMsgCrtAt) {
        this.locMsgCrtAt = locMsgCrtAt;
    }

    public String getUniDataID() {
        return uniDataID;
    }

    public void setUniDataID(String uniDataID) {
        this.uniDataID = uniDataID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBondKey() {
        return bondKey;
    }

    public void setBondKey(String bondKey) {
        this.bondKey = bondKey;
    }

    public String getListedMarket() {
        return listedMarket;
    }

    public void setListedMarket(String listedMarket) {
        this.listedMarket = listedMarket;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Byte getSide() {
        return side;
    }

    public void setSide(Byte side) {
        this.side = side;
    }

    public String getQuoteInstitution() {
        return quoteInstitution;
    }

    public void setQuoteInstitution(String quoteInstitution) {
        this.quoteInstitution = quoteInstitution;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public Integer getInternally() {
        return internally;
    }

    public void setInternally(Integer internally) {
        this.internally = internally;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Byte getIsExercise() {
        return isExercise;
    }

    public void setIsExercise(Byte isExercise) {
        this.isExercise = isExercise;
    }

    public Byte getBargainFlag() {
        return bargainFlag;
    }

    public void setBargainFlag(Byte bargainFlag) {
        this.bargainFlag = bargainFlag;
    }

    public Byte getRelationFlag() {
        return relationFlag;
    }

    public void setRelationFlag(Byte relationFlag) {
        this.relationFlag = relationFlag;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getYtm() {
        return ytm;
    }

    public void setYtm(Double ytm) {
        this.ytm = ytm;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    public Double getCleanPrice() {
        return cleanPrice;
    }

    public void setCleanPrice(Double cleanPrice) {
        this.cleanPrice = cleanPrice;
    }

    public Double getDirtyPrice() {
        return dirtyPrice;
    }

    public void setDirtyPrice(Double dirtyPrice) {
        this.dirtyPrice = dirtyPrice;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getVolumeStr() {
        return volumeStr;
    }

    public void setVolumeStr(String volumeStr) {
        this.volumeStr = volumeStr;
    }

    public String getVolumeDesc() {
        return volumeDesc;
    }

    public void setVolumeDesc(String volumeDesc) {
        this.volumeDesc = volumeDesc;
    }
}
