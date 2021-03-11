package com.example.test.service;

import com.example.test.entity.Payment;

import java.util.List;

public interface PaymentService {
    int getPaymentCount(); //获取收费单的数量
    Payment getPaymentById(int paymentId); //通过流水号查找缴费信息
    List<Payment> getPaymentList(int page,int limit);
    List<Payment> getPaymentListById(int paymentId, int page,int limit);
    int addPayment(Payment payment); //添加一个缴费信息
    int updatePayment(Payment payment); //通过主键缴费流水号更新缴费信息
    int deletePayment(int paymentId); //通过主键缴费流水号删除缴费信息

    //________________以下是用户缴费模块用到的方法_________________________________
    int getPaymentCountForUser(int householdId);
    List<Payment> getPaymentListForUser(int householdId,int page,int limit);
    List<Payment> getPaymentListByIdForUser(int householdId,int paymentId,int page,int limit);
}
