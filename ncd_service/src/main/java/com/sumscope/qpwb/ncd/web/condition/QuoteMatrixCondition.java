package com.sumscope.qpwb.ncd.web.condition;

import com.sumscope.service.webbond.common.web.query.QueryCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liu.yang on 2018/1/10.
 */
@ApiModel(value = "报价表格查询条件")
public class QuoteMatrixCondition implements QueryCondition {
    @NotBlank(message = "can not be null and it's length can not be 0")
    @Pattern(regexp = "all|favourite|filter",message = "参数category取值范围 all|favourite|filter")
    @ApiModelProperty(value = "分类(all:全部（没有筛选条件）;filter:我的筛选)", position = 1)
    private String category;
    @NotBlank(message = "can not be null or empty")
    @ApiModelProperty(value = "收益率", position = 2)
    private BigDecimal profit;
    @NotBlank(message = "can not be null or empty")
    @ApiModelProperty(value = "用户id", position = 3)
    private String userId;
    @NotBlank(message = "can not be null or empty")
    @ApiModelProperty(value = "经济商id", position = 4)
    private String brokerId;
    @NotBlank(message = "can not be null or empty")
    @ApiModelProperty(value = "期限all[1M, 3M, 6M, 9M, 1Y]", position = 4)
    private List<String> limit;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public List<String> getLimit() {
        return limit;
    }

    public void setLimit(List<String> limit) {
        this.limit = limit;
    }
}
