package com.prototype.vaadin.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IA_SERVICECH.BLR_S_FLOW_SETTING")
public class BlrsFlowSettingKey {
    private String srvId;

    private String srcDevice;

    private String itemCode;

    public static final String SRVID = "SRV_ID";

    public static final String SRCDEVICE = "SRC_DEVICE";

    public static final String ITEMCODE = "ITEM_CODE";

    public static final String FLOWDEFINE = "FLOW_DEFINE";

    public static final String CREATEUSER = "CREATEUSER";

    public static final String CREATEDATE = "CREATEDATE";

    public static final String UPDATEUSER = "UPDATEUSER";

    public static final String UPDATEDATE = "UPDATEDATE";

    @Id
    @Column(name="SRV_ID", length=15, nullable=false)
    public String getSrvId() {
        return srvId;
    }

    public void setSrvId(String srvId) {
        this.srvId = srvId;
    }

    @Id
    @Column(name="SRC_DEVICE", length=10, nullable=false)
    public String getSrcDevice() {
        return srcDevice;
    }

    public void setSrcDevice(String srcDevice) {
        this.srcDevice = srcDevice;
    }

    @Id
    @Column(name="ITEM_CODE", length=15, nullable=false)
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("srvId=").append(srvId);
        sb.append(", srcDevice=").append(srcDevice);
        sb.append(", itemCode=").append(itemCode);
        
        sb.append("}");
        return sb.toString();
    }
}