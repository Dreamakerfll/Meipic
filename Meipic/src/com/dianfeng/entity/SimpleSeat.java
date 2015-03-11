package com.dianfeng.entity;

public class SimpleSeat {
	private String agentNumber;
	private String account;
	private String status;
	public SimpleSeat(){}
	
	public SimpleSeat(String agentNumber,String account,String status){
		this.agentNumber = agentNumber;
		this.account = account;
		this.status = status;
	}
	
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
