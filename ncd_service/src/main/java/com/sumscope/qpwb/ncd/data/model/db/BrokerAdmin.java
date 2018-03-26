package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "broker_admin", schema = "web_ncd", catalog = "")
public class BrokerAdmin {
    private long autoId;
    private String id;
    private String name;

    @Id
    @Column(name = "auto_id")
    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }
    @Basic
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokerAdmin that = (BrokerAdmin) o;
        return autoId == that.autoId &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(autoId, id, name);
    }
}
