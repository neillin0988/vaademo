/**
 * Copyright (c) 2014 Far Eastone Telecommunications Co., Ltd.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Far Eastone Telecommunications Co., Ltd. ("Confidential Information").
 *
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of license agreement you entered
 * into with Far Eastone Telecommunications Co., Ltd.
 */
 
package com.prototype.vaadin.core;
 
/**
 *
 * @author Promeritage
 */
public class Context {
	
	public enum Message {
        system_error("系統發生錯誤, 請聯絡系統管理員"),
        system_info("系統通知"),
        system_warning("系統警告"),
        ;
        
        public String value;
    	public String getValue() {
    		return value;
    	}
    	
        private Message(String value) {
            this.value = value;
        }
    }

    public enum DataSource {
        postgres("postgres"),
        DS_Payment("DS_Payment"),
        ;
        
        public String name;
        
        private DataSource(String name) {
            this.name = name;
        }
    }
    
    public enum DateFormat {
    	
    	FULL("yyyy/MM/dd HH:mm:ss"),
    	DATE_ONLY("yyyy/MM/dd"),
    	;
    	
    	public String format;
    	
    	DateFormat(String format) {
    		this.format = format;
    	}
    	
    	public String getFormat() {
    		return format;
    	}
    }
}
