package com.sumscope.qpwb.ncd.service.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.qpwb.ncd.data.access.cdh.CdhHolidayAccess;
import com.sumscope.qpwb.ncd.data.access.iam.IamPermissionAccess;
import com.sumscope.qpwb.ncd.data.access.qpwb.QpwbNcdIssuersAccess;
import com.sumscope.qpwb.ncd.data.access.webserver.WebBondServerAccess;
import com.sumscope.qpwb.ncd.data.model.db.*;
import com.sumscope.qpwb.ncd.data.model.iam.IssuerInfo;
import com.sumscope.qpwb.ncd.data.model.qpwb.NcdIssuer;
import com.sumscope.qpwb.ncd.data.model.repository.*;
import com.sumscope.qpwb.ncd.data.model.webserver.WebBondResponse;
import com.sumscope.qpwb.ncd.data.service.NcdIssuersService;
import com.sumscope.qpwb.ncd.data.service.StateOwnBankService;
import com.sumscope.qpwb.ncd.domain.*;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.LimitEnum;
import com.sumscope.qpwb.ncd.global.enums.RateEnum;
import com.sumscope.qpwb.ncd.global.exception.NcdExceptionModel;
import com.sumscope.qpwb.ncd.global.exception.NcdWarnException;
import com.sumscope.qpwb.ncd.management.timedtask.DataManagementService;
import com.sumscope.qpwb.ncd.rabbitmq.SendReservedHandler;
import com.sumscope.qpwb.ncd.service.converter.ConditionToDBUtil;
import com.sumscope.qpwb.ncd.service.converter.DBToDtoUtil;
import com.sumscope.qpwb.ncd.service.converter.NcdConverter;
import com.sumscope.qpwb.ncd.utils.*;
import com.sumscope.qpwb.ncd.utils.restful.ResponseUtil;
import com.sumscope.qpwb.ncd.web.condition.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liu.yang on 2018/1/10.
 */
@Service("ncdServer")
public class NcdServiceImpl implements NcdServiceI {

    private static final Logger logger = LoggerFactory.getLogger(NcdServiceImpl.class);
    private SimpleDateFormat sdfhms = new SimpleDateFormat("HH:mm:ss");
    @Value("${application.broker.report.web}")
    private String brokerReportUrl;
    @Value("${application.web.broker.check}")
    private Boolean isCheckWebAuth;
    @Value("${application.pa.broker.id}")
    private String PABrokerId;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ConditionToDBUtil conditionToDBUtil;
    @Autowired
    private DBToDtoUtil dbToDtoUtil;
    @Autowired
    private CdhHolidayAccess cdhHolidayAccess;
    @Autowired
    private QpwbNcdIssuersAccess qpwbAccess;
    @Autowired
    private IamPermissionAccess iamAccess;
    @Autowired
    private UserFiltersRepo userFiltersRepo;
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;
    @Autowired
    private FavouritesRepo favouritesRepo;
    @Autowired
    private QuotesRepo quotesRepo;
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private UserContactRepo userContactRepo;
    @Autowired
    private NcdConverter ncdConverter;
    @Autowired
    private StateOwnBankRepo stateOwnBankRepo;
    @Autowired
    private StateOwnBankService stateOwnBankService;
    @Autowired
    private QuoteDetailsRepo quoteDetailsRepo;
    @Autowired
    private IamPermissionAccess iamPermissionAccess;
    @Autowired
    private QpwbNcdIssuersAccess qpwbNcdIssuersAccess;
    @Autowired
    private NcdIssuersService ncdIssuersService;
    @Autowired
    private WebBondServerAccess webBondServerAccess;
    @Autowired
    private SendReservedHandler sendReservedHandler;

    @Override
    public AttentionDTO setAttention(AttentionCondition ac) {
        try {
            if (StringUtils.equalsIgnoreCase("add", ac.getStatus())) {
                Favourites byUserIdAndIssuerId = favouritesRepo.findByUserIdAndIssuerId(ac.getUserId(), ac.getInstitution());
                if (byUserIdAndIssuerId == null)
                    favouritesRepo.saveAndFlush(conditionToDBUtil.convertAttentionConditionToFavourites(ac));
                else {
                    logger.warn("the {}'s {} exist in DB.", ac.getUserId(), ac.getInstitution());
                    throw NcdWarnException.warn("该订单已存在");
                }
            } else
                favouritesRepo.deleteByUserIdAndIssuerId(ac.getUserId(), ac.getInstitution());

            return getAttention(ac.getUserId());
        } catch (NcdWarnException e) {
            throw e;
        } catch (Exception e) {
            logger.error("DB save user attention error.  exception:{}", e.getMessage());
            throw NcdExceptionModel.operateDBFailed;
        }
    }

    @Override
    public AttentionDTO getAttention(String userId) {
        AttentionDTO attentionDTO = new AttentionDTO();
        try {
            List<Favourites> favourites = favouritesRepo.findByUserId(userId);
            logger.info("find db Favourites by userid={}.   result:{}", userId, JSON.toJSONString(favourites));
            favourites.forEach(f -> attentionDTO.getAttentionList().add(f.getIssuerId()));
            return attentionDTO;
        } catch (Exception e) {
            logger.error("find db Favourites by userid={} error.   exception:{}", userId, e.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
    }

    @Override
    public MatrixDTO getQuoteMatrix(QuoteMatrixCondition condition) {
        try {
            String brokerId = condition.getBrokerId();
            String userId = condition.getUserId();
            Collection<String> brokers = iamAccess.getBrokersByUserAuth(userId);
            if (!brokers.contains(brokerId)) {
                logger.warn("the user {} does not have the access the to get brokerId {} matrix info", userId, brokerId);
                throw NcdExceptionModel.NotAuthorizedUser;
            }
            String category = condition.getCategory();
            String[] categories = {NcdConstants.MATRIX_CON_CATEGORY_ALL, NcdConstants.MATRIX_CON_CATEGORY_FAVOURITE,
                    NcdConstants.MATRIX_CON_CATEGORY_FILTER};
            if (!Arrays.asList(categories).contains(category)) {
                throw new IllegalArgumentException(String.format("The parameter category %s not in all/favourite/filter list.", category));
            }
            List<String> limits = condition.getLimit();
            List<String> allLimits = LimitEnum.getLimitDescs();
            limits.forEach(item -> {
                if (!allLimits.contains(item)) {
                    throw new IllegalArgumentException(String.format("The parameter limits %s not in support list.", item));
                }
            });
            BigDecimal profit = condition.getProfit();
            Assert.notEmpty(limits, "Parameter sort can not be null or empty.");
            Assert.hasText(BigDecimalUtils.toString(profit), "the param profit field can not be null or empty");
//            String issuerDate = cdhHolidayAccess.getIssueDate();
//            Date date = DateUtils.convertToSqlDate(issuerDate, NcdConstants.DATE_YYYY_MM_DD);
            List<Quotes> quotes = quotesRepo.findByBrokerIdAndIssuerDateOrderByFirstLevelOrderAscSecondLevelOrderAsc(
                    brokerId);
            switch (category.toLowerCase()) {
                case NcdConstants.MATRIX_CON_CATEGORY_FILTER:
                    quotes = filterAllQuotes(quotes, userId);
                    break;
                case NcdConstants.MATRIX_CON_CATEGORY_FAVOURITE:
                    List<String> favIssuerCodes = getFavouriteCodes(userId);
                    quotes = quotes.stream().filter(item -> favIssuerCodes.contains(item.getIssuerId())).collect(Collectors.toList());
                    break;
                case NcdConstants.MATRIX_CON_CATEGORY_ALL:
                    break;
                default:
                    logger.error("the category {} not in support list", category);
                    return new MatrixDTO();
            }
            MatrixDTO matrixDTO = ncdConverter.buildMatrixInfos(quotes, condition);
            return matrixDTO;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Failed to get matrix data, err:{}", ex.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
    }

    public List<String> getFavouriteCodes(String userId) {
        List<Favourites> favourites = favouritesRepo.findByUserId(userId);
        return favourites.stream().map(item -> item.getIssuerId()).collect(Collectors.toList());
    }

    public List<Quotes> filterAllQuotes(List<Quotes> quotes, String userId) {
        try {
            List<Quotes> filterQuotes = new ArrayList<>();
            UserFilters filter = userFiltersRepo.findByUserId(userId);
            if (filter != null) {
                List<String> rates = BusinessUtils.convertToStringList(filter.getRating());
                List<String> bankTypes = BusinessUtils.convertToStringList(filter.getInstitutionType());
                List<BigDecimal> totalAssets = BusinessUtils.convertToDecimalList(filter.getTotalAsset());
                List<BigDecimal> netAssets = BusinessUtils.convertToDecimalList(filter.getNetAsset());
                List<BigDecimal> revenues = BusinessUtils.convertToDecimalList(filter.getRevenue());
                List<BigDecimal> netProfits = BusinessUtils.convertToDecimalList(filter.getNetProfit());
                List<BigDecimal> ldps = BusinessUtils.convertToDecimalList(filter.getLdp());
                List<BigDecimal> ccars = BusinessUtils.convertToDecimalList(filter.getCcar());
                List<BigDecimal> badRatios = BusinessUtils.convertToDecimalList(filter.getBadRatio());
                for (Quotes item : quotes) {
                    NcdIssuer ncdIssuer = NcdIssuersService.cachedAllNcdIssuers.get(item.getIssuerCode());
                    // 如果没有缓存就请求restful,获取最新issuer
                    if (ncdIssuer == null) {
                        logger.warn("Failed to get ncdIssuers by cache");
                        ncdIssuer = getLatestIssuerById(userId, item.getIssuerCode());
                    }
                    String bankType = ncdIssuer.getInstitutionType();
                    // String rate = ncdIssuer.getRate();
                    String rate = item.getRate();
                    if (BusinessUtils.matchRate(rates, rate)
                            && bankType != null && (bankTypes.contains(NcdConstants.ALL) || bankTypes.contains(bankType))
                            && BusinessUtils.inRange(totalAssets, ncdIssuer.getTotalAsset())
                            && BusinessUtils.inRange(netAssets, ncdIssuer.getNetAsset())
                            && BusinessUtils.inRange(netProfits, ncdIssuer.getNetProfit())
                            && BusinessUtils.inRange(revenues, ncdIssuer.getRevenue())
                            && BusinessUtils.inRange(ccars, ncdIssuer.getCcar())
                            && BusinessUtils.inRange(badRatios, ncdIssuer.getBadRatio())
                            && BusinessUtils.inRange(ldps, ncdIssuer.getLdp())) {
                        filterQuotes.add(item);
                    }
                }
            } else {
                logger.warn("the userId {} not save the filter", userId);
                filterQuotes = quotes;
            }
            return filterQuotes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to get user filters, err:{}", e.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
    }

    private NcdIssuer getLatestIssuerById(String userId, String issuerCode) {
        try {
            NcdIssuer ncdIssuer = new NcdIssuer();
            JSONObject object = qpwbAccess.getLatestIssuer(userId, issuerCode);
            if (object == null) {
                logger.warn("not get the ncd issuer info by qpwb");
                return ncdIssuer;
            }
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                String issuerValue = (String) ((JSONObject) entry.getValue()).get("indexValue");
                switch (entry.getKey()) {
                    case NcdConstants.TOTAL_ASSETS:
                        ncdIssuer.setTotalAsset(issuerValue);
                        break;
                    case NcdConstants.NET_MARGIN:
                        ncdIssuer.setTotalAsset(issuerValue);
                        break;
                    case NcdConstants.CLEAN_ASSETS:
                        ncdIssuer.setNetAsset(issuerValue);
                        break;
                    case NcdConstants.INCOME:
                        ncdIssuer.setRevenue(issuerValue);
                        break;
                    case NcdConstants.LOAN_DEPOSIT_RATIO:
                        ncdIssuer.setLdp(issuerValue);
                        break;
                    case NcdConstants.CCAR:
                        ncdIssuer.setCcar(issuerValue);
                        break;
                    case NcdConstants.REJECT_RATIO:
                        ncdIssuer.setBadRatio(issuerValue);
                        break;
                    case NcdConstants.RATE:
                        ncdIssuer.setRate(issuerValue);
                        break;
                    case NcdConstants.INSTITUTION:
                        ncdIssuer.setInstitutionType(issuerValue);
                        break;
                    case NcdConstants.INDEX_DATE:
                        ncdIssuer.setIndexDate(issuerValue);
                        break;
                    case NcdConstants.PLAN_AMOUNT_TEXT:
                        break;
                    default:
                        logger.warn("the latest issuer type {} not in support list", entry.getKey());
                        break;
                }
            }
            return ncdIssuer;
        } catch (Exception ex) {
            logger.error("Failed to get latest ncd issuer,err:{}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public NullDTO order(OrderCondition orderCondition) {
        saveUserContact(orderCondition);
        String userId = orderCondition.getUserId();
        String brokerId = orderCondition.getBrokerId();
        Collection<String> brokers = iamAccess.getBrokersByUserAuth(userId);
        if (!brokers.contains(brokerId)) {
            logger.warn("the user {} does not have the access the to order brokerId {}", userId, brokerId);
            throw NcdExceptionModel.NotAuthorizedUser;
        }
        Orders orders = conditionToDBUtil.convertOrderConditionToOrders(orderCondition);
        try {
            Orders ordersTemp = ordersRepo.findByQuoteDetailIdAndUserIdAndBrokerIdAndIssuerId(
                    orders.getQuoteDetailId(), orders.getUserId(), orders.getBrokerId(), orders.getIssuerId());

            if (ordersTemp != null) {
                logger.warn("userId={} brokerId={} quoteDetailId={} exist in DB.",
                        ordersTemp.getUserId(), ordersTemp.getBrokerId(), ordersTemp.getQuoteDetailId());
                return ResponseUtil.nullDTO("没有找到该订单对应的数据");
            }
            ordersRepo.saveAndFlush(orders);
            sendMq(orderCondition, orders);
        } catch (NcdWarnException e) {
            throw e;
        } catch (Exception e) {
            logger.error("save order error.    exception = {}", e.getMessage());
            throw NcdExceptionModel.SaveDBFailed;
        }
        return ResponseUtil.nullDTO();
    }

    @Override
    public List<IssuerDetailDTO> getQuoteDetail(String userId, String issuerCode, String rate, String issuerDate) {
        try {
            Collection<String> brokers = iamAccess.getBrokersByUserAuth(userId);
            List<BrokerDTO> brokerList = commonUtil.getBrokerList(brokers);
            List<Quotes> quotes = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode, DateUtils.convertToSqlDate(issuerDate, NcdConstants.DATE_YYYY_MM_DD), rate);

            Map<String, List<Quotes>> quoteByBrokerMap = quotes.stream().collect(Collectors.groupingBy(Quotes::getBrokerId));
            List<IssuerDetailDTO> quoteDetail = ncdConverter.buildQuoteDetail(userId, quoteByBrokerMap, brokerList);


            if (CollectionUtils.isEmpty(quoteDetail)) return quoteDetail;
            return quoteDetail;

        } catch (Exception ex) {
            logger.error("Failed to get quoteDetail data, err: {}", ex.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
    }

    @Override
    public List<ReserveDTO> getReserve(String userId) {
        /*
            1 首先根据用户 id 查询获取所有的 broker 权限，这是静态数据，程序从数据库加载至缓存,也可以直接联表查询
            2 获取到所有用户的 broker 权限后，再根据 userId + brokerId 查询并获取所有 orders 表记录
            3 获取到的 orders 表记录中的 quoteDetailId 字段，关联查询 剩余期限 和 报价
            4 返回数组 model
         */
        List<OrderDetails> orderDetails;
        String brokerId = PABrokerId;
        if (isCheckWebAuth) {
            IssuerInfo issuerInfoByQpwb = qpwbNcdIssuersAccess.getIssuerInfoByEdm(userId);
            if (issuerInfoByQpwb == null || !commonUtil.getBrokerIds().contains(issuerInfoByQpwb.getCompanyCode())) {
                logger.warn("the user {} institution {} not in support list", userId, issuerInfoByQpwb.getCompanyCode());
                throw NcdExceptionModel.UserNotMatchBrokers;
            }
            if (!iamPermissionAccess.isAuthByUserId(userId)) {
                logger.warn("the user {} not the permission get get reserve info");
                throw NcdExceptionModel.NotAuthorizedUser;
            }
            brokerId = issuerInfoByQpwb.getCompanyCode();
        }
        try {
            orderDetails = orderDetailsRepo.findOrderDetailsByBrokerId(brokerId);
        } catch (Exception e) {
            logger.error("get userId={}'s order details error.   exception={}", userId, e.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
        return dbToDtoUtil.convertOrderDetailsToOrderDetailsDTO(orderDetails);
    }

    @Override
    public List<QuoteDetailDTO> getTimeDetail(String detailId, String userId) {
        Collection<String> brokers = iamAccess.getBrokersByUserAuth(userId);
        List<QuoteDetailDTO> details = new ArrayList<>();
        Date date= DataManagementService.currentDate;
        List<QuoteDetails> quoteDetails = quoteDetailsRepo.findByOfferIdOrderByModifyTime(detailId, date);
        for (QuoteDetails obj : quoteDetails) {
            if (!brokers.contains(obj.getBrokerId())) {
                logger.warn("the user {} the detailId {} ,userId {} not get the broker {} info",
                        userId, detailId, obj.getBrokerId());
                continue;
            }
            QuoteDetailDTO detail = new QuoteDetailDTO();
            detail.setPrice(BigDecimalUtils.toDecimal(obj.getIssuePrice(), 2));
            String quoteTime = obj.getModifyTime() == null ? "" : sdfhms.format(obj.getModifyTime());
            detail.setTime(quoteTime);
            details.add(detail);
        }
        return details;
    }

    @Override
    public PageBasicDTO getPageBasic(String userId) {
        /*
            1 返回固定 broker 标签，通过配置文件获取，程序每次启动会自定加载
            2 获取 发行日 和 起息，分别是当天后面的第一个工作日和第二个工作日
            3 获取到期日是否为工作日
                逻辑：到期日=今天+2个工作日+期限，例如3M，则到期日为今天+2个工作日+3个自然月
            4 从 state_own_bank 表中获取 code 集合返回
            5 获取评级列表
            6 获取评级对应的机构排序
         */
        PageBasicDTO pageBasicDTO = new PageBasicDTO();
        Collection<String> brokers;
        try {
            brokers = iamAccess.getBrokersByUserAuth(userId);
        } catch (Exception ex) {
            throw NcdExceptionModel.AllApiNotAuthorizedToUser;
        }

        IssuerInfo issuerInfo = qpwbNcdIssuersAccess.getIssuerInfoByUserId(userId);
        if (issuerInfo != null) {
            pageBasicDTO.setQq(issuerInfo.getQq());
            pageBasicDTO.setMobile(issuerInfo.getMobile());
            pageBasicDTO.setUserName(issuerInfo.getUsername());
            pageBasicDTO.setCustomerOrgName(issuerInfo.getCustomerOrgName());
            pageBasicDTO.setTelephone(issuerInfo.getTelephone());
            pageBasicDTO.setCompanyCode(issuerInfo.getCompanyCode());
        }

        pageBasicDTO.setBrokers(commonUtil.getBrokerList(brokers));
        String nextHoliday1 = cdhHolidayAccess.getWorkdayByInt(LocalDate.now().toString(), 1, NcdConstants.MARKET_CIB);
        String nextHoliday2 = cdhHolidayAccess.getWorkdayByInt(nextHoliday1, 1, NcdConstants.MARKET_CIB);
        pageBasicDTO.setIssueDate(nextHoliday1);
        pageBasicDTO.setStartDate(nextHoliday2);
        pageBasicDTO.setDueDate(cdhHolidayAccess.getDueDayMap());
        pageBasicDTO.setRates(RateEnum.toList());
        pageBasicDTO.setBrokerReportUrl(brokerReportUrl);
        try {
            List<String> stateOwnBank = stateOwnBankService.stateOwnBank;
            // 判断缓存中是否有数据，如果没有则重新请求数据库，否则直接拿取本地缓存数据
            if (CollectionUtils.isEmpty(stateOwnBank)) {
                stateOwnBankService.loadAllStateOwnBank();
                if (CollectionUtils.isEmpty(stateOwnBank)) {
                    logger.warn("get issue codes size is 0 from DB");
                }
                else {
                    pageBasicDTO.getGroupItems().put(pageBasicDTO.getRates().get(0), stateOwnBank);
                }
            } else {
                pageBasicDTO.getGroupItems().put(pageBasicDTO.getRates().get(0), stateOwnBank);
            }
        } catch (Exception e) {
            logger.error("get issue codes from DB error.   exception={}", e.getMessage());
        }
        return pageBasicDTO;
    }

    @Override
    public NullDTO saveFilter(FilterCondition filterCondition) {
        UserFilters userFilters = conditionToDBUtil.convertFilterConditionToUserFilter(filterCondition);
        try {
            userFiltersRepo.saveAndFlush(userFilters);
        } catch (Exception e) {
            logger.error("DB save user filter error.  exception:{}", e.getMessage());
            throw NcdExceptionModel.SaveDBFailed;
        }
        return ResponseUtil.nullDTO();
    }

    @Override
    public FilterDTO filter(String userId) {
        UserFilters userFilters;
        try {
            userFilters = userFiltersRepo.findByUserId(userId);
        } catch (Exception e) {
            logger.error("get userId={}'s filter error.   exception={}", userId, e.getMessage());
            throw NcdExceptionModel.FindDBFailed;
        }
        return dbToDtoUtil.convertUserFiltersToFilterDTO(userFilters);
    }

    @Override
    public Collection<String> getBrokers(String userId) {
        Collection<String> brokers;
        try {
            brokers = iamAccess.getBrokersByUserAuth(userId);
        } catch (Exception e) {
            logger.error("Failed to get brokers by {}, err: {}", userId, e.getMessage());
            throw NcdExceptionModel.NotAuthorizedUser;
        }
        return brokers;
    }

    @Override
    public WebBondResponse subscribeNcdFilter(NcdSubCondition body) {
        String token = body.getToken();
        String brokerId = body.getBrokerId();
        String type = body.getType();
        String userId = body.getUserId();
        try {
            logger.info("do subscribe token:{} type:{} brokerId:{} userId:{}", token, type, brokerId, userId);
            Collection<String> brokers = getBrokers(userId);
            if (brokers == null || !brokers.contains(brokerId)) {
                logger.error("the user {} does not access to subscribe broker {}", userId, brokerId);
                throw NcdExceptionModel.NotAuthorizedUser;
            }
            List<String> institutionCodes = new ArrayList<>();
            List<String> brokerIds = new ArrayList<>();
            brokerIds.add(brokerId);
            Map<String, String> params = new HashMap<>();
            params.put("userId", userId);
            switch (type) {
                case NcdConstants.NCD_TYPE_ALL:
                    institutionCodes.add(NcdConstants.ANY);
                    break;
                case NcdConstants.NCD_TYPE_FAVOURITE:
                    institutionCodes.addAll(getAttention(userId).getAttentionList());
                    break;
                case NcdConstants.NCD_TYPE_FILTER:
                    FilterDTO filterDTO = filter(userId);
                    institutionCodes = getMatchFilterIssuerCodes(params, userId, filterDTO);
                    break;
                default:
                    logger.error("the subscribe type is not in support list");
                    return null;
            }
            return webBondServerAccess.subscribe(StringUtils.join(institutionCodes, ","), body);
        } catch (Exception ex) {
            logger.error("Failed to subscribe ncd, err:{}, userId:{}, brokerId:{} token:{}, type: {}", ex.getMessage(),
                    userId, brokerId, token, type);
            throw ex;
        }
    }

    private List<String> getMatchFilterIssuerCodes(Map<String, String> params, String userId, FilterDTO filterDTO) {
        List<String> institutionCodes = new ArrayList<>();
        try {
            Map<String, NcdIssuer> ncdIssuer = NcdIssuersService.cachedAllNcdIssuers;
            if (ncdIssuer == null || ncdIssuer.size() == 0) {
                ncdIssuersService.loadAllNcdIssuers(userId);
                ncdIssuer = NcdIssuersService.cachedAllNcdIssuers;
                if (ncdIssuer == null || ncdIssuer.size() == 0) {
                    return institutionCodes;
                }
            }
            if (filterDTO != null) {
                List<String> rates = filterDTO.getRate();
                List<String> institutionTypes = filterDTO.getInstitutionType();
                List<BigDecimal> totalAssets = filterDTO.getTotalAsset();
                List<BigDecimal> netAssets = filterDTO.getNetAsset();
                List<BigDecimal> revenues = filterDTO.getRevenue();
                List<BigDecimal> netProfits = filterDTO.getNetProfit();
                List<BigDecimal> ldps = filterDTO.getLdp();
                List<BigDecimal> ccars = filterDTO.getCcar();
                List<BigDecimal> badRatios = filterDTO.getBadRatio();
                for (NcdIssuer issuer : ncdIssuer.values()) {
                    String rate = issuer.getRate();
                    String bankType = issuer.getInstitutionType();
                    if (rate != null && (rates.contains(NcdConstants.ALL) || rates.contains(rate))
                            && bankType != null && (institutionTypes.contains(NcdConstants.ALL) || institutionTypes.contains(bankType))
                            && BusinessUtils.inRange(totalAssets, issuer.getTotalAsset())
                            && BusinessUtils.inRange(netAssets, issuer.getNetAsset())
                            && BusinessUtils.inRange(netProfits, issuer.getNetProfit())
                            && BusinessUtils.inRange(revenues, issuer.getRevenue())
                            && BusinessUtils.inRange(ccars, issuer.getCcar())
                            && BusinessUtils.inRange(badRatios, issuer.getBadRatio())
                            && BusinessUtils.inRange(ldps, issuer.getLdp())) {
                        institutionCodes.add(issuer.getIssuerCode());
                    }
                }
            } else {
                institutionCodes.addAll(ncdIssuer.keySet());
            }
            return institutionCodes;
        } catch (Exception ex) {
            logger.error("Failed to get matched issuerCode by filter, err:", ex.getMessage());
            return institutionCodes;
        }
    }

    @Override
    public WebBondResponse unSubscribeNcdFilter(NcdSubCondition condition) {
        String userId = condition.getUserId();
        String brokerId = condition.getBrokerId();
        String token = condition.getToken();
        try {
            logger.info("cancel subscribe token:{}", token);
            Collection<String> brokers = getBrokers(userId);
            if (!brokers.contains(condition.getBrokerId())) {
                logger.error("the user {} does not access to subscribe broker {}", userId, brokerId);
                return null;
            }
            List<String> brokerIds = new ArrayList<>();
            brokerIds.add(brokerId);
            return webBondServerAccess.unSubscribe("", condition);
        } catch (Exception ex) {
            logger.error("Failed to unSubscribe Ncd, err:{}, userId: {}, brokerId:{}, token:{}",
                    ex.getMessage(), userId, brokerId, token);
        }
        return null;
    }

    public void saveUserContact(OrderCondition userContactCondition) {
        try {
            UserContact contact = conditionToDBUtil.convertToUserContact(userContactCondition);
            QpwbNcdIssuersAccess.cachedUserContactByUserId.put(userContactCondition.getUserId(), contact);
            userContactRepo.save(contact);
        } catch (Exception e) {
            logger.error("save user contact error.  exception={}", e.getMessage());
            throw NcdExceptionModel.SaveDBFailed;
        }
    }

    private void sendMq(OrderCondition orderCondition, Orders order) {
        ReserveDTO reserveDto = new ReserveDTO();
        reserveDto.setBrokerId(orderCondition.getBrokerId());
        reserveDto.setId(Long.toString(order.getId()));
        reserveDto.setInstitutionName(orderCondition.getCustomerOrgName());
        reserveDto.setTrader(orderCondition.getUserName());
        reserveDto.setLimit(orderCondition.getLimit());
        reserveDto.setBankName(orderCondition.getIssuerName());
        reserveDto.setPrice(orderCondition.getPrice());
        reserveDto.setDateTime(order.getCreateTime().toLocalDateTime()
                .format(DateTimeFormatter.ofPattern(NcdConstants.DATE_YMD_HMS)));
        reserveDto.setQq(orderCondition.getQq());
        reserveDto.setMobile(orderCondition.getMobile());
        reserveDto.setTelephone(orderCondition.getTelephone());
        reserveDto.setFixRate(orderCondition.isFixRate());
        sendReservedHandler.sendMqMsg(JSONObject.toJSONBytes(reserveDto));
    }

    public Page<OrderDetails> findOrderDetailsByUserIdByLimit(String brokerId, int pageStart, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "create_time");
        Pageable pageable = new PageRequest(pageStart, pageSize, sort);
        return orderDetailsRepo.findOrderDetailsByBrokerIdByLimit(brokerId, pageable);
    }
}
