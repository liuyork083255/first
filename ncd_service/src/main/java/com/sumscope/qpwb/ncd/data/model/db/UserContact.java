package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_contact", schema = "wf_web_ncd_qa", catalog = "")
public class UserContact {
    @Transient
    private long autoId;
    private String userId;
    private String userName;
    private String qq;
    private String mobile;
    private String telephone;
    private String companyCode;
    private String customerOrgName;

    @Column(name = "auto_id")
    @Transient
    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long auotId) {
        this.autoId = auotId;
    }

    @Id
    @Column(name = "user_id", nullable = true, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 15)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "qq", nullable = true, length = 15)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 15)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "company_code", nullable = true, length = 32)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 11)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "customer_org_name", nullable = true, length = 15)
    public String getCustomerOrgName() {
        return customerOrgName;
    }

    public void setCustomerOrgName(String customerOrgName) {
        this.customerOrgName = customerOrgName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserContact that = (UserContact) o;
        return autoId == that.autoId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(qq, that.qq) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(autoId, userId, qq, mobile, telephone);
    }
}
