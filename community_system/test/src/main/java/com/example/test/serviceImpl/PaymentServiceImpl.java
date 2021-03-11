package com.example.test.serviceImpl;

import com.example.test.entity.Payment;
import com.example.test.mapper.PaymentMapper;
import com.example.test.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    //将DAO注入Service层
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public int getPaymentCount() {
        return paymentMapper.getPaymentCount();
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        return paymentMapper.getPaymentById(paymentId);
    }

    @Override
    public List<Payment> getPaymentList(int page, int limit) {
        return paymentMapper.getPaymentList(page,limit);
    }

    @Override
    public List<Payment> getPaymentListById(int paymentId, int page, int limit) {
        return paymentMapper.getPaymentListById(paymentId,page,limit);
    }

    @Override
    public int addPayment(Payment payment) {
        return paymentMapper.addPayment(payment);
    }

    @Override
    public int updatePayment(Payment payment) {
        return paymentMapper.updatePayment(payment);
    }

    @Override
    public int deletePayment(int paymentId) {
        return paymentMapper.deletePayment(paymentId);
    }

    //_____________________________以下是用户缴费模块调用的方法_____________________________________________________

    @Override
    public int getPaymentCountForUser(int householdId) {
        return paymentMapper.getPaymentCountForUser(householdId);
    }

    @Override
    public List<Payment> getPaymentListForUser(int householdId, int page, int limit) {
        return paymentMapper.getPaymentListForUser(householdId,page,limit);
    }

    @Override
    public List<Payment> getPaymentListByIdForUser(int householdId, int paymentId, int page, int limit) {
        return paymentMapper.getPaymentListByIdForUser(householdId,paymentId,page,limit);
    }
}
