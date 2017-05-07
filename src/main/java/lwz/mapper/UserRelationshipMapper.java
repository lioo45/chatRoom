package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.UserRelationshipExample;
import lwz.pojo.UserRelationshipKey;

import java.util.List;

public interface UserRelationshipMapper {
    int countByExample(UserRelationshipExample example);

    int deleteByExample(UserRelationshipExample example);

    int deleteByPrimaryKey(UserRelationshipKey key);

    int insert(UserRelationshipKey record);

    int insertSelective(UserRelationshipKey record);

    List<UserRelationshipKey> selectByExample(UserRelationshipExample example);

    int updateByExampleSelective(@Param("record") UserRelationshipKey record, @Param("example") UserRelationshipExample example);

    int updateByExample(@Param("record") UserRelationshipKey record, @Param("example") UserRelationshipExample example);
}