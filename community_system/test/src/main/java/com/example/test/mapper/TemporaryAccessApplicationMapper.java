package com.example.test.mapper;

import com.example.test.entity.TemporaryAccessApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemporaryAccessApplicationMapper {
    List<TemporaryAccessApplication> getTemporaryAccessApplicationList(
            @Param("personName") String personName,
            @Param("page") int page,
            @Param("limit") int limit
    );

    int getTemporaryAccessApplicationCount();

    TemporaryAccessApplication getTemporaryAccessApplicationById(@Param("applicationId") Integer applicationId);

    int addTemporaryAccessApplication(@Param("temporaryAccessApplication") TemporaryAccessApplication temporaryAccessApplication);

    int updateTemporaryAccessApplication(@Param("temporaryAccessApplication") TemporaryAccessApplication temporaryAccessApplication);

    int deleteTemporaryAccessApplication(@Param("applicationId") Integer applicationId);

    List<TemporaryAccessApplication> getApplicationListForGuest(
            @Param("personId") String personId,
            @Param("personName") String personName,
            @Param("page") int page,
            @Param("limit") int limit
    );

    int getApplicationCountForGuest(@Param("personId") String personId, @Param("personName") String personName);


}
