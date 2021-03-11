package com.example.test.serviceImpl;

import com.example.test.entity.PartyMemberInfo;
import com.example.test.mapper.PartyMemberInfoMapper;
import com.example.test.service.PartyMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyMemberInfoServiceImpl implements PartyMemberInfoService {
    //将DAO注入Service层
    @Autowired
    private PartyMemberInfoMapper partyMemberInfoMapper;

    @Override
    public List<PartyMemberInfo> getPartyMemberInfoList(String fullName, int page, int limit) {
        return partyMemberInfoMapper.getPartyMemberInfoList(fullName, page, limit);
    }

    @Override
    public int getPartyMemberInfoCount() {
        return partyMemberInfoMapper.getPartyMemberInfoCount();
    }

    @Override
    public PartyMemberInfo getPartyMemberInfoById(String partyMemberId) {
        return partyMemberInfoMapper.getPartyMemberInfoById(partyMemberId);
    }

    @Override
    public int addPartyMemberInfo(PartyMemberInfo partyMemberInfo) {
        return partyMemberInfoMapper.addPartyMemberInfo(partyMemberInfo);
    }

    @Override
    public int updatePartyMemberInfo(PartyMemberInfo partyMemberInfo) {
        return partyMemberInfoMapper.updatePartyMemberInfo(partyMemberInfo);
    }

    @Override
    public int deletePartyMemberInfo(String partyMemberId) {
        return partyMemberInfoMapper.deletePartyMemberInfo(partyMemberId);
    }
}
