package com.example.test.mapper;

import com.example.test.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {
    int getPaymentCount(); //获取收费单的数量
    Payment getPaymentById(@Param("paymentId") int paymentId); //通过流水号查找缴费信息
    List<Payment> getPaymentList(@Param("page") int page,@Param("limit") int limit);
    List<Payment> getPaymentListById(@Param("paymentId") int paymentId,@Param("page") int page,@Param("limit") int limit);
    int addPayment(@Param("payment") Payment payment); //添加一个缴费信息
    int updatePayment(@Param("payment") Payment payment); //通过主键缴费流水号更新缴费信息
    int deletePayment(@Param("paymentId") int paymentId); //通过主键缴费流水号删除缴费信息

    //________________________________用户缴费模块用到的方法__________________________________________
    int getPaymentCountForUser(@Param("householdId") int householdId);
    List<Payment> getPaymentListForUser(@Param("householdId") int householdId, @Param("page") int page,@Param("limit") int limit);
    List<Payment> getPaymentListByIdForUser(@Param("householdId") int householdId,@Param("paymentId") int paymentId,@Param("page") int page,@Param("limit") int limit);
}
