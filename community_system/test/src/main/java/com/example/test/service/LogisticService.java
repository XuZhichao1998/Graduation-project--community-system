package com.example.test.service;

import com.example.test.entity.Logistic;

import java.util.List;

public interface LogisticService {
    int getLogisticCount(); //获取物流信息的个数
    Logistic getLogisticById(int logisticId); //通过主键物流单流水号查询物流信息
    List<Logistic> getLogisticList(int page,int limit);
    List<Logistic> getLogisticListById(int logisticId,int page,int limit);
    int updateLogistic(Logistic logistic);
    int addLogistic(Logistic logistic);
    int deleteLogistic(int logisticId); //通过主键删除物流订单，这个函数用不到

    //______________________用户某块调用的方法___________________________________
    int getLogisticCountForUser(String userId);
    List<Logistic> getLogisticListForUser(String userId,int page,int limit);
    List<Logistic> getLogisticListByIdForUser(String userId, int logisticId, int page, int limit);
}
