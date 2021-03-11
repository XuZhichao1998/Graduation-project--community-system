package com.example.test.service;

import com.example.test.entity.TemporaryAccessApplication;

import java.util.List;

public interface TemporaryAccessApplicationService {
    List<TemporaryAccessApplication> getTemporaryAccessApplicationList(String personName, int page, int limit);

    int getTemporaryAccessApplicationCount();

    TemporaryAccessApplication getTemporaryAccessApplicationById(Integer applicationId);

    int addTemporaryAccessApplication(TemporaryAccessApplication temporaryAccessApplication);

    int updateTemporaryAccessApplication(TemporaryAccessApplication temporaryAccessApplication);

    int deleteTemporaryAccessApplication(Integer applicationId);

    List<TemporaryAccessApplication> getApplicationListForGuest(String personId, String personName, int page, int limit);
    int getApplicationCountForGuest(String personId, String personName);
}
