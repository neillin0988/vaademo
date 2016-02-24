package com.prototype.vaadin.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.logmsg")
public class Logmsg {
    private String guid;

    private String logWho;

    private String logWhere;

    private String logWhat;

    private Date logWhen;

    private String logMsg;

    private Long logNum;

    public static final String GUID = "guid";

    public static final String LOGWHO = "log_who";

    public static final String LOGWHERE = "log_where";

    public static final String LOGWHAT = "log_what";

    public static final String LOGWHEN = "log_when";

    public static final String LOGMSG = "log_msg";

    public static final String LOGNUM = "log_num";

    @Id
    @Column(name="guid", length=36, nullable=false)
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Column(name="log_who", length=20)
    public String getLogWho() {
        return logWho;
    }

    public void setLogWho(String logWho) {
        this.logWho = logWho;
    }

    @Column(name="log_where", length=30)
    public String getLogWhere() {
        return logWhere;
    }

    public void setLogWhere(String logWhere) {
        this.logWhere = logWhere;
    }

    @Column(name="log_what", length=40)
    public String getLogWhat() {
        return logWhat;
    }

    public void setLogWhat(String logWhat) {
        this.logWhat = logWhat;
    }

    @Column(name="log_when", length=13)
    public Date getLogWhen() {
        return logWhen;
    }

    public void setLogWhen(Date logWhen) {
        this.logWhen = logWhen;
    }

    @Column(name="log_msg", length=100)
    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @Column(name="log_num", length=10)
    public Long getLogNum() {
        return logNum;
    }

    public void setLogNum(Long logNum) {
        this.logNum = logNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("guid=").append(guid);
        sb.append(", logWho=").append(logWho);
        sb.append(", logWhere=").append(logWhere);
        sb.append(", logWhat=").append(logWhat);
        sb.append(", logWhen=").append(logWhen);
        sb.append(", logMsg=").append(logMsg);
        sb.append(", logNum=").append(logNum);
        
        sb.append("}");
        return sb.toString();
    }
}