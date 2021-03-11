package com.example.test.mapper;

import com.example.test.entity.Logistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogisticMapper {
    int getLogisticCount(); //获取物流信息的个数
    Logistic getLogisticById(@Param("logisticId") int logisticId); //通过主键物流单流水号查询物流信息
    List<Logistic> getLogisticList(@Param("page") int page,@Param("limit") int limit);
    List<Logistic> getLogisticListById(@Param("logisticId") int logisticId,@Param("page") int page,@Param("limit") int limit);
    int updateLogistic(@Param("logistic") Logistic logistic);
    int addLogistic(@Param("logistic") Logistic logistic);
    int deleteLogistic(@Param("logisticId") int logisticId); //通过主键删除物流订单，这个函数用不到

    //_________________________用户物流模块的方法___________________________
    int getLogisticCountForUser(@Param("userId") String userId);
    List<Logistic> getLogisticListForUser(@Param("userId") String userId, @Param("page") int page,@Param("limit") int limit);
    List<Logistic> getLogisticListByIdForUser(@Param("userId") String userId, @Param("logisticId") int logisticId,@Param("page") int page,@Param("limit") int limit);
}
