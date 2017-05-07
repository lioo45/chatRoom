package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.RecordChatroom;
import lwz.pojo.RecordChatroomExample;
import lwz.pojo.RecordChatroomKey;

import java.util.List;

public interface RecordChatroomMapper {
    int countByExample(RecordChatroomExample example);

    int deleteByExample(RecordChatroomExample example);

    int deleteByPrimaryKey(RecordChatroomKey key);

    int insert(RecordChatroom record);

    int insertSelective(RecordChatroom record);

    List<RecordChatroom> selectByExample(RecordChatroomExample example);

    RecordChatroom selectByPrimaryKey(RecordChatroomKey key);

    int updateByExampleSelective(@Param("record") RecordChatroom record, @Param("example") RecordChatroomExample example);

    int updateByExample(@Param("record") RecordChatroom record, @Param("example") RecordChatroomExample example);

    int updateByPrimaryKeySelective(RecordChatroom record);

    int updateByPrimaryKey(RecordChatroom record);
}