package com.example.test.serviceImpl;

import com.example.test.entity.CooperationUnit;
import com.example.test.mapper.CooperationUnitMapper;
import com.example.test.service.CooperationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CooperationUnitServiceImpl implements CooperationUnitService {
    //将DAO注入Service层
    @Autowired
    private CooperationUnitMapper cooperationUnitMapper;

    @Override
    public int getCooperationUnitCount() {
        return cooperationUnitMapper.getCooperationUnitCount();
    }

    @Override
    public CooperationUnit getCooperationUnitById(int unitId) {
        return cooperationUnitMapper.getCooperationUnitById(unitId);
    }

    @Override
    public List<CooperationUnit> getCooperationUnitList(String unitName, int page, int limit) {
        return cooperationUnitMapper.getCooperationUnitList(unitName, page, limit);
    }

    @Override
    public List<CooperationUnit> getCooperationUnitListById(int unitId, int page, int limit) {
        return cooperationUnitMapper.getCooperationUnitListById(unitId, page, limit);
    }

    @Override
    public int addCooperationUnit(CooperationUnit cooperationUnit) {
        return cooperationUnitMapper.addCooperationUnit(cooperationUnit);
    }

    @Override
    public int deleteCooperationUnit(int unitId) {
        return cooperationUnitMapper.deleteCooperationUnit(unitId);
    }
}
