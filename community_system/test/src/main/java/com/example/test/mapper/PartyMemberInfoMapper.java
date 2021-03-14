package com.example.test.mapper;

import com.example.test.entity.PartyMemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartyMemberInfoMapper {
    // @Select(" SELECT * FROM party_member_info WHERE full_name LIKE Concat('%' , #{fullName} , '%') LIMIT #{page}, #{limit};")
    List<PartyMemberInfo> getPartyMemberInfoList(@Param("fullName") String fullName, @Param("page") int page, @Param("limit") int limit);

    // @Select("SELECT COUNT(id) FROM party_member_info;")
    int getPartyMemberInfoCount();

    // @Select("SELECT * FROM party_member_info WHERE id = #{partyMemberId};")
    PartyMemberInfo getPartyMemberInfoById(@Param("partyMemberId") String partyMemberId); // 身份证号

//    @Insert("        INSERT INTO service_activity(\n" +
//            "            project_name,\n" +
//            "            content,\n" +
//            "            begin_time,\n" +
//            "            registration_deadline,\n" +
//            "            recruitment_numbers,\n" +
//            "            requirement,\n" +
//            "            project_status\n" +
//            "        ) VALUES(\n" +
//            "                    #{activity.projectName},\n" +
//            "                    #{activity.content},\n" +
//            "                    #{activity.beginTime},\n" +
//            "                    #{activity.registrationDeadline},\n" +
//            "                    #{activity.recruitmentNumbers},\n" +
//            "                    #{activity.requirement},\n" +
//            "                    #{activity.projectStatus}\n" +
//            "        );")
    int addPartyMemberInfo(@Param("partyMemberInfo") PartyMemberInfo partyMemberInfo);

//    @Update("UPDATE party_member_info SET\n" +
//            "            full_name = #{partyMemberInfo.fullName},\n" +
//            "            unit_id = #{partyMemberInfo.unitId},\n" +
//            "            sex = #{partyMemberInfo.sex},\n" +
//            "        WHERE id = #{partyMemberInfo.partyMemberId};")
    int updatePartyMemberInfo(@Param("partyMemberInfo") PartyMemberInfo partyMemberInfo);


//    @Delete("DELETE FROM party_member_info WHERE id = #{partyMemberId};")
    int deletePartyMemberInfo(@Param("partyMemberId") String partyMemberId);
    int partyMemberLogin(@Param("volunteerId") String volunteerId, @Param("fullName") String fullName);
}
