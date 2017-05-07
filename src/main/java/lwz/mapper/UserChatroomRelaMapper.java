package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.UserChatroomRelaExample;
import lwz.pojo.UserChatroomRelaKey;

import java.util.List;

public interface UserChatroomRelaMapper {
    int countByExample(UserChatroomRelaExample example);

    int deleteByExample(UserChatroomRelaExample example);

    int deleteByPrimaryKey(UserChatroomRelaKey key);

    int insert(UserChatroomRelaKey record);

    int insertSelective(UserChatroomRelaKey record);

    List<UserChatroomRelaKey> selectByExample(UserChatroomRelaExample example);

    int updateByExampleSelective(@Param("record") UserChatroomRelaKey record, @Param("example") UserChatroomRelaExample example);

    int updateByExample(@Param("record") UserChatroomRelaKey record, @Param("example") UserChatroomRelaExample example);
}