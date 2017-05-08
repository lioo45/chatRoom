package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.RecordChatroom;
import lwz.pojo.RecordChatroomExample;

import java.util.List;

public interface RecordChatroomMapper {
    int countByExample(RecordChatroomExample example);

    int deleteByExample(RecordChatroomExample example);

    int insert(RecordChatroom record);

    int insertSelective(RecordChatroom record);

    List<RecordChatroom> selectByExample(RecordChatroomExample example);

    int updateByExampleSelective(@Param("record") RecordChatroom record, @Param("example") RecordChatroomExample example);

    int updateByExample(@Param("record") RecordChatroom record, @Param("example") RecordChatroomExample example);
}