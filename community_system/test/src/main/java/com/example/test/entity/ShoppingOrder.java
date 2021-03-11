package com.example.test.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ShoppingOrder {
	private int shoppingOrderId;
	private String userId;
	private BigDecimal totalMoney;
	private Timestamp orderDate;
	public int getShoppingOrderId() {
		return shoppingOrderId;
	}
	public void setShoppingOrderId(int shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

}
