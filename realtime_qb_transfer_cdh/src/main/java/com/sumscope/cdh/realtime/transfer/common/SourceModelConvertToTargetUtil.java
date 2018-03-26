package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.sumscope.cdh.realtime.transfer.model.db.*;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import com.sumscope.cdh.realtime.transfer.restful.RestfulUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liu.yang on 2017/10/9.
 */
@Component
public class SourceModelConvertToTargetUtil implements EventTranslatorOneArg<TargetEventModel,SourceEventModel>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceModelConvertToTargetUtil.class);
    private StringBuffer sb = new StringBuffer();

    @Value("${rabbitmq.receiver.trade.from}")
    private String tradeFrom;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RestfulUtil restfulUtil;

    @Override
    public void translateTo(TargetEventModel targetEventModel, long sequence, SourceEventModel sourceEventModel) {
        switch (sourceEventModel.getBondType()){
            case BBO:
                targetEventModel.setBondType(sourceEventModel.getBondType());
                targetEventModel.setTargetBboDBModelList(convertBbo(sourceEventModel.getSourceBboDBModelList()));
                break;
            case TRADE:
                targetEventModel.setBondType(sourceEventModel.getBondType());
                if(StringUtils.equalsIgnoreCase(tradeFrom,"qb")){
                    targetEventModel.setTargetTradeDBModelList(convertTrade(sourceEventModel.getSourceTradeDBModelList()));
                }else{
                    targetEventModel.setTargetTradeDBModelList(sourceEventModel.getTargetTradeDBModelList());
                }
                break;
            case SINGLEBBO:
                targetEventModel.setBondType(sourceEventModel.getBondType());
                targetEventModel.setTargetSingleBboDBModelList(convertSingleBbo(sourceEventModel.getSourceSingleBboDBModelList()));
                break;
            default:
                LOGGER.error(String.format("no match Model type. message =>[%s]",sourceEventModel.getMessage()));
                sourceEventModel.setFlag(false);
        }
    }

    private List<TargetBboDBModel> convertBbo(List<SourceBboDBModel> sourceList){
        List<TargetBboDBModel> targetList = new ArrayList<>();
        sourceList.forEach((sourceModel) -> {
            if(!commonUtil.isBroker(sourceModel.getMS_COMPANY_ID()))
                return;
            try{
                TargetBboDBModel targetModel = new TargetBboDBModel();

                targetModel.setUuid(commonUtil.getUUID());
                targetModel.setBboMonth(commonUtil.getDBPartition());
                String bondKey = sourceModel.getMS_BOND_KEY();

                // 这里添加一个逻辑判断：如果bondKey为空，则从 MS_BOND_KEY_LISTED_MARKET 里面获取
                if(!StringUtils.isEmpty(bondKey)){
                    String _bk_list_market = sourceModel.getMS_BOND_KEY_LISTED_MARKET();
                    if(!StringUtils.isEmpty(_bk_list_market) && _bk_list_market.length() > 3){
                        bondKey = _bk_list_market.substring(0,_bk_list_market.length()-3);
                    }
                }

                String bondKey_listedMarket = bondKey + "_" + sourceModel.getMS_LISTED_MARKET();

                if(sourceModel.getMS_lastDealPrice() != null) {
                    targetModel.setLatestTransactionValue(Double.parseDouble(sourceModel.getMS_lastDealPrice()));
                }else targetModel.setLatestTransactionValue(-999D);

                if(sourceModel.getMS_LISTED_MARKET() != null && sourceModel.getMS_GOODS_CODE() != null){
                    targetModel.setListedMarket(sourceModel.getMS_LISTED_MARKET());
                    targetModel.setBondCode(commonUtil.setListedMarket(sourceModel.getMS_GOODS_CODE(),sourceModel.getMS_LISTED_MARKET()));
                }
                if(sourceModel.getMS_ofrYield() != null) {
                    targetModel.setOfrYtm(new BigDecimal(sourceModel.getMS_ofrYield()));
                }
                if(sourceModel.getMS_bidYield() != null) {
                    targetModel.setBidYtm(new BigDecimal(sourceModel.getMS_bidYield()));
                }
                if(sourceModel.getMS_ofrNetPrice() != null) {
                    targetModel.setOfrCleanPrice(new BigDecimal(sourceModel.getMS_ofrNetPrice()));
                }
                if(sourceModel.getMS_bidNetPrice() != null) {
                    targetModel.setBidCleanPrice(new BigDecimal(sourceModel.getMS_bidNetPrice()));
                }
                if(sourceModel.getMS_COMPANY_ID() != null) {
                    targetModel.setBrokerName(commonUtil.convertBrokerName(sourceModel.getMS_COMPANY_ID()));
                }else targetModel.setBrokerName("--");

                if(sourceModel.getMS_bidFlagRelation() != null) {
                    targetModel.setBidRelationFlag(Integer.parseInt(sourceModel.getMS_bidFlagRelation()));
                }
                if(sourceModel.getMS_bidRebate() != null) {
                    targetModel.setBidRebate(new BigDecimal(sourceModel.getMS_bidRebate()));
                }
                if(sourceModel.getMS_ofrFlagBargain() != null) {
                    targetModel.setOfrBarginFlag(Integer.parseInt(sourceModel.getMS_ofrFlagBargain()));
                }
                if(sourceModel.getMS_ofrRebate() != null) {
                    targetModel.setOfrRebate(new BigDecimal(sourceModel.getMS_ofrRebate()));
                }
                if(sourceModel.getMS_bidExercise() != null) {
                    targetModel.setBidIsExercise(Integer.parseInt(sourceModel.getMS_bidExercise()));
                }
                if(sourceModel.getMS_bidFlagBargain() != null) {
                    targetModel.setBidBarginFlag(Integer.parseInt(sourceModel.getMS_bidFlagBargain()));
                }
                if(sourceModel.getMS_ofrFlagRelation() != null) {
                    targetModel.setOfrRelationFlag(Integer.parseInt(sourceModel.getMS_ofrFlagRelation()));
                }
                if(sourceModel.getMS_ofrExercise() != null) {
                    targetModel.setOfrIsExercise(Integer.parseInt(sourceModel.getMS_ofrExercise()));
                }

                targetModel.setLatestTransaction(sourceModel.getMS_lastDealPrice() == null? "--":sourceModel.getMS_lastDealPrice());
                targetModel.setBondKey(bondKey);
                targetModel.setBid(commonUtil.getBid(sourceModel.getMS_bidPrice(),sourceModel.getMS_bidOfferId()));
                targetModel.setOfrQuoteIds(sourceModel.getMS_ofrOfferId());
                targetModel.setBidQuoteIds(sourceModel.getMS_bidOfferId());
                targetModel.setOfr(commonUtil.getOfr(sourceModel.getMS_ofrPrice(),sourceModel.getMS_ofrOfferId()));
                targetModel.setOfrVolume(commonUtil.formatVolume(sourceModel.getMS_ofrVolume()));
                targetModel.setBidVolume(commonUtil.formatVolume(sourceModel.getMS_bidVolume()));
                targetModel.setBrokerId(sourceModel.getMS_COMPANY_ID());
                targetModel.setOfrPriceComment(sourceModel.getMS_ofrPriceDescription());
                targetModel.setBidPriceComment(sourceModel.getMS_bidPriceDescription());

                /*
                以上是注入qb过来的数据源，下面是注入restful静态数据源
                 */
                targetModel.setShortName(restfulUtil.getShortName(bondKey_listedMarket));
                targetModel.setRemaintTime(restfulUtil.getRemaintTime(bondKey));
                targetModel.setCdcValuation(restfulUtil.getCdcValuation(bondKey_listedMarket));
                targetModel.setCsiValuation(restfulUtil.getCsiValuation(bondKey));
                targetModel.setIssuerBondRating(restfulUtil.getIssuerBondRating(bondKey_listedMarket));
                targetModel.setExpection(restfulUtil.getExpection(bondKey_listedMarket));
                targetModel.setRatingInstitutionName(restfulUtil.getRatingInstitutionName(bondKey_listedMarket));
                targetModel.setBidSubCdc(restfulUtil.getBidSubCdc(bondKey_listedMarket,targetModel.getBid(),targetModel.getCdcValuation()));
                targetModel.setCdcSubOfr(restfulUtil.getCdcSubOfr(bondKey_listedMarket,targetModel.getCdcValuation(),targetModel.getOfr()));
                targetModel.setBidSubCsi(restfulUtil.getBidSubCsi(bondKey_listedMarket,targetModel.getBid(),targetModel.getCsiValuation()));
                targetModel.setCsiSubOfr(restfulUtil.getCsiSubOfr(bondKey_listedMarket,targetModel.getCsiValuation(),targetModel.getOfr()));
                targetModel.setDuration(restfulUtil.getDuration(bondKey_listedMarket));
                targetModel.setRemaintTimeValue(restfulUtil.getRemaintTimeValue(bondKey));

                targetModel.setBidVolumeValue(restfulUtil.getBidVolumeValue(targetModel.getBidVolume()));
                targetModel.setOfrVolumeValue(restfulUtil.getOfrVolumeValue(targetModel.getOfrVolume()));
                targetModel.setBidValue(restfulUtil.getBidValue(targetModel.getBid()));
                targetModel.setOfrValue(restfulUtil.getOfrValue(targetModel.getOfr()));
                targetModel.setCdcValuationValue(restfulUtil.getCdcValuationValue(targetModel.getCdcValuation()));
                targetModel.setCsiValuationValue(restfulUtil.getCsiValuationValue(targetModel.getCsiValuation()));
                targetModel.setBidSubCdcValue(restfulUtil.getBidSubCdcValue(targetModel.getBidSubCdc()));
                targetModel.setBidSubCsiValue(restfulUtil.getBidSubCsiValue(targetModel.getBidSubCsi()));
                targetModel.setCdcSubOfrValue(restfulUtil.getCdcSubOfrValue(targetModel.getCdcSubOfr()));
                targetModel.setCsiSubOfrValue(restfulUtil.getCsiSubOfrValue(targetModel.getCsiSubOfr()));

                targetModel.setPinyinFull(restfulUtil.getPinyinFull(bondKey_listedMarket));
                targetModel.setRatingInstitutionPinyin(restfulUtil.getRatingInstitutionPinyin(bondKey_listedMarket));
                targetModel.setExpectionValue(restfulUtil.getExpectionValue(bondKey_listedMarket));
                targetModel.setBidVolumeSortValue(restfulUtil.getBidVolumeSortValue(targetModel.getBidVolume(),targetModel.getBidVolumeValue()));
                targetModel.setOfrVolumeSortValue(restfulUtil.getOfrVolumeSortValue(targetModel.getOfrVolume(),targetModel.getOfrVolumeValue()));
                targetModel.setExpectionSortValue(restfulUtil.getExpectionSortValue(bondKey_listedMarket));
                targetModel.setRestSymbolDays(restfulUtil.getRestSymbolDays(bondKey,bondKey_listedMarket));
                targetModel.setRestSymbolDaysExchange(restfulUtil.getRestSymbolDaysExchange(bondKey,bondKey_listedMarket));

                /*
                这个地方是利用qb上游数据给的businessCode字段，如果为空，才会默认加载resuful的数据字段
                 */
                if(sourceModel.getBusinessCode() == null || sourceModel.getBusinessCode().equals("ALL")){
                    targetModel.setBusinessCode(restfulUtil.getBusinessCode(bondKey_listedMarket));
                }else{
                    targetModel.setBusinessCode(sourceModel.getBusinessCode());
                }

                /*
                数据库字段设置
                 */
                targetModel.setCreateDateTime(commonUtil.convertLongToStringDatetime(sourceModel.getMS_CREATE_TIME()*1000L));
                targetModel.setUpdateTime(commonUtil.getTime(sourceModel.getMS_MODIFY_TIME()*1000L));
                targetModel.setUpdateDateTime(commonUtil.convertLongToStringDatetime(sourceModel.getMS_MODIFY_TIME()*1000L));

                if(StringUtils.isEmpty(sourceModel.getMS_BOND_KEY())){
                    LOGGER.error("get bbo2 bondKey is null.    source={}.  target={}",JSON.toJSONString(sourceModel),JSON.toJSONString(targetModel));
                }

                targetList.add(targetModel);
            }catch (Exception e){
                LOGGER.error(String.format("convert sourceBboModel to targetBboModel fail.exception=>[%s]   message=>[%s]",e.getMessage(), JSON.toJSONString(sourceModel)));
            }
        });
        return targetList;
    }

    private List<TargetSingleBboDBModel> convertSingleBbo(List<SourceSingleBboDBModel> sourceList){
        List<TargetSingleBboDBModel> targetList = new ArrayList<>();
        sourceList.forEach((sourceModel) -> {
            // 过滤交易所行情 cid = e
            if(!commonUtil.isBroker(sourceModel.getCid()))
                return;

            TargetSingleBboDBModel targetModel = new TargetSingleBboDBModel();
            try{
                targetModel.setTransType("update");   // 本来是insert update delete，qb过来的过滤了delete，而insert和update无法区分，所以默认update

                targetModel.setBidOrAsk(commonUtil.getBidOrAsk(sourceModel.getSym()));
                targetModel.setLocMsgCrtAt(commonUtil.getLocMsgCrtAt());
                targetModel.setUniDataID(commonUtil.getUniDataID(sourceModel.getCid(),sourceModel.getLm(),sourceModel.getBk(),sourceModel.getId(),sourceModel.getSym()));
                targetModel.setId(sourceModel.getId());
                targetModel.setCreateTime(sourceModel.getCt()*1000L);
                targetModel.setModifyTime(sourceModel.getMt()*1000L);
                targetModel.setBondKey(sourceModel.getBk());
                targetModel.setListedMarket(sourceModel.getLm());
                targetModel.setCode(sourceModel.getGc());
                targetModel.setShortName(sourceModel.getGsn());
                if(!StringUtils.isEmpty(sourceModel.getSym()))
                    targetModel.setSide(commonUtil.getSide(sourceModel.getSym()));

                targetModel.setQuoteInstitution(null);   // 这是保密字段，不下发
                targetModel.setTrader(null);   // 这是保密字段，不下发

                targetModel.setBrokerId(sourceModel.getCid());
                targetModel.setQuoteType(sourceModel.getQt());

                targetModel.setInternally(1);   // qb过来的只有外部报价，内部报价对应的transType是delete，固定为1

                if(!StringUtils.isEmpty(sourceModel.getSts()))
                    targetModel.setStatus(commonUtil.getStatus(sourceModel.getSts()));
                if(!StringUtils.isEmpty(sourceModel.getDs()))
                    targetModel.setDealStatus(commonUtil.getDealStatus(sourceModel.getDs()));

                targetModel.setIsExercise(null);   // 这个字段下游quickfix虽然有获取，但是processor上游基本是不会发送的，并且qb没有这个字段，所以不发送

                if(!StringUtils.isEmpty(sourceModel.getFbar()))
                    targetModel.setBargainFlag(commonUtil.getBargainFlag(sourceModel.getFbar()));
                if(!StringUtils.isEmpty(sourceModel.getFr()))
                    targetModel.setRelationFlag(commonUtil.getRelationFlag(sourceModel.getFr()));
                if(!StringUtils.isEmpty(sourceModel.getReb()))
                    targetModel.setRebate(commonUtil.getRebate(sourceModel.getReb()));
                if(!StringUtils.isEmpty(sourceModel.getVol()))
                    targetModel.setVolume(restfulUtil.getBidVolumeValue(sourceModel.getVol()));
                if(!StringUtils.isEmpty(sourceModel.getYd()))
                    targetModel.setYtm(commonUtil.getYtm(sourceModel.getYd()));
                if(!StringUtils.isEmpty(sourceModel.getPri()))
                    targetModel.setPrice(commonUtil.getPrice(sourceModel.getPri()));
                if(!StringUtils.isEmpty(sourceModel.getPdesc()))
                    targetModel.setPriceDescription(sourceModel.getPdesc());
                if(!StringUtils.isEmpty(sourceModel.getNp()))
                    targetModel.setCleanPrice(commonUtil.getCleanPrice(sourceModel.getNp()));

                targetModel.setDirtyPrice(null);   // 无法确定 不发送

                targetModel.setPriceStr(sourceModel.getPri());
                targetModel.setVolumeStr(sourceModel.getVol());

                targetModel.setVolumeDesc(null);   // 无法确定 不发送

                /*
                数据库字段
                 */
                targetModel.setQuoteMonth(commonUtil.getDBPartition());
                targetModel.setUpdateDateTime(commonUtil.getUpdateDateTime(sourceModel.getMt()*1000L,sourceModel.getCt()*1000L));
                targetModel.setAutoId(null);

                targetList.add(targetModel);
            }catch (Exception e){
                LOGGER.error(String.format("convert sourceSingleBboModel to targetSingleBboModel error.exception=>[%s]   message=>[%s]",e.getMessage(), JSON.toJSONString(sourceModel)));
            }
        });
        return targetList;
    }

    public List<TargetTradeDBModel> convertTrade(List<SourceTradeDBModel> sourceList){

        List<TargetTradeDBModel> targetList = new ArrayList<>();

        sourceList.forEach((sourceModel) -> {

            if(!commonUtil.isBroker(sourceModel.getMS_COMPANY_ID()))
                return;

            sb.delete(0,sb.length());

            try {
                TargetTradeDBModel targetModel = new TargetTradeDBModel();

                if(sourceModel.getMS_OPERATE() != null) {
                    targetModel.setDealType(commonUtil.convertOperate(sourceModel.getMS_OPERATE()));
                }
                if(sourceModel.getMS_LISTED_MARKET() != null){
                    targetModel.setListedMarket(sourceModel.getMS_LISTED_MARKET());
                }
                if(sourceModel.getMS_LISTED_MARKET() != null && sourceModel.getMS_GOODS_CODE() != null){
                    targetModel.setBondCode(commonUtil.setListedMarket(sourceModel.getMS_GOODS_CODE(),sourceModel.getMS_LISTED_MARKET()));
                }
                if(sourceModel.getMS_COMPANY_ID() != null) {
                    targetModel.setBrokerName(commonUtil.convertBrokerName(sourceModel.getMS_COMPANY_ID()));
                }
                if(sourceModel.getMS_dealStatus() != null) {
                    targetModel.setDealStatus(Integer.parseInt(sourceModel.getMS_dealStatus()));
                }
                if(sourceModel.getMS_netPrice() != null) {
                    targetModel.setCleanPrice(new BigDecimal("0".equals(sourceModel.getMS_netPrice())?"0.0":sourceModel.getMS_netPrice()));
                }
                if(sourceModel.getMS_yield() != null) {
                    targetModel.setYtm(new BigDecimal(sourceModel.getMS_yield()));
                }

                targetModel.setBrokerId(sourceModel.getMS_COMPANY_ID());
                targetModel.setBondKey(sourceModel.getMS_BOND_KEY());
                targetModel.setTradeId(sourceModel.getMS_id());
                targetModel.setDealPrice(sourceModel.getMS_price());
                targetModel.setShortName(sourceModel.getMS_goodsShortName());
                targetModel.setCreateDateTime(commonUtil.convertLongToStringDatetime(sourceModel.getMS_CREATE_TIME()*1000L));
                targetModel.setUpdateTime(commonUtil.getTime(sourceModel.getMS_MODIFY_TIME()*1000L));
                targetModel.setUpdateDateTime(commonUtil.convertLongToStringDatetime(sourceModel.getMS_MODIFY_TIME()*1000L));

                String bondKey = sb.append(sourceModel.getMS_BOND_KEY()).toString();
                String bondKey_listedMarket = sb.append("_").append(targetModel.getListedMarket()).toString();

                targetModel.setRemaintTime(restfulUtil.getRemaintTime(bondKey));
                targetModel.setCdcValuation(restfulUtil.getCdcValuation(bondKey_listedMarket));
                targetModel.setCsiValuation(restfulUtil.getCsiValuation(bondKey));
                targetModel.setIssuerBondRating(restfulUtil.getIssuerBondRating(bondKey_listedMarket));
                targetModel.setExpection(restfulUtil.getExpection(bondKey_listedMarket));
                targetModel.setRatingInstitutionName(restfulUtil.getRatingInstitutionName(bondKey_listedMarket));
                targetModel.setRemaintTimeValue(restfulUtil.getRemaintTimeValue(bondKey));
                targetModel.setDealPriceValue(restfulUtil.parseDouble(targetModel.getDealPrice()));
                targetModel.setCdcValuationValue(restfulUtil.parseDouble(targetModel.getCdcValuation()));
                targetModel.setCsiValuationValue(restfulUtil.parseDouble(targetModel.getCsiValuation()));
                targetModel.setStatus(1);// status

                // transType
                if(targetModel.getDealStatus() > 3){
                    targetModel.setTransType("delete");
                }else{
                    if(commonUtil.containTradeId(targetModel.getTradeId())){
                        targetModel.setTransType("update");
                    }else{
                        targetModel.setTransType("insert");
                    }
                }

                targetModel.setPinyinFull(restfulUtil.getPinyinFull(bondKey_listedMarket));
                targetModel.setRatingInstitutionPinyin(restfulUtil.getRatingInstitutionPinyin(bondKey_listedMarket));
                targetModel.setExpectionValue(restfulUtil.getExpectionValue(bondKey_listedMarket));
                targetModel.setExpectionSortValue(restfulUtil.getExpectionSortValue(bondKey_listedMarket));
                targetModel.setRestSymbolDays(restfulUtil.getRestSymbolDays(bondKey,bondKey_listedMarket));
                targetModel.setRestSymbolDaysExchange(restfulUtil.getRestSymbolDaysExchange(bondKey,bondKey_listedMarket));

                targetModel.setSettlementDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));// 这个字段qb没有给，所以默认当天
                 /*
                    这个地方是利用qb上游数据给的businessCode字段，如果为空，才会默认加载resuful的数据字段
                 */
                if(sourceModel.getBusinessCode() == null || sourceModel.getBusinessCode().equals("ALL")){
                    targetModel.setBusinessCode(restfulUtil.getBusinessCode(bondKey_listedMarket));
                }else{
                    targetModel.setBusinessCode(sourceModel.getBusinessCode());
                }

                targetList.add(targetModel);
            } catch (Exception e) {
                LOGGER.error(String.format("convert sourceTradeModel to targetTradeModel fail.exception=>[%s]   message=>[%s]",e.getMessage(), JSON.toJSONString(sourceModel)));
            }
        });
        return targetList;
    }
}
