package bnb.comment.job.dto.wormhoteldb;

import java.util.Date;

public class BnbCommentTagsAppendDto {
    private Long tagid;

    private String tagname;

    private Integer tagtype;

    private Date dataChange_lastTime;

    private Date dataCreate_lastTime;

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname == null ? null : tagname.trim();
    }

    public Integer getTagtype() {
        return tagtype;
    }

    public void setTagtype(Integer tagtype) {
        this.tagtype = tagtype;
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