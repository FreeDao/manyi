/**
 * 
 */
package com.manyi.hims.actionexcel.model;


public class BDWorkCountModel {

	private String name;//BDName
	private int directMarket;//直接推广
	private int indirectPromotion;//间接推广
	private int activeUser;//活跃中介
	private int createRentCount;//发布出租量
	private int createSellCount;//发布出售量
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDirectMarket() {
		return directMarket;
	}
	public void setDirectMarket(int directMarket) {
		this.directMarket = directMarket;
	}
	public int getIndirectPromotion() {
		return indirectPromotion;
	}
	public void setIndirectPromotion(int indirectPromotion) {
		this.indirectPromotion = indirectPromotion;
	}
	public int getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(int activeUser) {
		this.activeUser = activeUser;
	}
	public int getCreateRentCount() {
		return createRentCount;
	}
	public void setCreateRentCount(int createRentCount) {
		this.createRentCount = createRentCount;
	}
	public int getCreateSellCount() {
		return createSellCount;
	}
	public void setCreateSellCount(int createSellCount) {
		this.createSellCount = createSellCount;
	}
	
}
