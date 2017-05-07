package lwz.mapper;

import org.apache.ibatis.annotations.Param;
import lwz.pojo.ChatRoom;
import lwz.pojo.ChatRoomExample;

import java.util.List;

public interface ChatRoomMapper {
    int countByExample(ChatRoomExample example);

    int deleteByExample(ChatRoomExample example);

    int deleteByPrimaryKey(Integer ctid);

    int insert(ChatRoom record);

    int insertSelective(ChatRoom record);

    List<ChatRoom> selectByExample(ChatRoomExample example);

    ChatRoom selectByPrimaryKey(Integer ctid);

    int updateByExampleSelective(@Param("record") ChatRoom record, @Param("example") ChatRoomExample example);

    int updateByExample(@Param("record") ChatRoom record, @Param("example") ChatRoomExample example);

    int updateByPrimaryKeySelective(ChatRoom record);

    int updateByPrimaryKey(ChatRoom record);
}