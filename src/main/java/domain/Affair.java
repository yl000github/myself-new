package domain;

import java.util.Date;

public class Affair {
    private Integer id;

    private String what;

    private String why;

    private String how;

    private String status;

    private String type;

    private String comment;

    private Date createTime;

    private Date startTime;

    private Date doneTime;

    private String duration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what == null ? null : what.trim();
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why == null ? null : why.trim();
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how == null ? null : how.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }
}