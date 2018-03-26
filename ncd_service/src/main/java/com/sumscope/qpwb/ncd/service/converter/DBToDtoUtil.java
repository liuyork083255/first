package com.sumscope.qpwb.ncd.service.converter;

import com.rabbitmq.http.client.domain.UserInfo;
import com.sumscope.qpwb.ncd.data.model.db.OrderDetails;
import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import com.sumscope.qpwb.ncd.data.model.db.UserFilters;
import com.sumscope.qpwb.ncd.data.model.iam.IssuerInfo;
import com.sumscope.qpwb.ncd.data.model.repository.UserContactRepo;
import com.sumscope.qpwb.ncd.domain.FilterDTO;
import com.sumscope.qpwb.ncd.domain.ReserveDTO;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.MiddleTerminalEnum;
import com.sumscope.qpwb.ncd.utils.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DBToDtoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBToDtoUtil.class);
    @Autowired
    private UserContactRepo userContactRepo;

    public FilterDTO convertUserFiltersToFilterDTO(UserFilters uf){
        FilterDTO filterDTO = new FilterDTO();
        if(uf == null){
            filterDTO.setSaveFlag(false);
            return filterDTO;
        }else filterDTO.setSaveFlag(true);

        filterDTO.setRate(getStrListByString(uf.getRating()));
        filterDTO.setInstitutionType(getStrListByString(uf.getInstitutionType()));
        filterDTO.setTotalAsset(getBigDecimalListByString(uf.getTotalAsset()));
        filterDTO.setNetAsset(getBigDecimalListByString(uf.getNetAsset()));
        filterDTO.setRevenue(getBigDecimalListByString(uf.getRevenue()));
        filterDTO.setNetProfit(getBigDecimalListByString(uf.getNetProfit()));
        filterDTO.setLdp(getBigDecimalListByString(uf.getLdp()));
        filterDTO.setCcar(getBigDecimalListByString(uf.getCcar()));
        filterDTO.setBadRatio(getBigDecimalListByString(uf.getBadRatio()));

        return  filterDTO;
    }

    public List<ReserveDTO> convertOrderDetailsToOrderDetailsDTO(List<OrderDetails> orderDetail){
        List<ReserveDTO> listOrderDetails = new ArrayList<>();
        if(CollectionUtils.isEmpty(orderDetail)) return listOrderDetails;
        orderDetail.forEach(od -> {
            ReserveDTO reserveDTO = new ReserveDTO();
            reserveDTO.setId(String.valueOf(od.getId()));
            reserveDTO.setLimit(MiddleTerminalEnum.terminalEnum(od.getTerminal()).getTerminal());
            reserveDTO.setBankName(od.getIssuerName());
            reserveDTO.setPrice(BigDecimalUtils.toDecimal(od.getIssuePrice(),2));
            reserveDTO.setDateTime(od.getCreateTime().toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern(NcdConstants.DATE_YMD_HMS)));

            String userId = od.getUserId();
            UserContact userContact = userContactRepo.findByUserId(userId);
            if(userContact != null){
                reserveDTO.setQq(userContact.getQq());
                reserveDTO.setMobile(userContact.getMobile());
                reserveDTO.setTelephone(userContact.getTelephone());
                reserveDTO.setInstitutionName(userContact.getCustomerOrgName());
                reserveDTO.setTrader(userContact.getUserName());
            }

            reserveDTO.setFixRate(od.getPriceType());
            listOrderDetails.add(reserveDTO);
        });
        return listOrderDetails;
    }

    private List<String> getStrListByString(String str){
        if(!StringUtils.isBlank(str)){
            return Arrays.asList(str.split(":"));
        }else{
            return null;
        }
    }

    private List<BigDecimal> getBigDecimalListByString(String str){
        if (!StringUtils.isBlank(str)){
            List<BigDecimal> list = new ArrayList<>();
            try {
                list.add(new BigDecimal(str.split(":")[0]));
                list.add(new BigDecimal(str.split(":")[1]));
            } catch (Exception e) {
                LOGGER.error("str={} convert to List<Integer> error.    exception={}",str,e.getMessage());
                return null;
            }
            return list;
        }else{
            return null;
        }
    }
}
