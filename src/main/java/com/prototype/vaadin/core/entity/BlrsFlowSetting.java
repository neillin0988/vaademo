package com.prototype.vaadin.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IA_SERVICECH.BLR_S_FLOW_SETTING")
public class BlrsFlowSetting extends BlrsFlowSettingKey {
    private String flowDefine;

    private String createuser;

    private Date createdate;

    private String updateuser;

    private Date updatedate;

    public static final String SRVID = "SRV_ID";

    public static final String SRCDEVICE = "SRC_DEVICE";

    public static final String ITEMCODE = "ITEM_CODE";

    public static final String FLOWDEFINE = "FLOW_DEFINE";

    public static final String CREATEUSER = "CREATEUSER";

    public static final String CREATEDATE = "CREATEDATE";

    public static final String UPDATEUSER = "UPDATEUSER";

    public static final String UPDATEDATE = "UPDATEDATE";

    @Column(name="FLOW_DEFINE", length=255)
    public String getFlowDefine() {
        return flowDefine;
    }

    public void setFlowDefine(String flowDefine) {
        this.flowDefine = flowDefine;
    }

    @Column(name="CREATEUSER", length=10)
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Column(name="CREATEDATE", length=7, nullable=false)
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Column(name="UPDATEUSER", length=10)
    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    @Column(name="UPDATEDATE", length=7)
    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("flowDefine=").append(flowDefine);
        sb.append(", createuser=").append(createuser);
        sb.append(", createdate=").append(createdate);
        sb.append(", updateuser=").append(updateuser);
        sb.append(", updatedate=").append(updatedate);
        
        sb.append("}");
        return sb.toString();
    }
}