package lwz.pojo;

import java.util.Date;

public class RecordChatroom extends RecordChatroomKey {
    private String record;

    private Date date;

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