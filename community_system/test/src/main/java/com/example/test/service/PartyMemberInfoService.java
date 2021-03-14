package com.example.test.service;

import com.example.test.entity.PartyMemberInfo;

import java.util.List;

public interface PartyMemberInfoService {
    List<PartyMemberInfo> getPartyMemberInfoList(String fullName, int page, int limit);

    int getPartyMemberInfoCount();

    PartyMemberInfo getPartyMemberInfoById(String partyMemberId);

    int addPartyMemberInfo(PartyMemberInfo partyMemberInfo);

    int updatePartyMemberInfo(PartyMemberInfo partyMemberInfo);

    int deletePartyMemberInfo(String partyMemberId);

    int partyMemberLogin(String volunteerId, String fullName);
}
