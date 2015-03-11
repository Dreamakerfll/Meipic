package com.dianfeng.entity;

public class ReturnSeat {
	private String queue;
	private String role;				//角色
	private String jurisdictions;		//权限 
	private String regionals;			//区域
	private String agentNumber;
	private String number;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getJurisdictions() {
		return jurisdictions;
	}
	public void setJurisdictions(String jurisdictions) {
		this.jurisdictions = jurisdictions;
	}
	public String getRegionals() {
		return regionals;
	}
	public void setRegionals(String regionals) {
		this.regionals = regionals;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
