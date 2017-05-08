package lwz.service;

import lwz.mapper.RecordChatroomMapper;
import lwz.mapper.RecordUserMapper;
import lwz.pojo.RecordChatroom;
import lwz.pojo.RecordChatroomExample;
import lwz.pojo.RecordUser;
import lwz.pojo.RecordUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pro on 17/5/7.
 */
@Service
public class RecordService {

    @Autowired
    private RecordChatroomMapper rcMapper;

    @Autowired
    private RecordUserMapper ruMapper;

    public void addRecord(RecordChatroom rc){
        rcMapper.insert(rc);
    }

    public void addRecord(RecordUser ru){
        ruMapper.insert(ru);
    }
    public List<RecordChatroom> getRecordsOrderByDate(int ctid){
        RecordChatroomExample example=new RecordChatroomExample();
        RecordChatroomExample.Criteria criteria=example.createCriteria();
        criteria.andCtidEqualTo(ctid);
        example.setOrderByClause("date");
        List<RecordChatroom> list=rcMapper.selectByExample(example);
        if(list==null||list.size()<=0)
            return null;
        if(list.size()>100)
            list=list.subList(0,100);
        return list;
    }

    public List<RecordUser> getUserRecord(String id1,String id2){
        List<Integer> list=new ArrayList<Integer>(2);
        list.add(new Integer(id1));
        list.add(new Integer(id2));

        RecordUserExample example=new RecordUserExample();
        RecordUserExample.Criteria criteria=example.createCriteria();
        criteria.andFromidIn(list);
        criteria.andToidIn(list);

        example.setOrderByClause("date");
        List<RecordUser> records=ruMapper.selectByExample(example);

        return records;
    }


}
