package lwz.pojo;

import java.util.Date;

public class RecordChatroom {
    private Integer ctid;

    private Integer uid;

    private String record;

    private Date date;

    public RecordChatroom(){}

    public RecordChatroom(Integer ctid, Integer uid, String record, Date date) {
        this.ctid = ctid;
        this.uid = uid;
        this.record = record;
        this.date = date;
    }

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}