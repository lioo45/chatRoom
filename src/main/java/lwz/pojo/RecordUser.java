package lwz.pojo;

import java.util.Date;

public class RecordUser {
    private Integer fromid;

    private Integer toid;

    private String record;

    private Date date;


    public RecordUser(){}

    public RecordUser(Integer fromid, Integer toid, String record, Date date) {
        this.fromid = fromid;
        this.toid = toid;
        this.record = record;
        this.date = date;
    }

    public Integer getFromid() {
        return fromid;
    }

    public void setFromid(Integer fromid) {
        this.fromid = fromid;
    }

    public Integer getToid() {
        return toid;
    }

    public void setToid(Integer toid) {
        this.toid = toid;
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