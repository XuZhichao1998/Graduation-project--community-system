package com.example.test.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 物流信息表
 * */
public class Logistic {
	private int logisticId; //物流订单流水号
	private String logisticOrderId; //物流单号
	private Timestamp logisticSubmitDate;  //用户下单时间
	private String userId; //下单用户的编号
	private String senderName; //寄件人姓名
	private int logisticType; //寄件物品类型(外键) 1文件票证,2数码产品,3日用品,4生鲜水果,5休闲食品,6服装鞋帽,7工艺品,8珠宝首饰,9其它
	private String logisticTypeName; //寄件物品类型的名称
	private String senderPhoneNumber; //寄件人手机号码
	private String senderProvince; //寄件人所在省份
	private String senderCity; //寄件人所在市
	private String senderDistrict; //寄件人所在区(/县/乡)
	private String senderPostalCode; //寄件人邮政编码
	private String senderDetailedAddress; //寄件人详细地址
	private String receiverName; //收件人姓名
	private String receiverPhoneNumber; //收件人电话号码
	private String receiverProvince; //收件人所在省份
	private String receiverCity; //收件人所在市
	private String receiverDistrict; //收件人所在区
	private String receiverPostalCode; //接收人邮政编码
	private String receiverDetailedAddress; //收件人详细地址
	private BigDecimal logisticMoney; //寄件收费
	private Integer courierId; //处理订单的快递员编号
	private String logisticLastState; //最新物流状态信息
	private Timestamp logisticLastUpdatetime; //物流信息最后更新的时间
	private int logisticFinish; //收件人是否已经成功签收 0表示还没有 1表示已经成功签收
	public int getLogisticId() {
		return logisticId;
	}
	public void setLogisticId(int logisticId) {
		this.logisticId = logisticId;
	}
	public String getLogisticOrderId() {
		return logisticOrderId;
	}
	public void setLogisticOrderId(String logisticOrderId) {
		this.logisticOrderId = logisticOrderId;
	}
	public Timestamp getLogisticSubmitDate() {
		return logisticSubmitDate;
	}
	public void setLogisticSubmitDate(Timestamp logisticSubmitDate) {
		this.logisticSubmitDate = logisticSubmitDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public int getLogisticType() {
		return logisticType;
	}
	public void setLogisticType(int logisticType) {
		this.logisticType = logisticType; this.setTypeNameById();
	}
	public String getLogisticTypeName() {
		return logisticTypeName;
	}
	public void setLogisticTypeName(String logisticTypeName) {
		this.logisticTypeName = logisticTypeName;
	}
	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}
	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}
	public String getSenderProvince() {
		return senderProvince;
	}
	public void setSenderProvince(String senderProvince) {
		this.senderProvince = senderProvince;
	}
	public String getSenderCity() {
		return senderCity;
	}
	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}
	public String getSenderDistrict() {
		return senderDistrict;
	}
	public void setSenderDistrict(String senderDistrict) {
		this.senderDistrict = senderDistrict;
	}
	public String getSenderPostalCode() {
		return senderPostalCode;
	}
	public void setSenderPostalCode(String senderPostalCode) {
		this.senderPostalCode = senderPostalCode;
	}
	public String getSenderDetailedAddress() {
		return senderDetailedAddress;
	}
	public void setSenderDetailedAddress(String senderDetailedAddress) {
		this.senderDetailedAddress = senderDetailedAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}
	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverPostalCode() {
		return receiverPostalCode;
	}
	public void setReceiverPostalCode(String receiverPostalCode) {
		this.receiverPostalCode = receiverPostalCode;
	}
	public String getReceiverDetailedAddress() {
		return receiverDetailedAddress;
	}
	public void setReceiverDetailedAddress(String receiverDetailedAddress) {
		this.receiverDetailedAddress = receiverDetailedAddress;
	}
	public BigDecimal getLogisticMoney() {
		return logisticMoney;
	}
	public void setLogisticMoney(BigDecimal logisticMoney) {
		this.logisticMoney = logisticMoney;
	}
	public Integer getCourierId() {
		return courierId;
	}
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public String getLogisticLastState() {
		return logisticLastState;
	}
	public void setLogisticLastState(String logisticLastState) {
		this.logisticLastState = logisticLastState;
	}
	public Timestamp getLogisticLastUpdatetime() {
		return logisticLastUpdatetime;
	}
	public void setLogisticLastUpdatetime(Timestamp logisticLastUpdatetime) {
		this.logisticLastUpdatetime = logisticLastUpdatetime;
	}
	public int getLogisticFinish() {
		return logisticFinish;
	}
	public void setLogisticFinish(int logisticFinish) {
		this.logisticFinish = logisticFinish;
	}
	public Logistic(){}

	public Logistic(int logisticId, String logisticOrderId, Timestamp logisticSubmitDate, String userId, String senderName, int logisticType, String logisticTypeName, String senderPhoneNumber, String senderProvince, String senderCity, String senderDistrict, String senderPostalCode, String senderDetailedAddress, String receiverName, String receiverPhoneNumber, String receiverProvince, String receiverCity, String receiverDistrict, String receiverPostalCode, String receiverDetailedAddress, BigDecimal logisticMoney, Integer courierId, String logisticLastState, Timestamp logisticLastUpdatetime, int logisticFinish) {
		this.logisticId = logisticId;
		this.logisticOrderId = logisticOrderId;
		this.logisticSubmitDate = logisticSubmitDate;
		this.userId = userId;
		this.senderName = senderName;
		this.logisticType = logisticType;
		this.logisticTypeName = logisticTypeName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.senderProvince = senderProvince;
		this.senderCity = senderCity;
		this.senderDistrict = senderDistrict;
		this.senderPostalCode = senderPostalCode;
		this.senderDetailedAddress = senderDetailedAddress;
		this.receiverName = receiverName;
		this.receiverPhoneNumber = receiverPhoneNumber;
		this.receiverProvince = receiverProvince;
		this.receiverCity = receiverCity;
		this.receiverDistrict = receiverDistrict;
		this.receiverPostalCode = receiverPostalCode;
		this.receiverDetailedAddress = receiverDetailedAddress;
		this.logisticMoney = logisticMoney;
		this.courierId = courierId;
		this.logisticLastState = logisticLastState;
		this.logisticLastUpdatetime = logisticLastUpdatetime;
		this.logisticFinish = logisticFinish;
	}

	public void setTypeNameById() {
		switch(logisticType) {
			case 1:
				logisticTypeName = "文件票证";
				break;
			case 2:
				logisticTypeName = "数码产品";
				break;
			case 3:
				logisticTypeName = "日用品";
				break;
			case 4:
				logisticTypeName = "生鲜水果";
				break;
			case 5:
				logisticTypeName = "休闲食品";
				break;
			case 6:
				logisticTypeName = "服装鞋帽";
				break;
			case 7:
				logisticTypeName = "工艺品";
				break;
			case 8:
				logisticTypeName = "珠宝首饰";
				break;
			default:
				logisticTypeName = "其它";
				break;
		}
	}
	public String showMsg() {
		return "logisticId:"+logisticId+",userId:"+userId+",senderName:"+senderName+",logisticType:"+logisticType+",logisticTypeName:"+logisticTypeName;
	}
}
