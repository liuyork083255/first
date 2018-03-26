package com.sumscope.qpwb.ncd.web.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by liu.yang on 2018/1/10.
 */
@ApiModel
public class AttentionCondition {
    @ApiModelProperty(value = "取消或关注", required = true)
    @Pattern(regexp = "add|delete",message = "参数status取值范围 add|delete")
    private String status;
    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;
    @ApiModelProperty(value = "机构", required = true)
    @NotBlank(message = "can not be null and it's length can not be 0")
    private String institution;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
