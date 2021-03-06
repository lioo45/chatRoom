package lwz.pojo;

public class ChatRoom {
    private Integer ctid;

    private String name;

    private String description;

    public ChatRoom(Integer ctid, String name, String description) {
        this.ctid = ctid;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public ChatRoom(){}

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}