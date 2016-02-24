package com.prototype.vaadin.web.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author neil.lin
 * 
 */
@SuppressWarnings("serial")
@Component
@Scope("session")
public class SessionInfo implements Serializable {

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}