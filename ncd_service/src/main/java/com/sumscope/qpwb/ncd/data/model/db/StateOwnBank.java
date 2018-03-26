package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "state_own_bank", schema = "web_ncd", catalog = "")
public class StateOwnBank {
    private long autoId;
    private String code;
    private String name;
    private String sortValue;

    @Id
    @Column(name = "auto_id")
    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sort_value")
    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateOwnBank that = (StateOwnBank) o;
        return autoId == that.autoId &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sortValue, that.sortValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(autoId, code, name, sortValue);
    }
}
