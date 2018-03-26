package com.sumscope.cdh.realtime.transfer.model.db;

/**
 * Created by liu.yang on 2017/9/29.
 */
public class SourceSingleBboDBModel {
    /**
     *  UUID
     *  唯一标识
     */
    private String uuid;//(primary key)
    /**
     * 由 ct 字段转换而来
     */
    private String createTime;
    /**
     *  由mt 字段转换而来
     */
    private String modifyTime;//(index)
    /**
     *  Mysql 分区字段
     */
    private int singleBboMonth;//(primary key)

    /**
     *  id
     */
    private String id;
    /**
     *  bond_key
     */
    private String bk;
    /**
     *  goods_code
     */
    private String gc;
    /**
     *  symbol
     *  bid/ask标识',1--bid,-1,ofr
     */
    private String sym;
    /**
     *  price
     *  收益率
     */
    private String pri;
    /**
     *  Bvolume
     *  数量，总价
     */
    private String vol;
    /**
     *  deal_status
     *  订单状态 \n-1：待审核 \n0：发布\n1：成交\n2：作废
     */
    private String ds;
    /**
     * flag_bargain
     * 可议价*
     */
    private String fbar;
    /**
     *  company_id
     *  经纪公司ID
     */
    private String cid;
    /**
     *  company_name
     *  经纪公司名称
     */
    private String cn;
    /**
     *  status
     */
    private String sts;
    /**
     *  modify_time
     */
    private long mt;
    /**
     *  create_time
     */
    private long ct;
    /**
     *  rebate
     *  是否返点(0-不返，1返点)
     */
    private String reb;
    /**
     *  goods_short_name
     */
    private String gsn;
    /**
     *  price_status
     *  价格状态，0:报价被refer或deal掉了（UI灰色显示） 1:报价发布或者报价删除或者内部报价，按原逻辑显示
     */
    private String ps;
    /**
     *  yield
     *  收益率
     */
    private String yd;
    /**
     *  listed_market
     *  市场代码
     */
    private String lm;
    /**
     *  bond_key_listed_market
     */
    private String bklm;
    /**
     *  Version
     *  版本索引，每个公司唯一自增，从0开始
     */
    private long ver;
    /**
     *  报价价格类型
     *  1-净价2-全价3-收益率4-利差
     */
    private String qt;

    /**
     * price_description
     */
    private String pdesc;

    /**
     * net_price
     */
    private String np;

    /**
     *  最新成交净价
     */
    private String ldnp;

    /**
     *  最新成交价格
     */
    private String ldp;

    /**
     * flag_relation
     */
    private String fr;

    /**
     * flag_bad
     */
    private String fbad;

    /**
     * flag_vip
     */
    private String fv;

    /**
     *  这个字段是来源为qb数据源中的 BusinessCode 字段
     */
    private String businessCode;

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

    public int getSingleBboMonth() {
        return singleBboMonth;
    }

    public void setSingleBboMonth(int singleBboMonth) {
        this.singleBboMonth = singleBboMonth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBk() {
        return bk;
    }

    public void setBk(String bk) {
        this.bk = bk;
    }

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getFbar() {
        return fbar;
    }

    public void setFbar(String fbar) {
        this.fbar = fbar;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public long getMt() {
        return mt;
    }

    public void setMt(long mt) {
        this.mt = mt;
    }

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public String getReb() {
        return reb;
    }

    public void setReb(String reb) {
        this.reb = reb;
    }

    public String getGsn() {
        return gsn;
    }

    public void setGsn(String gsn) {
        this.gsn = gsn;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getYd() {
        return yd;
    }

    public void setYd(String yd) {
        this.yd = yd;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getBklm() {
        return bklm;
    }

    public void setBklm(String bklm) {
        this.bklm = bklm;
    }

    public long getVer() {
        return ver;
    }

    public void setVer(long ver) {
        this.ver = ver;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getNp() {
        return np;
    }

    public void setNp(String np) {
        this.np = np;
    }

    public String getLdnp() {
        return ldnp;
    }

    public void setLdnp(String ldnp) {
        this.ldnp = ldnp;
    }

    public String getLdp() {
        return ldp;
    }

    public void setLdp(String ldp) {
        this.ldp = ldp;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getFbad() {
        return fbad;
    }

    public void setFbad(String fbad) {
        this.fbad = fbad;
    }

    public String getFv() {
        return fv;
    }

    public void setFv(String fv) {
        this.fv = fv;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
