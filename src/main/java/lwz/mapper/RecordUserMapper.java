package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.RecordUser;
import lwz.pojo.RecordUserExample;
import lwz.pojo.RecordUserKey;

import java.util.List;

public interface RecordUserMapper {
    int countByExample(RecordUserExample example);

    int deleteByExample(RecordUserExample example);

    int deleteByPrimaryKey(RecordUserKey key);

    int insert(RecordUser record);

    int insertSelective(RecordUser record);

    List<RecordUser> selectByExample(RecordUserExample example);

    RecordUser selectByPrimaryKey(RecordUserKey key);

    int updateByExampleSelective(@Param("record") RecordUser record, @Param("example") RecordUserExample example);

    int updateByExample(@Param("record") RecordUser record, @Param("example") RecordUserExample example);

    int updateByPrimaryKeySelective(RecordUser record);

    int updateByPrimaryKey(RecordUser record);
}