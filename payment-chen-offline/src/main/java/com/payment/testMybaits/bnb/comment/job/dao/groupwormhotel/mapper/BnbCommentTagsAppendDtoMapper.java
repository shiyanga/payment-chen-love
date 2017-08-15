package bnb.comment.job.dao.groupwormhotel.mapper;

import bnb.comment.job.dto.wormhoteldb.BnbCommentTagsAppendDto;

public interface BnbCommentTagsAppendDtoMapper {
    int deleteByPrimaryKey(Long tagid);

    int insert(BnbCommentTagsAppendDto record);

    int insertSelective(BnbCommentTagsAppendDto record);

    BnbCommentTagsAppendDto selectByPrimaryKey(Long tagid);

    int updateByPrimaryKeySelective(BnbCommentTagsAppendDto record);

    int updateByPrimaryKey(BnbCommentTagsAppendDto record);
}