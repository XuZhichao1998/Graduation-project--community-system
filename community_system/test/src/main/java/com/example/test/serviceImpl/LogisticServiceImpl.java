package com.example.test.serviceImpl;

import com.example.test.entity.Logistic;
import com.example.test.mapper.LogisticMapper;
import com.example.test.service.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticServiceImpl implements LogisticService {
    //将DAO注入Service层
    @Autowired
    private LogisticMapper logisticMapper;


    @Override
    public int getLogisticCount() {
        return logisticMapper.getLogisticCount();
    }

    @Override
    public Logistic getLogisticById(int logisticId) {
        Logistic result = logisticMapper.getLogisticById(logisticId);
        result.setTypeNameById();
        System.out.println(result.showMsg());
        return result;
    }

    @Override
    public List<Logistic> getLogisticList(int page, int limit) {
        List<Logistic> resList = logisticMapper.getLogisticList(page,limit);
        List<Logistic> resList2 = new ArrayList<Logistic>();
        for(Logistic m:resList) {
            m.setTypeNameById();
            resList2.add(m);
        }
        for(Logistic p:resList2) {
            System.out.println(p.showMsg());
        }
        return resList2;
    }

    @Override
    public List<Logistic> getLogisticListById(int logisticId, int page, int limit) {
        List<Logistic> resList = logisticMapper.getLogisticListById(logisticId,page,limit);
        List<Logistic> resList2 = new ArrayList<Logistic>();
        for(Logistic m:resList) {
            m.setTypeNameById();
            resList2.add(m);
        }
        for(Logistic p:resList2) {
            System.out.println(p.showMsg());
        }
        return resList2;
    }

    @Override
    public int updateLogistic(Logistic logistic) {
        return logisticMapper.updateLogistic(logistic);
    }

    @Override
    public int addLogistic(Logistic logistic) {
        return logisticMapper.addLogistic(logistic);
    }

    @Override
    public int deleteLogistic(int logisticId) {
        return logisticMapper.deleteLogistic(logisticId);
    }

    @Override
    public int getLogisticCountForUser(String userId) {
        return logisticMapper.getLogisticCountForUser(userId);
    }

    @Override
    public List<Logistic> getLogisticListForUser(String userId, int page, int limit) {
        return logisticMapper.getLogisticListForUser(userId,page,limit);
    }

    @Override
    public List<Logistic> getLogisticListByIdForUser(String userId, int logisticId, int page, int limit) {
        return logisticMapper.getLogisticListByIdForUser(userId,logisticId,page,limit);
    }
}
