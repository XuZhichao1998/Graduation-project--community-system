package com.example.test.serviceImpl;

import com.example.test.entity.TemporaryAccessApplication;
import com.example.test.mapper.TemporaryAccessApplicationMapper;
import com.example.test.service.TemporaryAccessApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemporaryAccessApplicationServiceImpl implements TemporaryAccessApplicationService {
    //将DAO注入Service层
    @Autowired
    private TemporaryAccessApplicationMapper temporaryAccessApplicationMapper;

    @Override
    public List<TemporaryAccessApplication> getTemporaryAccessApplicationList(String personName, int page, int limit) {
        return temporaryAccessApplicationMapper.getTemporaryAccessApplicationList(personName, page, limit);
    }

    @Override
    public int getTemporaryAccessApplicationCount() {
        return temporaryAccessApplicationMapper.getTemporaryAccessApplicationCount();
    }

    @Override
    public TemporaryAccessApplication getTemporaryAccessApplicationById(Integer applicationId) {
        return temporaryAccessApplicationMapper.getTemporaryAccessApplicationById(applicationId);
    }

    @Override
    public int addTemporaryAccessApplication(TemporaryAccessApplication temporaryAccessApplication) {
        return temporaryAccessApplicationMapper.addTemporaryAccessApplication(temporaryAccessApplication);
    }

    @Override
    public int updateTemporaryAccessApplication(TemporaryAccessApplication temporaryAccessApplication) {
        return temporaryAccessApplicationMapper.updateTemporaryAccessApplication(temporaryAccessApplication);
    }

    @Override
    public int deleteTemporaryAccessApplication(Integer applicationId) {
        return temporaryAccessApplicationMapper.deleteTemporaryAccessApplication(applicationId);
    }

    @Override
    public List<TemporaryAccessApplication> getApplicationListForGuest(String personId, String personName, int page, int limit) {
        return temporaryAccessApplicationMapper.getApplicationListForGuest(personId, personName, page, limit);
    }

    @Override
    public int getApplicationCountForGuest(String personId, String personName) {
        return temporaryAccessApplicationMapper.getApplicationCountForGuest(personId, personName);
    }
}
