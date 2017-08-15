package bnb.comment.job.dto.wormhoteldb;

import java.util.Date;

public class BbbCommentTagsCommentAppendDto {
    private Long id;

    private Long commentid;

    private Integer tagid;

    private Long spaceid;

    private String highlight;

    private Date dataChange_lastTime;

    private Date dataCreate_lastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentid() {
        return commentid;
    }

    public void setCommentid(Long commentid) {
        this.commentid = commentid;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Long getSpaceid() {
        return spaceid;
    }

    public void setSpaceid(Long spaceid) {
        this.spaceid = spaceid;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight == null ? null : highlight.trim();
    }

    public Date getDataChange_lastTime() {
        return dataChange_lastTime;
    }

    public void setDataChange_lastTime(Date dataChange_lastTime) {
        this.dataChange_lastTime = dataChange_lastTime;
    }

    public Date getDataCreate_lastTime() {
        return dataCreate_lastTime;
    }

    public void setDataCreate_lastTime(Date dataCreate_lastTime) {
        this.dataCreate_lastTime = dataCreate_lastTime;
    }
}