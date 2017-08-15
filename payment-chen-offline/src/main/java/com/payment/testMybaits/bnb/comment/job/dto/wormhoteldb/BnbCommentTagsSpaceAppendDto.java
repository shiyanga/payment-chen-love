package bnb.comment.job.dto.wormhoteldb;

import java.util.Date;

public class BnbCommentTagsSpaceAppendDto {
    private Long id;

    private Long spaceId;

    private Long tagid;

    private Long popularity;

    private Date dataCreate_lastTime;

    private Date dataChange_lastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public Date getDataCreate_lastTime() {
        return dataCreate_lastTime;
    }

    public void setDataCreate_lastTime(Date dataCreate_lastTime) {
        this.dataCreate_lastTime = dataCreate_lastTime;
    }

    public Date getDataChange_lastTime() {
        return dataChange_lastTime;
    }

    public void setDataChange_lastTime(Date dataChange_lastTime) {
        this.dataChange_lastTime = dataChange_lastTime;
    }
}