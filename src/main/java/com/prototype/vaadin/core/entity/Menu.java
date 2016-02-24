package com.prototype.vaadin.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.menu")
public class Menu {
    private String uuid;

    private String key;

    private String parentkey;

    private String isalive;

    private Short sort;

    private String component;

    private String name;

    private String creator;

    private Date createdate;

    private String editor;

    private Date editdate;

    public static final String UUID = "uuid";

    public static final String KEY = "key";

    public static final String PARENTKEY = "parentkey";

    public static final String ISALIVE = "isalive";

    public static final String SORT = "sort";

    public static final String COMPONENT = "component";

    public static final String NAME = "name";

    public static final String CREATOR = "creator";

    public static final String CREATEDATE = "createdate";

    public static final String EDITOR = "editor";

    public static final String EDITDATE = "editdate";

    @Id
    @Column(name="uuid", length=36, nullable=false)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name="key", length=36, nullable=false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name="parentkey", length=36)
    public String getParentkey() {
        return parentkey;
    }

    public void setParentkey(String parentkey) {
        this.parentkey = parentkey;
    }

    @Column(name="isalive", length=1, nullable=false)
    public String getIsalive() {
        return isalive;
    }

    public void setIsalive(String isalive) {
        this.isalive = isalive;
    }

    @Column(name="sort", length=5)
    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    @Column(name="component", length=100)
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Column(name="name", length=36, nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="creator", length=20, nullable=false)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name="createdate", length=13)
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Column(name="editor", length=20)
    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Column(name="editdate", length=13)
    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("uuid=").append(uuid);
        sb.append(", key=").append(key);
        sb.append(", parentkey=").append(parentkey);
        sb.append(", isalive=").append(isalive);
        sb.append(", sort=").append(sort);
        sb.append(", component=").append(component);
        sb.append(", name=").append(name);
        sb.append(", creator=").append(creator);
        sb.append(", createdate=").append(createdate);
        sb.append(", editor=").append(editor);
        sb.append(", editdate=").append(editdate);
        
        sb.append("}");
        return sb.toString();
    }
}